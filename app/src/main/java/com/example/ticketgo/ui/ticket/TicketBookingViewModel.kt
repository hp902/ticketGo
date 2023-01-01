package com.example.ticketgo.ui.ticket

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ticketgo.ui.events.Event
import com.example.ticketgo.ui.events.EventDao
import com.example.ticketgo.ui.events.Seat
import com.example.ticketgo.utils.Constants
import com.example.ticketgo.utils.DateTimeUtils
import kotlinx.coroutines.launch

class TicketBookingViewModel(
    private val eventDao: EventDao, private val ticketDao: TicketDao
) : ViewModel() {

    companion object {
        private const val TAG: String = "TicketBookingViewModel"
    }

    var eventList = ArrayList<Event>()
    var eventId: Int = 0

    private var selectedEvents = HashMap<Int, Int>()
    var selectedSeats = MutableLiveData(0)

    private val _eventsData: MutableLiveData<List<Event>> = MutableLiveData()
    val eventsData: LiveData<List<Event>> = _eventsData

    private var _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    fun bookTicket() {
        viewModelScope.launch {
            runCatching {
                _loading.postValue(true)
                val bookedEventList = ArrayList<BookedEvents>()
                eventList.forEach { event ->
                    if (selectedEvents.containsKey(event.eventId)) {
                        val bookedEvent =
                            BookedEvents(
                                event.eventId,
                                event.eventName,
                                event.startTime,
                                event.seats.filter {
                                    it.status == Constants.SEAT_SELECTED
                                } as ArrayList<Seat>)
                        bookedEventList.add(bookedEvent)
                        event.seats.forEach {
                            if (it.status == Constants.SEAT_SELECTED)
                                it.status = Constants.SEAT_BOOKED
                        }
                    }
                }
                val ticket = Ticket(
                    events = bookedEventList,
                    ticketStatus = Constants.TICKET_BOOKED,
                    createdAt = DateTimeUtils.getCurrentDateTime()
                )
                ticketDao.putTicket(ticket)
            }.onSuccess {
                Log.d(TAG, "bookTicket: ")
                _loading.postValue(false)
                updateEvents()
            }.onFailure {
                _loading.postValue(false)
                Log.e(TAG, "bookTicket: ", it)
            }
        }
    }

    fun onSeatSelected(seat: Seat, position: Int, status: Int) {
        viewModelScope.launch {
            eventList[eventId - 1].seats[position].apply {
                this.status = seat.status
            }
            selectedSeats.postValue(selectedSeats.value?.plus(status) ?: status)
            val eventSeats = selectedEvents[eventId]
            if (status > 0) {
                selectedEvents[eventId] = status.plus(eventSeats ?: 0)
            } else {
                if (eventSeats == 1 || eventSeats == 0 || eventSeats == null) {
                    selectedEvents.remove(eventId)
                } else {
                    selectedEvents[eventId] = status.plus(eventSeats)
                }
            }
        }
    }

    private fun updateEvents() {
        viewModelScope.launch {
            runCatching {
                eventDao.addEventUpdate(*eventList.toTypedArray())
            }.onSuccess {
                Log.d(TAG, "updateEvents: ")
            }.onFailure {
                Log.e(TAG, "updateEvents: ", it)
            }
        }
    }

    fun getAllEvents() {
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

}