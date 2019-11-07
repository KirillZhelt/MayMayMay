package dev.kirillzhelt.maymaymay.daysmodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

// https://developer.android.com/training/data-storage/room
// https://developer.android.com/training/data-storage/room/referencing-data

@Entity(tableName = "day_table")
data class Day(
    @PrimaryKey
    @ColumnInfo(name = "day_date")
    val date: Date,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "tags")
    val tags: MutableList<String>,

    @ColumnInfo(name = "grade")
    val grade: DayGrade

)
