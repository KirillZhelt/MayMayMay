package dev.kirillzhelt.maymaymay.daysmodel.db.utils

import androidx.room.TypeConverter
import dev.kirillzhelt.maymaymay.daysmodel.DayGrade
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun timestampFromDate(date: Date?): Long? {
        if (date != null) {
            val calendar = Calendar.getInstance()
            calendar.time = date

            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            return calendar.time.time
        }

        return null
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