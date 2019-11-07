package dev.kirillzhelt.maymaymay.daysmodel.db.daos

import androidx.room.*
import dev.kirillzhelt.maymaymay.daysmodel.db.entities.DayEntity
import java.util.*

@Dao
interface DayDao {

    @Insert
    fun insert(day: DayEntity)

    @Query("""
        SELECT *
        FROM days
    """)
    fun getDays(): List<DayEntity>

    @Update
    fun update(day: DayEntity)

    @Delete
    fun delete(day: DayEntity)

    @Query("""
        SELECT days.day_date
        FROM days
    """)
    fun getDates(): List<Date>

}