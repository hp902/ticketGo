package com.example.ticketgo.ui.ticket

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ticketgo.ui.events.Seat
import com.example.ticketgo.utils.Constants

@Entity
data class Ticket(
    @PrimaryKey(autoGenerate = true)
    val ticketId: Int? = null,
    val events: ArrayList<BookedEvents>,
    val createdAt: String,
    var ticketStatus: String = Constants.TICKET_BOOKED
)

data class BookedEvents(
    val eventId: Int,
    val eventName: String,
    val startTime: String,
    val seats: ArrayList<Seat>
)