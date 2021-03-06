package dev.kirillzhelt.maymaymay.daysmodel.db.entities

import dev.kirillzhelt.maymaymay.daysmodel.DayGrade

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

// https://developer.android.com/training/data-storage/room
// https://developer.android.com/training/data-storage/room/referencing-data

@Entity(tableName = "days", indices = [Index(value = ["day_date"], unique = true)])
data class DayEntity(

    @ColumnInfo(name = "day_date")
    val date: Date,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "grade")
    val grade: DayGrade,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
)
