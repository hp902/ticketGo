package com.example.ticketgo.ui.ticket

import androidx.room.*

@Dao
interface TicketDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun putTicket(ticket: Ticket): Long

    @Query("SELECT * FROM Ticket ORDER BY ticketId DESC")
    suspend fun getAllTickets(): List<Ticket>

    @Update
    suspend fun changeTicketStatus(ticket: Ticket): Int

}