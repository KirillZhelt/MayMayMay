package dev.kirillzhelt.maymaymay.dbtests

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.core.internal.deps.guava.collect.Sets
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.kirillzhelt.maymaymay.daysmodel.DayGrade
import dev.kirillzhelt.maymaymay.daysmodel.db.DayRoomDatabase
import dev.kirillzhelt.maymaymay.daysmodel.db.daos.DayDao
import dev.kirillzhelt.maymaymay.daysmodel.db.daos.DayTagJoinDao
import dev.kirillzhelt.maymaymay.daysmodel.db.daos.TagDao
import dev.kirillzhelt.maymaymay.daysmodel.db.entities.DayEntity
import dev.kirillzhelt.maymaymay.daysmodel.db.entities.DayTagJoin
import dev.kirillzhelt.maymaymay.daysmodel.db.entities.DayWithTagEntity
import dev.kirillzhelt.maymaymay.daysmodel.db.entities.TagEntity
import dev.kirillzhelt.maymaymay.utils.getCurrentDate
import dev.kirillzhelt.maymaymay.utils.getDateWithoutTime
import dev.kirillzhelt.maymaymay.utils.observeOnce
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class DayTagJoinDaoTests {

    private lateinit var db: DayRoomDatabase
    private lateinit var dayTagJoinDao: DayTagJoinDao

    private lateinit var dayDao: DayDao
    private lateinit var tagDao: TagDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, DayRoomDatabase::class.java).build()

        dayTagJoinDao = db.dayTagJoinDao()

        dayDao = db.dayDao()
        tagDao = db.tagDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertOneTagForOneDay() = runBlocking {

        val day = DayEntity(getCurrentDate(), "day", DayGrade.EIGHT, 1)
        val tag = TagEntity("tag", 1)

        dayDao.insert(day)
        tagDao.insert(tag)

        val dayTagJoin = DayTagJoin(1, 1)
        dayTagJoinDao.insert(dayTagJoin)

        dayTagJoinDao.getTagsForDay(1).observeOnce {
            assertEquals(tag, it[0])
        }

    }

    @Test
    @Throws(Exception::class)
    fun insertManyTagsForOneDay() = runBlocking {

        val day = DayEntity(getCurrentDate(), "day", DayGrade.EIGHT, 1)

        val tags = mutableListOf<TagEntity>()
        for (i in 0..5) {
            tags.add(TagEntity("tag$i", i + 1))
        }

        dayDao.insert(day)

        for (tag in tags) {
            tagDao.insert(tag)

            val dayTagJoin = DayTagJoin(1, tag.id)
            dayTagJoinDao.insert(dayTagJoin)
        }

        dayTagJoinDao.getTagsForDay(1).observeOnce {
            assertEquals(tags, it)
        }

    }

    @Test
    @Throws(Exception::class)
    fun insertManyTagsForOneDayAndDeleteOneTag() = runBlocking {
        val day = DayEntity(getCurrentDate(), "day", DayGrade.EIGHT, 1)

        val tags = mutableListOf<TagEntity>()
        for (i in 0..5) {
            tags.add(TagEntity("tag$i", i + 1))
        }

        dayDao.insert(day)

        for (tag in tags) {
            tagDao.insert(tag)

            val dayTagJoin = DayTagJoin(1, tag.id)
            dayTagJoinDao.insert(dayTagJoin)
        }

        dayTagJoinDao.delete(DayTagJoin(1, 6))
        tags.removeAt(tags.size - 1)

        dayTagJoinDao.getTagsForDay(1).observeOnce {
            assertEquals(tags, it)
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertOneDayWithTagsAndGetTags() = runBlocking {
        val day = DayEntity(getCurrentDate(), "day", DayGrade.EIGHT, 1)

        val tags = List(4) {
            TagEntity("${it + 1}", it + 1)
        }

        dayDao.insert(day)
        tags.forEach { tag ->
            tagDao.insert(tag)
            dayTagJoinDao.insert(DayTagJoin(day.id, tag.id))
        }

        val expected = tags.map { tag ->
            DayWithTagEntity(day.date, day.description, day.grade, tag.tag)
        }

        dayTagJoinDao.getDaysWithTags().observeOnce {
            assertEquals(expected, it)
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertManyDaysWithTagsAndGetTags() = runBlocking {
        val day1 = DayEntity(getCurrentDate(), "day1", DayGrade.EIGHT, 1)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, 3)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val day2 = DayEntity(calendar.time, "day2", DayGrade.ONE, 2)

        val tags = List(4) {
            TagEntity("${it + 1}", it + 1)
        }

        dayDao.insert(day1)
        dayDao.insert(day2)
        tags.forEach { tag ->
            tagDao.insert(tag)
            dayTagJoinDao.insert(DayTagJoin(day1.id, tag.id))
            dayTagJoinDao.insert(DayTagJoin(day2.id, tag.id))
        }

        val expected = tags.flatMap { tag ->
            listOf(DayWithTagEntity(day1.date, day1.description, day1.grade, tag.tag),
                DayWithTagEntity(day2.date, day2.description, day2.grade, tag.tag))
        }

        dayTagJoinDao.getDaysWithTags().observeOnce {
            Log.i("expected", expected.toString())
            Log.i("expected", it.toString())

            assertEquals(true, expected.containsAll(it))
            assertEquals(true, it.containsAll(expected))
        }
    }
}