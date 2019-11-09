package dev.kirillzhelt.maymaymay.daysmodel.db.entities

import androidx.room.ColumnInfo
import dev.kirillzhelt.maymaymay.daysmodel.DayGrade
import java.util.*

data class DayWithTagEntity(

    @ColumnInfo(name = "day_date")
    val date: Date,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "grade")
    val grade: DayGrade,

    @ColumnInfo(name = "tag")
    val tag: String?
    )