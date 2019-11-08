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

    fun deleteDay(day: Day) {



    }

    fun getAllDays(): LiveData<List<Day>> {
        //return days
        return MutableLiveData<List<Day>>()
    }

    fun addNewTag(tag: String) {
//        tags.add(tag)
    }

    fun deleteTag(tag: String): Boolean {
//        return tags.remove(tag)
        return false
    }

    fun getAllTags(): List<String> {
//        return tags
        return listOf()
    }

}