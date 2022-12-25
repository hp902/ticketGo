package com.example.ticketgo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ticketgo.ui.event_type.EventType
import com.example.ticketgo.ui.event_type.EventTypeDao
import com.example.ticketgo.ui.events.Event
import com.example.ticketgo.ui.events.EventDao

@Database(entities = [EventType::class, Event::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getEventDao(): EventDao
    abstract fun getEventCategoryDao(): EventTypeDao

}