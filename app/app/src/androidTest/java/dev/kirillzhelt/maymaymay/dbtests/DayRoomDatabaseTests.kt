package dev.kirillzhelt.maymaymay.dbtests

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.kirillzhelt.maymaymay.daysmodel.DayGrade
import dev.kirillzhelt.maymaymay.daysmodel.db.DayRoomDatabase
import dev.kirillzhelt.maymaymay.daysmodel.db.daos.DayDao
import dev.kirillzhelt.maymaymay.daysmodel.db.daos.DayTagJoinDao
import dev.kirillzhelt.maymaymay.daysmodel.db.daos.TagDao
import dev.kirillzhelt.maymaymay.daysmodel.db.entities.DayEntity
import dev.kirillzhelt.maymaymay.utils.observeOnce
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class DayRoomDatabaseTests {

    private lateinit var db: DayRoomDatabase
    private lateinit var dayDao: DayDao
    private lateinit var tagDao: TagDao
    private lateinit var dayTagJoinDao: DayTagJoinDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, DayRoomDatabase::class.java).build()

        dayDao = db.dayDao()
        tagDao = db.tagDao()
        dayTagJoinDao = db.dayTagJoinDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeOneDayAndReadInList() {
        val day = DayEntity(Date(System.currentTimeMillis()), "jumping running sleeping", DayGrade.EIGHT)

        dayDao.insert(day)

        dayDao.getDays().observeOnce {
            assert(it[0] == day)
        }
    }

}