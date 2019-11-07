package dev.kirillzhelt.maymaymay.daysmodel

import java.util.*

data class Day(
    val date: Date,
    var description: String,
    val tags: MutableList<String>,
    val grade: DayGrade,
    val id: Int? = null
)
