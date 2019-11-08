package dev.kirillzhelt.maymaymay.newday

import android.util.Log
import androidx.lifecycle.*
import dev.kirillzhelt.maymaymay.daysmodel.Day
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

    private val _tags: MutableLiveData<List<String>>
    private val _checkedTags = MutableLiveData<List<String>>(emptyList())

    val tags = MediatorLiveData<List<Pair<String, Boolean>>>()

    private val _description = MutableLiveData<String>()

    val description: LiveData<String> = _description

    init {
        val gradeValues = DayGrade.values().map { dayGrade -> dayGrade.grade }

        minGradeValue = MutableLiveData(gradeValues.min() ?: throw NoGradesException())
        maxGradeValue = MutableLiveData(gradeValues.max() ?: throw NoGradesException())

        _pickedGrade.value = maxGradeValue.value

        _tags = MutableLiveData(daysRepository.getAllTags())

        tags.addSource(_tags) { newTags ->
            tags.value = newTags.map { newTag ->
                Pair(newTag, _checkedTags.value?.contains(newTag) ?: false)
            }

            Log.i("Tags", "mediator: ${tags.value}")
        }

        tags.addSource(_checkedTags) { newCheckedTags ->
            tags.value = tags.value?.map { tag ->
                Pair(tag.first, newCheckedTags.contains(tag.first))
            }

            Log.i("Tags", "mediator: ${tags.value}")
        }
    }

    fun onDatePicked(selection: Long) {
        _pickedDate.value = Date(selection)

        Log.i("Date picked", _pickedDate.value.toString())
    }

    fun onGradePicked(grade: Int) {
        _pickedGrade.value = grade

        Log.i("Grade picked", _pickedGrade.value.toString())
    }

    fun saveCheckedTags(checkedTags: List<String>) {
        _checkedTags.value = checkedTags

        Log.i("Tags saved", _checkedTags.value.toString())
    }

    fun saveDescription(description: String) {
        _description.value = description
    }

    fun addNewDay() {
        Log.i("Add", "Day added")

        val day = Day(_pickedDate.value!!, _description.value!!, _checkedTags.value!!.toMutableList(),
            DayGrade.fromInt(_pickedGrade.value!!))

        // TODO: add day
//        daysRepository.addNewDay(day)

    }
}