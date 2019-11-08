package dev.kirillzhelt.maymaymay.daysmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.kirillzhelt.maymaymay.daysmodel.db.daos.DayDao
import dev.kirillzhelt.maymaymay.daysmodel.db.daos.DayTagJoinDao
import dev.kirillzhelt.maymaymay.daysmodel.db.daos.TagDao
import dev.kirillzhelt.maymaymay.daysmodel.db.entities.DayEntity
import dev.kirillzhelt.maymaymay.daysmodel.db.entities.DayTagJoin
import dev.kirillzhelt.maymaymay.daysmodel.db.entities.TagEntity
import java.text.SimpleDateFormat
import java.util.*

class DaysRepository(private val dayDao: DayDao, private val tagDao: TagDao,
                     private val dayTagJoinDao: DayTagJoinDao) {

    suspend fun addNewDay(day: Day) {
        val dayEntity = DayEntity(day.date, day.description, day.grade, day.id ?: 0)

        dayDao.insert(dayEntity)
        val dayId = dayDao.getDayId(day.date)

        if (dayId != null) {
            val dayTagJoins = tagDao.getTagIds(day.tags).map { tagId -> DayTagJoin(dayId, tagId) }

            dayTagJoinDao.insert(dayTagJoins)
        }
    }

    suspend fun deleteDay(day: Day) {
        dayDao.deleteByDate(day.date)
    }

    fun getAllDays(): LiveData<List<Day>> {
        throw NotImplementedError()
    }

    fun addNewTag(tag: String) {
        throw NotImplementedError()
    }

    fun deleteTag(tag: String): Boolean {
        throw NotImplementedError()
    }

    fun getAllTags(): List<String> {
        throw NotImplementedError()
    }

}