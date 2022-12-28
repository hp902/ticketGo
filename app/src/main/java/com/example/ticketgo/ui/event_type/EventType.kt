package com.example.ticketgo.ui.event_type

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EventType(
    @PrimaryKey
    val eventType: String,
    val name: String
)
