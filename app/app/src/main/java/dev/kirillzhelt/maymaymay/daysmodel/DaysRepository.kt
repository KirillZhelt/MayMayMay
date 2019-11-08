package dev.kirillzhelt.maymaymay.daysmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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

    private val _days = MediatorLiveData<List<Day>>()

    init {
        _days.addSource(dayDao.getDays()) { dayEntities ->
            _days.value = dayEntities.map { dayEntity ->
                Day(dayEntity.date, dayEntity.description, dayEntity.grade, listOf())
            }
        }
    }

    suspend fun addNewDay(day: Day) {
        val dayEntity = DayEntity(day.date, day.description, day.grade)

        dayDao.insert(dayEntity)
        val dayId = dayDao.getDayId(day.date)

        if (dayId != null && day.tags.isNotEmpty()) {
            val dayTagJoins = tagDao.getTagIds(day.tags).map { tagId -> DayTagJoin(dayId, tagId) }

            dayTagJoinDao.insert(dayTagJoins)
        }
    }

    suspend fun deleteDay(day: Day) {
        dayDao.deleteByDate(day.date)
    }

    fun getAllDays(): LiveData<List<Day>> {
        return _days
    }

    fun addNewTag(tag: String) {
        throw NotImplementedError()
    }

    fun deleteTag(tag: String) {
        throw NotImplementedError()
    }

    fun getAllTags(): LiveData<List<String>> {
        return Transformations.map(tagDao.getTags()) { it.map { it.tag } }
    }

}