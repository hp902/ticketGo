package com.example.ticketgo.ui.your_bookings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ticketgo.ui.ticket.Ticket
import com.example.ticketgo.ui.ticket.TicketDao
import kotlinx.coroutines.launch

class YourBookingsViewModel(
    private val ticketDao: TicketDao
) : ViewModel() {
    companion object {
        private const val TAG: String = "YourBookingsViewModel"
    }

    private val _ticketData: MutableLiveData<List<Ticket>> = MutableLiveData()
    val tickerData: LiveData<List<Ticket>> = _ticketData


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

}