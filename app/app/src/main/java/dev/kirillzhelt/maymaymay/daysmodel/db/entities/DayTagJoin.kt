package dev.kirillzhelt.maymaymay.daysmodel.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "day_tag_join",
    primaryKeys = ["day_id", "tag_id"],
    foreignKeys = [
        ForeignKey(entity = DayEntity::class,
            parentColumns = ["id"],
            childColumns = ["day_id"],
            onDelete = CASCADE
            ),
        ForeignKey(entity = TagEntity::class,
            parentColumns = ["id"],
            childColumns = ["tag_id"],
            onDelete = CASCADE
            )
    ]
    )
data class DayTagJoin(
    @ColumnInfo(name = "day_id")
    val dayId: Int,

    @ColumnInfo(name = "tag_id")
    val tagId: Int
)