package dev.kirillzhelt.maymaymay.daysmodel.db.utils

import androidx.room.TypeConverter
import dev.kirillzhelt.maymaymay.daysmodel.DayGrade
import dev.kirillzhelt.maymaymay.utils.getDateWithoutTime
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun timestampFromDate(date: Date?): Long? {
        return date?.getDateWithoutTime()?.time
    }

    @TypeConverter
    fun fromInt(value: Int?): DayGrade? {
        return if (value != null) DayGrade.fromInt(value) else null
    }

    @TypeConverter
    fun intFromGrade(grade: DayGrade?): Int? {
        return grade?.grade
    }

}