package com.example.ticketgo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ticketgo.ui.event_type.EventType
import com.example.ticketgo.ui.event_type.EventTypeDao
import com.example.ticketgo.ui.events.Event
import com.example.ticketgo.ui.events.EventDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val eventDao: EventDao,
    private val eventTypeDao: EventTypeDao
) : ViewModel() {

    fun addEventCategory(eventCategoryList: Array<EventType>) {
        viewModelScope.launch(Dispatchers.IO) {
            eventTypeDao.addEventCategory(*eventCategoryList)
        }
    }

    fun addEvents(eventList: Array<Event>) {
        viewModelScope.launch(Dispatchers.IO) {
            eventDao.addEvent(*eventList)
        }
    }

}