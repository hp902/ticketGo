package com.example.ticketgo.ui.events

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event(
    @PrimaryKey
    val eventId: Int,
    val eventName: String,
    val eventDescription: String,
    val bannerImageUrl: String,
    val eventDurationSecond: Int,
    val startTime: String,
    val eventCategory: String
)