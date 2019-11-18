package dev.kirillzhelt.maymaymay.dayslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.kirillzhelt.maymaymay.daysmodel.Day
import dev.kirillzhelt.maymaymay.daysmodel.DaysRepository

class DaysListViewModel(private val daysRepository: DaysRepository): ViewModel() {
    val days: LiveData<List<Day>> = daysRepository.getAllDays()

    private val _navigateDayInfo = MutableLiveData<Day?>(null)

    val navigateDayInfo: LiveData<Day?> = _navigateDayInfo

    fun onNavigateDayInfo(day: Day) {
        _navigateDayInfo.value = day
    }

    fun onNavigateDayInfoComplete() {
        _navigateDayInfo.value = null
    }
}