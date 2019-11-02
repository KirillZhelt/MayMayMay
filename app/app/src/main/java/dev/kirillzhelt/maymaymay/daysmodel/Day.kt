package dev.kirillzhelt.maymaymay.daysmodel

import java.util.*

// https://developer.android.com/training/data-storage/room
// https://developer.android.com/training/data-storage/room/referencing-data

data class Day(val date: Date,
               var description: String,
               val tags: MutableList<String>,
               val grade: DayGrade)
