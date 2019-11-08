package dev.kirillzhelt.maymaymay.dbtests

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.kirillzhelt.maymaymay.daysmodel.DayGrade
import dev.kirillzhelt.maymaymay.daysmodel.db.DayRoomDatabase
import dev.kirillzhelt.maymaymay.daysmodel.db.daos.DayDao
import dev.kirillzhelt.maymaymay.daysmodel.db.entities.DayEntity
import dev.kirillzhelt.maymaymay.utils.observeOnce
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@RunWith(AndroidJUnit4::class)
class DayDaoTests {

    private lateinit var db: DayRoomDatabase
    private lateinit var dayDao: DayDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, DayRoomDatabase::class.java).build()

        dayDao = db.dayDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertOneDayAndReadInList() = runBlocking {
        val day = DayEntity(Date(System.currentTimeMillis()), "jumping running sleeping", DayGrade.EIGHT, 1)

        dayDao.insert(day)

        dayDao.getDays().observeOnce {
            assertEquals(day, it[0])
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertOneDayAndDeleteIt() = runBlocking {
        val day = DayEntity(Date(System.currentTimeMillis()), "jumping running sleeping", DayGrade.EIGHT, 1)

        dayDao.insert(day)
        dayDao.delete(day)

        dayDao.getDays().observeOnce {
            assertTrue(it.isEmpty())
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertDaysWithTheSameDate() = runBlocking {
        val date = Date(System.currentTimeMillis())

        val day1 = DayEntity(date, "sleeping", DayGrade.TWO, 1)
        val day2 = DayEntity(date, "sleeping running", DayGrade.THREE, 2)

        dayDao.insert(day1)
        dayDao.insert(day2)

        dayDao.getDays().observeOnce {
            assertEquals(1, it.size)
            assertEquals(day1, it[0])
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertManyDaysAndReadInList() = runBlocking {
        val days = mutableListOf<DayEntity>()

        val simpleDateFormat = SimpleDateFormat("dd-mm-yyyy", Locale.US)

        for (i in 0..5) {
            val date = simpleDateFormat.parse("$i-10-2019")!!

            val grade = (0..10).random()
            val dayGrade = DayGrade.fromInt(grade)

            days.add(DayEntity(date, "description", dayGrade, i + 1))
        }

        for (day in days) {
            dayDao.insert(day)
        }

        dayDao.getDays().observeOnce {
            days.forEachIndexed { index, dayEntity ->
                assertEquals(dayEntity, it[index])
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertManyDaysAndReadDatesInList() = runBlocking {
        val days = mutableListOf<DayEntity>()
        val dates = mutableListOf<Date>()

        val simpleDateFormat = SimpleDateFormat("dd-mm-yyyy", Locale.US)

        for (i in 0..5) {
            val date = simpleDateFormat.parse("$i-10-2019")!!

            val grade = (0..10).random()
            val dayGrade = DayGrade.fromInt(grade)

            dates.add(date)
            days.add(DayEntity(date, "description", dayGrade, i + 1))
        }

        for (day in days) {
            dayDao.insert(day)
        }

        dayDao.getDates().observeOnce {
            dates.forEachIndexed { index, date ->
                assertEquals(date, it[index])
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertOneDayAndUpdateIt() = runBlocking {
        val day = DayEntity(Date(System.currentTimeMillis()), "jumping running sleeping", DayGrade.EIGHT, 1)

        dayDao.insert(day)

        day.description = "hello world"

        dayDao.update(day)

        dayDao.getDays().observeOnce {
            assertEquals(day, it[0])
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertDayAndGetItsIdByDate() = runBlocking {
        val date = Date(System.currentTimeMillis())
        val day = DayEntity(date, "day", DayGrade.EIGHT, 1)

        dayDao.insert(day)

        assertEquals(1, dayDao.getDayId(date))
    }

    @Test
    @Throws(Exception::class)
    fun insertDayAndGetNullIdByWrongDate() = runBlocking {
        val date = Date(System.currentTimeMillis())
        val day = DayEntity(date, "day", DayGrade.EIGHT, 1)

        dayDao.insert(day)

        assertEquals(null, dayDao.getDayId(Date(System.currentTimeMillis())))
    }
}