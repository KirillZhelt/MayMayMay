package dev.kirillzhelt.maymaymay

import android.app.Application
import dev.kirillzhelt.maymaymay.daysmodel.DaysRepository
import dev.kirillzhelt.maymaymay.daysmodel.db.DayRoomDatabase

class MainApplication: Application() {

    companion object {
        lateinit var dayRoomDatabase: DayRoomDatabase

        val daysRepository: DaysRepository by lazy {
            DaysRepository()
        }
    }

    override fun onCreate() {
        super.onCreate()

        dayRoomDatabase = DayRoomDatabase.getDatabase(this)
    }
}