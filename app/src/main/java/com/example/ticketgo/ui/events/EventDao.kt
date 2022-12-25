package com.example.ticketgo.ui.events

import androidx.room.*

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEvent(vararg events: Event)

    @Query("SELECT * FROM EVENT ORDER BY eventId ASC")
    suspend fun getAllEvents(): List<Event>

    @Query("SELECT * FROM EVENT WHERE eventCategory = :category")
    suspend fun getEventsByEventType(category: String): List<Event>

    @Query("SELECT * FROM EVENT WHERE eventId = :id")
    suspend fun getEventByEventID(id: Int): Event
}