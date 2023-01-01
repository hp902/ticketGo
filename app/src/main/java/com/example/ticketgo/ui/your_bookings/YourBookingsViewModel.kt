package com.example.ticketgo.ui.your_bookings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ticketgo.ui.events.EventDao
import com.example.ticketgo.ui.ticket.Ticket
import com.example.ticketgo.ui.ticket.TicketDao
import com.example.ticketgo.utils.Constants
import kotlinx.coroutines.launch

class YourBookingsViewModel(
    private val ticketDao: TicketDao,
    private val eventDao: EventDao
) : ViewModel() {

    companion object {
        private const val TAG: String = "YourBookingsViewModel"
    }

    private val _ticketData: MutableLiveData<List<Ticket>> = MutableLiveData()
    val ticketData: LiveData<List<Ticket>> = _ticketData


    init {
        getTickets()
    }


    fun getTickets() {
        viewModelScope.launch {
            runCatching {
                ticketDao.getAllTickets()
            }.onSuccess {
                _ticketData.postValue(it)
            }.onFailure {
                Log.e(TAG, "getTickets: ", it)
            }
        }
    }


    fun cancelTicket(ticket: Ticket) {
        viewModelScope.launch {
            ticket.apply {
                ticketStatus = Constants.TICKET_CANCELED
            }

            ticket.events.forEach { events ->
                val event = eventDao.getEventByEventID(events.eventId)
                events.seats.forEach {
                    event.seats[it.id].apply {
                        this.status = Constants.SEAT_EMPTY
                    }
                }
                launch {
                    eventDao.updateEvent(event)
                }
            }
            runCatching {
                ticketDao.changeTicketStatus(ticket)
            }.onSuccess {
                getTickets()
            }.onFailure {
                Log.e(TAG, "cancelTicket: ", it)
            }
        }
    }
}