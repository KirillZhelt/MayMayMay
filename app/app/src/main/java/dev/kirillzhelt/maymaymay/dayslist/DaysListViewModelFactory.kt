package dev.kirillzhelt.maymaymay.dayslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.kirillzhelt.maymaymay.daysmodel.DaysRepository
import java.lang.IllegalArgumentException

class DaysListViewModelFactory(private val daysRepository: DaysRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DaysListViewModel::class.java)) {
            return DaysListViewModel(daysRepository) as T
        }

        throw IllegalArgumentException("modelClass is not assignable from DaysListViewModel")

    }
}