package dev.kirillzhelt.maymaymay.dayslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kirillzhelt.maymaymay.daysmodel.Day
import dev.kirillzhelt.maymaymay.daysmodel.DaysRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DaysListViewModel(private val daysRepository: DaysRepository): ViewModel() {
    val days: LiveData<List<Day>> = daysRepository.getAllDays()

    private val _navigateDayInfo = MutableLiveData<Day?>(null)

    val navigateDayInfo: LiveData<Day?> = _navigateDayInfo

    fun deleteDay(day: Day) {
        viewModelScope.launch(Dispatchers.IO) {
            daysRepository.deleteDay(day)
        }
    }

    fun onNavigateDayInfo(day: Day) {
        _navigateDayInfo.value = day
    }

    fun onNavigateDayInfoComplete() {
        _navigateDayInfo.value = null
    }
}