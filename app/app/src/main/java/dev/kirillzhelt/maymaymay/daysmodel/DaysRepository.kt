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
import dev.kirillzhelt.maymaymay.daysmodel.db.entities.DayWithTagEntity
import dev.kirillzhelt.maymaymay.daysmodel.db.entities.TagEntity
import java.text.SimpleDateFormat
import java.util.*

class DaysRepository(private val dayDao: DayDao, private val tagDao: TagDao,
                     private val dayTagJoinDao: DayTagJoinDao) {

    suspend fun addNewDay(day: Day) {
        val dayEntity = DayEntity(day.date, day.description, day.grade)

        dayDao.insert(dayEntity)
        val dayId = dayDao.getDayId(day.date)

        if (dayId != null && day.tags.isNotEmpty()) {
            val dayTagJoins = tagDao.getTagIds(day.tags).map { tagId -> DayTagJoin(dayId, tagId) }

            dayTagJoinDao.insert(dayTagJoins)
        }
    }

    suspend fun updateDay(day: Day) {
        val dayId = dayDao.getDayId(day.date)

        if (dayId != null) {
            val dayEntity = DayEntity(day.date, day.description, day.grade, dayId)

            dayDao.update(dayEntity)

            if (day.tags.isNotEmpty()) {
                dayTagJoinDao.deleteTagsForDayByDayId(dayId)

                val dayTagJoins = tagDao.getTagIds(day.tags).map { tagId -> DayTagJoin(dayId, tagId) }
                dayTagJoinDao.insert(dayTagJoins)
            }
        }
    }

    suspend fun deleteDay(day: Day) {
        dayDao.deleteByDate(day.date)
    }

    fun getAllDays(): LiveData<List<Day>> {
        return Transformations.map(dayTagJoinDao.getDaysWithTags()) {
                daysWithTags ->
            val groupedByDateDays = daysWithTags.groupBy { it.date }

            groupedByDateDays.values.map { day ->
                val date = day[0].date
                val description = day[0].description
                val grade = day[0].grade

                val tags = day.map { it.tag ?: "" }

                Day(date, description, grade, tags)
            }
        }
    }

    fun getDay(date: Date): LiveData<Day> {
        return Transformations.map(dayTagJoinDao.getDayWithTagsByDate(date)) { daysWithTags ->
            Day(daysWithTags[0].date, daysWithTags[0].description, daysWithTags[0].grade, daysWithTags.mapNotNull { it.tag })
        }
    }

    fun getAllDates(): LiveData<List<Date>> {
        return dayDao.getDates()
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