package dev.kirillzhelt.maymaymay.daysmodel.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.kirillzhelt.maymaymay.daysmodel.db.entities.DayEntity
import java.util.*

@Dao
interface DayDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(day: DayEntity)

    @Query("""
        SELECT *
        FROM days
    """)
    fun getDays(): LiveData<List<DayEntity>>

    @Query("""
        SELECT days.id
        FROM days
        WHERE days.day_date = :date
    """)
    suspend fun getDayId(date: Date): Int?

    @Update
    suspend fun update(day: DayEntity)

    @Delete
    suspend fun delete(day: DayEntity)

    @Query("""
        DELETE
        FROM days
        WHERE days.day_date = :date
    """)
    suspend fun deleteByDate(date: Date)

    @Query("""
        SELECT days.day_date
        FROM days
    """)
    fun getDates(): LiveData<List<Date>>

}