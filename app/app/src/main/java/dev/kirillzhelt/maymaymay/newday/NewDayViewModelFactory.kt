package dev.kirillzhelt.maymaymay.newday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.kirillzhelt.maymaymay.daysmodel.DaysRepository
import java.lang.IllegalArgumentException

class NewDayViewModelFactory(private val daysRepository: DaysRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewDayViewModel::class.java)) {
            return NewDayViewModel(daysRepository) as T
        }

        throw IllegalArgumentException("modelClass is not assignable from NewDayViewModel")
    }
}