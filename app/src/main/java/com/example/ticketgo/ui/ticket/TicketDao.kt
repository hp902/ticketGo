package com.example.ticketgo.ui.ticket

import androidx.room.Dao

@Dao
interface TicketDao {

    suspend fun putTicket()

}