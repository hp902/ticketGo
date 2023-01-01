package com.example.ticketgo.ui.ticket

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TicketDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun putTicket(ticket: Ticket): Long

    @Query("SELECT * FROM Ticket ORDER BY ticketId DESC")
    suspend fun getAllTickets(): List<Ticket>

}