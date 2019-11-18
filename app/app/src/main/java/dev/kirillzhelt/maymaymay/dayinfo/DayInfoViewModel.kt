package dev.kirillzhelt.maymaymay.dayinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kirillzhelt.maymaymay.daysmodel.Day
import dev.kirillzhelt.maymaymay.daysmodel.DaysRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class DayInfoViewModel(day: Day, daysRepository: DaysRepository): ViewModel() {

    val day: LiveData<Day> = daysRepository.getDay(day.date)

}