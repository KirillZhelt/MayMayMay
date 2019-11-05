package dev.kirillzhelt.maymaymay.newday

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.kirillzhelt.maymaymay.daysmodel.DaysRepository
import java.util.*

class NewDayViewModel(private val daysRepository: DaysRepository): ViewModel() {

    private val _pickedDate = MutableLiveData<Date>(Date(System.currentTimeMillis()))

    val pickedDate: LiveData<Date> = _pickedDate

    fun onDatePicked(selection: Long) {
        _pickedDate.value = Date(selection)

        Log.i("Date picked", _pickedDate.value.toString())
    }

}