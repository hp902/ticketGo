package com.example.ticketgo.ui.events

import androidx.room.*

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addEvent(vararg events: Event): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEventUpdate(vararg events: Event): List<Long>

    @Query("SELECT * FROM EVENT ORDER BY eventId ASC")
    suspend fun getAllEvents(): List<Event>

    @Query("SELECT * FROM EVENT WHERE eventCategory = :category")
    suspend fun getEventsByEventType(category: String): List<Event>

    @Query("SELECT * FROM EVENT WHERE eventId = :id")
    suspend fun getEventByEventID(id: Int): Event

    @Update
    suspend fun updateEvent(event: Event)
}