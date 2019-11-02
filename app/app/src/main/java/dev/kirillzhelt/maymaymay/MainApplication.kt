package dev.kirillzhelt.maymaymay

import android.app.Application
import dev.kirillzhelt.maymaymay.daysmodel.DaysRepository

class MainApplication: Application() {

    companion object {
        val daysRepository: DaysRepository by lazy {
            DaysRepository()
        }
    }

}