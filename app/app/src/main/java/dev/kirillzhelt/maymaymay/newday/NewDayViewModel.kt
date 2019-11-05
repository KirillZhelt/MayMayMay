package dev.kirillzhelt.maymaymay.newday

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dev.kirillzhelt.maymaymay.daysmodel.DaysRepository
import java.text.SimpleDateFormat
import java.util.*

class NewDayViewModel(private val daysRepository: DaysRepository): ViewModel() {

    private val _pickedDate = MutableLiveData<Date>(Date(System.currentTimeMillis()))

    val formattedPickedDate: LiveData<String> = Transformations.map(_pickedDate) { date ->
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)

        dateFormat.format(date)
    }

    fun onDatePicked(selection: Long) {
        _pickedDate.value = Date(selection)

        Log.i("Date picked", _pickedDate.value.toString())
    }

}