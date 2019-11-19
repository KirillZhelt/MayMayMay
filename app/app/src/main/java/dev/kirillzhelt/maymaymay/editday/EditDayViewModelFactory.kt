package dev.kirillzhelt.maymaymay.editday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.kirillzhelt.maymaymay.daysmodel.DaysRepository
import java.util.*

class EditDayViewModelFactory(private val date: Date, private val daysRepository: DaysRepository): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditDayViewModel::class.java)) {
            return EditDayViewModel(date, daysRepository) as T
        }

        throw IllegalArgumentException("modelClass is not assignable from EditDayViewModel")
    }
}