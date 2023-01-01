package com.example.ticketgo.ui.events

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ticketgo.utils.Constants

@Entity
data class Event(
    @PrimaryKey
    val eventId: Int,
    val eventName: String,
    val eventDescription: String,
    val bannerImageUrl: String,
    val eventDurationSecond: Int,
    val startTime: String,
    val eventCategory: String,
    var seats: ArrayList<Seat>
)

data class Seat(
    val id: Int,
    val rowId: String,
    var columnId: Int,
    var status: String? = Constants.SEAT_EMPTY
)