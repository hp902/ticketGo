package com.example.ticketgo.utils

import androidx.room.TypeConverter
import com.example.ticketgo.ui.events.Event
import com.example.ticketgo.ui.events.Seat
import com.example.ticketgo.ui.ticket.BookedEvents
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun listToString(list: ArrayList<Seat>): String {
        return Gson().convertToJsonString(list)
    }

    @TypeConverter
    fun stringToList(jsonString: String): ArrayList<Seat> {
        return Gson().fromJson(jsonString)
    }

    @TypeConverter
    fun listToString2(list: ArrayList<BookedEvents>): String {
        return Gson().convertToJsonString(list)
    }

    @TypeConverter
    fun stringToList2(jsonString: String): ArrayList<BookedEvents> {
        return Gson().fromJson(jsonString)
    }

}