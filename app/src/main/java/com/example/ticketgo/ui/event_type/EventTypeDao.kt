package com.example.ticketgo.ui.event_type

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EventTypeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEventCategory(vararg events: EventType)

    @Query("SELECT * FROM EventType")
    suspend fun getAllEventCategories(): List<EventType>

}