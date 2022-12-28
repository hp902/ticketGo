package com.example.ticketgo.utils

import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtils {
    companion object {

        const val TIME = 1
        const val TIME_DATE_DAY = 2
        const val TIME_DATE = 3
        const val DATE = 4

        /**
         * Get the Current Date Time
         * @return DateString in format of "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
         */
        fun getCurrentDateTime(): String {
            val simpleDateFormatFrom = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()
            )
            simpleDateFormatFrom.timeZone = TimeZone.getDefault()
            return simpleDateFormatFrom.format(Date())
        }


        /**
         * Convert dateString into readable format
         * @param dateString DateString in format of "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
         * @param flag Set the return string format
         * @return DateString eg:- 10:05 PM 25 Dec 2022
         */
        fun convertDateString(dateString: String, flag: Int): String {
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
                val simpleDateFormatTo: SimpleDateFormat = when (flag) {
                    TIME -> SimpleDateFormat("hh:mm aa", Locale.getDefault())
                    TIME_DATE -> SimpleDateFormat("hh:mm aa dd MMM yyyy", Locale.getDefault())
                    TIME_DATE_DAY -> SimpleDateFormat(
                        "hh:mm aa dd MMM yyyy, EEEE",
                        Locale.getDefault()
                    )
                    DATE -> SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                    else -> SimpleDateFormat("hh:mm aa dd MMM yyyy, EEEE", Locale.getDefault())
                }
                simpleDateFormatTo.timeZone = TimeZone.getDefault()
                convertedDate = simpleDateFormatTo.format(date!!)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return convertedDate
        }


        /**
         * Get DateString of Next Day
         * @param hourOfDay the hour of next day in 24-hours format
         * @return Date String in format of "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
         */
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


        /**
         * Get readable Time from Seconds
         * @param second seconds value
         * @return Time String eg:- 1 hour 25 minutes, 1 hour
         */
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