package com.example.ticketgo.utils

import java.text.SimpleDateFormat
import java.util.*

fun main() {
    println(Date().hours)
    print(Date().minutes)
}


class DateTimeUtils {
    companion object {

        fun getCurrentDateTime(): String {
            val simpleDateFormatFrom = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()
            )
            simpleDateFormatFrom.timeZone = TimeZone.getDefault()
            return simpleDateFormatFrom.format(Date())
        }

        fun convertDate(dateString: String, includeDayOfWeek: Boolean? = false): String {
            if (dateString == "") {
                return ""
            }
            var convertedDate = ""

            try {
                val simpleDateFormatFrom = SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()
                )
                simpleDateFormatFrom.timeZone = TimeZone.getDefault()
                print(TimeZone.getDefault().displayName)
                val date = simpleDateFormatFrom.parse(dateString)
                val simpleDateFormatTo: SimpleDateFormat = if (includeDayOfWeek == true) {
                    SimpleDateFormat("hh:mm aa dd MMM yyyy, EEEE", Locale.getDefault())
                } else {
                    SimpleDateFormat("hh:mm aa dd MMM yyyy", Locale.getDefault())
                }
                simpleDateFormatTo.timeZone = TimeZone.getDefault()
                convertedDate = simpleDateFormatTo.format(date!!)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return convertedDate
        }

        fun getNextDayTime(hourOfDay: Int): String {
            var convertedDate = ""
            try {
                val calendar = Calendar.getInstance()
                calendar.apply {
                    add(Calendar.DATE, 1)
                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                    set(Calendar.MINUTE, 0)
                }
                val simpleDateFormatFrom = SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()
                )
                simpleDateFormatFrom.timeZone = TimeZone.getDefault()
                convertedDate = simpleDateFormatFrom.format(Date(calendar.timeInMillis))
            } catch (exception: java.lang.Exception) {
                exception.printStackTrace()
            }

            return convertedDate
        }

        fun convertSecToTime(second: Int): String {
            var timeString = ""

            var minutes = second / 60
            val hours = minutes / 60
            minutes %= 60
            if (hours >= 1) {
                timeString = "$timeString$hours hour "
            }
            if (minutes >= 1) {
                timeString = "$timeString$minutes minute"
            }

            return timeString
        }
    }
}