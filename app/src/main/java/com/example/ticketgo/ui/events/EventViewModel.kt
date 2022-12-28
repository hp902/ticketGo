package com.example.ticketgo.ui.events

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ticketgo.ui.event_type.EventCategory
import com.example.ticketgo.ui.event_type.EventType
import com.example.ticketgo.ui.event_type.EventTypeDao
import kotlinx.coroutines.launch

class EventViewModel(
    private val eventDao: EventDao,
    private val eventTypeDao: EventTypeDao
) : ViewModel() {

    companion object {
        private const val TAG: String = "EventViewModel"
    }

    private val _eventsData: MutableLiveData<List<Event>> = MutableLiveData()
    val eventsData: LiveData<List<Event>> = _eventsData

    private val _eventsCategory: MutableLiveData<List<EventType>> = MutableLiveData()
    val eventsCategory: LiveData<List<EventType>> = _eventsCategory

    init {
        getEventCategory()
        getAllEvents()
    }

    fun getEvents(category: String? = null) {
        if (category == null || category == EventCategory.All.name) {
            getAllEvents()
        } else {
            getEventsByCategory(category)
        }
    }

    private fun getAllEvents() {
        viewModelScope.launch {
            runCatching {
                eventDao.getAllEvents()
            }.onSuccess {
                _eventsData.postValue(it)
            }.onFailure {
                Log.e(TAG, "getAllEvents: ", it)
            }
        }
    }

    private fun getEventsByCategory(category: String) {
        viewModelScope.launch {
            runCatching {
                eventDao.getEventsByEventType(category)
            }.onSuccess {
                _eventsData.postValue(it)
            }.onFailure {
                Log.e(TAG, "getEventsByCategory: ", it)
            }
        }
    }

    private fun getEventCategory() {
        viewModelScope.launch {
            runCatching {
                eventTypeDao.getAllEventCategories()
            }.onSuccess {
                _eventsCategory.postValue(it)
            }.onFailure {
                Log.e(TAG, "getEventCategory: ", it)
            }
        }
    }
}