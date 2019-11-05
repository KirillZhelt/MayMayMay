package dev.kirillzhelt.maymaymay.dayslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.kirillzhelt.maymaymay.daysmodel.Day
import dev.kirillzhelt.maymaymay.daysmodel.DaysRepository

class DaysListViewModel(private val daysRepository: DaysRepository): ViewModel() {
    val days: LiveData<List<Day>> = daysRepository.getAllDays()

}