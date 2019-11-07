package dev.kirillzhelt.maymaymay.daysmodel.db.daos

import android.nfc.Tag
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.kirillzhelt.maymaymay.daysmodel.db.entities.TagEntity

@Dao
interface TagDao {

    @Insert
    fun insert(tag: TagEntity)

    @Query("""
        SELECT * 
        FROM tags
    """)
    fun getTags(): List<TagEntity>

}