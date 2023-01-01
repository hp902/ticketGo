package com.example.ticketgo.ui.ticket

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ticketgo.ui.events.Seat
import com.example.ticketgo.utils.Constants

@Entity
data class Ticket(
    @PrimaryKey
    val ticketId: Int = 0,
    val events: ArrayList<BookedEvents>,
    val createdAt: String,
    val ticketStatus: String = Constants.TICKET_BOOKED
)

data class BookedEvents(
    val eventId: Int,
    val startTime: String,
    val seats: ArrayList<Seat>
)