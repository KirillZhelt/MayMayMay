package dev.kirillzhelt.maymaymay.newday

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dev.kirillzhelt.maymaymay.daysmodel.DayGrade
import dev.kirillzhelt.maymaymay.daysmodel.DaysRepository
import dev.kirillzhelt.maymaymay.daysmodel.exceptions.NoGradesException
import java.text.SimpleDateFormat
import java.util.*

class NewDayViewModel(private val daysRepository: DaysRepository): ViewModel() {

    private val _pickedDate = MutableLiveData<Date>(Date(System.currentTimeMillis()))

    val formattedPickedDate: LiveData<String> = Transformations.map(_pickedDate) { date ->
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)

        dateFormat.format(date)
    }

    val minGradeValue: LiveData<Int>
    val maxGradeValue: LiveData<Int>

    private val _pickedGrade = MutableLiveData<Int>()

    val pickedGrade: LiveData<Int> = _pickedGrade

    init {
        val gradeValues = DayGrade.values().map { dayGrade -> dayGrade.grade }

        minGradeValue = MutableLiveData(gradeValues.min() ?: throw NoGradesException())
        maxGradeValue = MutableLiveData(gradeValues.max() ?: throw NoGradesException())

        _pickedGrade.value = maxGradeValue.value
    }

    fun onDatePicked(selection: Long) {
        _pickedDate.value = Date(selection)

        Log.i("Date picked", _pickedDate.value.toString())
    }

    fun onGradePicked(grade: Int) {
        _pickedGrade.value = grade

        Log.i("Grade picked", _pickedGrade.value.toString())
    }
}