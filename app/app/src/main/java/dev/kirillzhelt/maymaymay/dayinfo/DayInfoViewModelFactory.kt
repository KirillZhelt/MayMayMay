package dev.kirillzhelt.maymaymay.dayinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.kirillzhelt.maymaymay.daysmodel.DaysRepository
import java.lang.IllegalArgumentException

class DayInfoViewModelFactory(private val daysRepository: DaysRepository): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DayInfoViewModel::class.java)) {
            return DayInfoViewModel(daysRepository) as T
        }

        throw IllegalArgumentException("modelClass is not assignable from DayInfoViewModel")

    }
}