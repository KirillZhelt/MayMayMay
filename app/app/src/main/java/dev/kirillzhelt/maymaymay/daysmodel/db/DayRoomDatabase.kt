package dev.kirillzhelt.maymaymay.daysmodel.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.kirillzhelt.maymaymay.daysmodel.db.daos.DayDao
import dev.kirillzhelt.maymaymay.daysmodel.db.daos.DayTagJoinDao
import dev.kirillzhelt.maymaymay.daysmodel.db.daos.TagDao
import dev.kirillzhelt.maymaymay.daysmodel.db.entities.DayEntity
import dev.kirillzhelt.maymaymay.daysmodel.db.entities.DayTagJoin
import dev.kirillzhelt.maymaymay.daysmodel.db.entities.TagEntity
import dev.kirillzhelt.maymaymay.daysmodel.db.utils.Converters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Database(entities = [DayEntity::class, TagEntity::class, DayTagJoin::class], version = 1)
@TypeConverters(Converters::class)
abstract class DayRoomDatabase: RoomDatabase() {

    abstract fun dayDao(): DayDao

    abstract fun tagDao(): TagDao

    abstract fun dayTagJoinDao(): DayTagJoinDao

    companion object {

        @Volatile
        private var INSTANCE: DayRoomDatabase? = null

        fun getDatabase(context: Context): DayRoomDatabase {
            val i1 = INSTANCE
            if (i1 != null) {
                return i1
            }

            return synchronized(this) {
                var i2 = INSTANCE
                if (i2 != null) {
                    i2
                } else {
                    i2 = Room.databaseBuilder(
                        context.applicationContext,
                        DayRoomDatabase::class.java,
                        "day_database"
                        )
                        .addCallback(object: Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)

                                GlobalScope.launch(Dispatchers.Main) {
                                    val tags = listOf(TagEntity("Good sleep"), TagEntity("Studying"),
                                        TagEntity("Sport"))

                                    val tagDao = getDatabase(context).tagDao()

                                    tags.forEach { tagDao.insert(it) }
                                }
                            }
                        })
                        .build()

                    INSTANCE = i2
                    i2
                }
            }

        }


    }

}