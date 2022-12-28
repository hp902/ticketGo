package com.example.ticketgo.ui.ticket

import androidx.room.Entity
import com.example.ticketgo.ui.events.Seat
import com.example.ticketgo.utils.Constants

@Entity
data class Ticket(
    val ticketId: Int = 0,
    val events: ArrayList<BookedEvents>
)

data class BookedEvents(
    val eventId: Int,
    val startTime: String,
    val seats: ArrayList<Seat>,
    val ticketStatus: String = Constants.TICKET_BOOKED
)