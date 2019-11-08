package dev.kirillzhelt.maymaymay.daysmodel.db.utils

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun timestampFromDate(date: Date?): Long? {
        return date?.time
    }

}