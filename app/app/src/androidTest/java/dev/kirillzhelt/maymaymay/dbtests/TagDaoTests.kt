package dev.kirillzhelt.maymaymay.dbtests

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.kirillzhelt.maymaymay.daysmodel.db.DayRoomDatabase
import dev.kirillzhelt.maymaymay.daysmodel.db.daos.TagDao
import dev.kirillzhelt.maymaymay.daysmodel.db.entities.TagEntity
import dev.kirillzhelt.maymaymay.utils.observeOnce
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TagDaoTests {

    private lateinit var db: DayRoomDatabase
    private lateinit var tagDao: TagDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, DayRoomDatabase::class.java).build()

        tagDao = db.tagDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertTagAndReadInList() = runBlocking {
        val tag = TagEntity("tag", 1)

        tagDao.insert(tag)

        tagDao.getTags().observeOnce {
            assertEquals(tag, it[0])
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertManyTagsAndReadInList() = runBlocking {
        val tags = mutableListOf<TagEntity>()

        for (i in 0..5) {
            tags.add(TagEntity("$i", i + 1))
        }

        for (tag in tags) {
            tagDao.insert(tag)
        }

        tagDao.getTags().observeOnce {
            assertEquals(it, tags)
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertTwoTagsWithTheSameString() = runBlocking {
        val tag1 = TagEntity("tag", 1)
        val tag2 = TagEntity("tag", 2)

        tagDao.insert(tag1)
        tagDao.insert(tag2)

        tagDao.getTags().observeOnce {
            assertEquals(1, it.size)
            assertEquals(tag1, it[0])
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertManyTagsAndGetTheirIdsByStrings() = runBlocking {
        val tags = mutableListOf<TagEntity>()

        for (i in 0..5) {
            tags.add(TagEntity("$i", i + 1))
        }

        for (tag in tags) {
            tagDao.insert(tag)
        }

        tagDao.getTagIds(listOf("0", "2")).observeOnce {
            assertEquals(listOf(1, 3), it)
        }
    }
}