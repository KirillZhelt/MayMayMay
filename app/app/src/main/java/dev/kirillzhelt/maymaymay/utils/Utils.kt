package dev.kirillzhelt.maymaymay.utils

import java.util.*

fun Date.getDateWithoutTime(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this

    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)

    return Date(calendar.time.time)
}

fun getCurrentDate() = Date(System.currentTimeMillis()).getDateWithoutTime()
