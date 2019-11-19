package dev.kirillzhelt.maymaymay.editday

import androidx.lifecycle.*
import dev.kirillzhelt.maymaymay.daysmodel.Day
import dev.kirillzhelt.maymaymay.daysmodel.DaysRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditDayViewModel(private val dayToEdit: Day, private val daysRepository: DaysRepository): ViewModel() {

    private val _description = MutableLiveData<String>()

    val description: LiveData<String> = _description

    private val _tags: LiveData<List<String>> = daysRepository.getAllTags()
    private val _checkedTags = MutableLiveData<List<String>>(dayToEdit.tags)

    val tags = MediatorLiveData<List<Pair<String, Boolean>>>()

    init {
        tags.addSource(_tags) { newTags ->
            tags.value = newTags.map { newTag ->
                Pair(newTag, _checkedTags.value?.contains(newTag) ?: false)
            }
        }

        tags.addSource(_checkedTags) { newCheckedTags ->
            tags.value = tags.value?.map { tag ->
                Pair(tag.first, newCheckedTags.contains(tag.first))
            } ?: emptyList()
        }
    }

    fun saveDescription(description: String) {
        _description.value = description
    }

    fun saveCheckedTags(tags: List<String>) {
        _checkedTags.value = tags
    }

    fun updateDay() {
        val day = Day(dayToEdit.date, _description.value!!, dayToEdit.grade, _checkedTags.value!!)

        viewModelScope.launch(Dispatchers.IO) {
            daysRepository.updateDay(day)
        }
    }
}