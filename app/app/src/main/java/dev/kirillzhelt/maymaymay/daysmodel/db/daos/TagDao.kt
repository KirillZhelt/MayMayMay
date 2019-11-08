package dev.kirillzhelt.maymaymay.daysmodel.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.kirillzhelt.maymaymay.daysmodel.db.entities.TagEntity

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tag: TagEntity)

    @Query("""
        SELECT * 
        FROM tags
    """)
    fun getTags(): LiveData<List<TagEntity>>

    @Query("""
        SELECT tags.id
        FROM tags
        WHERE tags.tag IN (:tags)
    """)
    fun getTagIds(tags: List<String>): LiveData<List<Int>>

}