package dev.kirillzhelt.maymaymay.daysmodel

import java.lang.IllegalArgumentException

enum class DayGrade(val grade: Int) {

    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10);

    companion object {
        fun fromInt(grade: Int) =
            values().find { grade == it.grade } ?: throw IllegalArgumentException("No such grade")
    }
}

