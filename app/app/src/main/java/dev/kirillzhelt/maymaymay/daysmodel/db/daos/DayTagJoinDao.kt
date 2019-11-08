package dev.kirillzhelt.maymaymay.daysmodel.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.kirillzhelt.maymaymay.daysmodel.db.entities.DayTagJoin
import dev.kirillzhelt.maymaymay.daysmodel.db.entities.DayWithTagEntity
import dev.kirillzhelt.maymaymay.daysmodel.db.entities.TagEntity

@Dao
interface DayTagJoinDao {

    @Insert
    suspend fun insert(dayTagJoin: DayTagJoin)

    @Insert
    suspend fun insert(dayTagJoin: List<DayTagJoin>)

    @Query("""
        SELECT * FROM tags
        INNER JOIN day_tag_join
        ON tags.id = day_tag_join.tag_id
        WHERE day_tag_join.day_id = :dayId
    """)
    fun getTagsForDay(dayId: Int): LiveData<List<TagEntity>>

    @Query("""
        SELECT days.day_date, days.description, days.grade, tags.tag
        FROM days 
        LEFT JOIN day_tag_join 
        ON days.id = day_tag_join.day_id
        LEFT JOIN tags
        ON day_tag_join.tag_id = tags.id
    """)
    fun getDaysWithTags(): LiveData<List<DayWithTagEntity>>

    @Delete
    suspend fun delete(dayTagJoin: DayTagJoin)
}