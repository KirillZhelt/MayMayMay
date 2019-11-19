package dev.kirillzhelt.maymaymay.editday

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.kirillzhelt.maymaymay.daysmodel.Day
import dev.kirillzhelt.maymaymay.daysmodel.DaysRepository
import java.util.*

class EditDayViewModel(private val day: Day, private val daysRepository: DaysRepository): ViewModel() {

    private val _description = MutableLiveData<String>()

    val description: LiveData<String> = _description

    private val _tags: LiveData<List<String>> = daysRepository.getAllTags()
    private val _checkedTags = MutableLiveData<List<String>>(day.tags)

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

}