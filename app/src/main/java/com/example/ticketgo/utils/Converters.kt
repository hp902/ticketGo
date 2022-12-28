package com.example.ticketgo.utils

import androidx.room.TypeConverter
import com.example.ticketgo.ui.events.Seat
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

}