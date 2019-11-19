package dev.kirillzhelt.maymaymay.editday

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.kirillzhelt.maymaymay.daysmodel.DaysRepository
import java.util.*

class EditDayViewModel(private val date: Date, private val daysRepository: DaysRepository): ViewModel() {

    private val _description = MutableLiveData<String>()

    val description: LiveData<String> = _description

    fun saveDescription(description: String) {
        _description.value = description
    }

}