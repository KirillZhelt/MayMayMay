package dev.kirillzhelt.maymaymay.daysmodel

import java.text.SimpleDateFormat
import java.util.*

class DaysRepository {

    private val tags: MutableList<String> = mutableListOf("Good sleep", "Friends", "Sport")
    private val days: MutableList<Day> = mutableListOf()

    init {
        val simpleDateFormat = SimpleDateFormat("dd-mm-yyyy", Locale.US)

        for (i in 0..24) {
            val date = simpleDateFormat.parse("$i-10-2019")!!

            val beginTagIndex = (0..tags.size).random()
            val endTagIndex = (0..tags.size).random()

            val dayGrade = DayGrade.values().find { (0..11).random() == it.grade } !!

            days.add(Day(date, "Lorem ipsum dolor sit amet, consectetur " +
                    "adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore " +
                    "magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris " +
                    "nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate " +
                    "velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, " +
                    "sunt in culpa qui officia deserunt mollit anim id est laborum.",
                tags.subList(beginTagIndex, endTagIndex).toMutableList(), dayGrade))
        }

    }

    fun addNewDay(day: Day) {
        days.add(day)
    }

    fun deleteDay(day: Day): Boolean {
        return days.remove(day)
    }

    fun getAllDays(): List<Day> {
        return days
    }

    fun addNewTag(tag: String) {
        tags.add(tag)
    }

    fun deleteTag(tag: String): Boolean {
        return tags.remove(tag)
    }

    fun getAllTags(): List<String> {
        return tags
    }

}