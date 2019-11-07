package dev.kirillzhelt.maymaymay.newday

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.google.android.material.datepicker.MaterialDatePicker
import dev.kirillzhelt.maymaymay.MainApplication
import dev.kirillzhelt.maymaymay.R
import dev.kirillzhelt.maymaymay.utils.findCheckedChipTexts
import kotlinx.android.synthetic.main.fragment_new_day.*

/**
 * A simple [Fragment] subclass.
 */
class NewDayFragment: Fragment() {

    private val newDayViewModel: NewDayViewModel by viewModels { NewDayViewModelFactory(MainApplication.daysRepository) }

    private lateinit var tagsChipGroup: ChipGroup
    private lateinit var descriptionEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflatedView = inflater.inflate(R.layout.fragment_new_day, container, false)

        val chooseDateButton: Button = inflatedView.findViewById(R.id.fragment_new_day_choose_date_btn)
        chooseDateButton.setOnClickListener(this::showDatePickerDialog)

        val dateTextView: TextView = inflatedView.findViewById(R.id.fragment_new_day_date_tv)
        newDayViewModel.formattedPickedDate.observe(this, Observer { formattedDate ->
            dateTextView.text = formattedDate
        })

        val gradeNumberPicker: NumberPicker = inflatedView.findViewById(R.id.fragment_new_day_grade_np)
        newDayViewModel.minGradeValue.observe(this, Observer { minGrade ->
            gradeNumberPicker.minValue = minGrade
        })

        newDayViewModel.maxGradeValue.observe(this, Observer { maxGrade ->
            gradeNumberPicker.maxValue = maxGrade
        })

        newDayViewModel.pickedGrade.observe(this, Observer { grade ->
            gradeNumberPicker.value = grade
        })

        gradeNumberPicker.setOnValueChangedListener { _, _, newValue ->
            newDayViewModel.onGradePicked(newValue)
        }

        tagsChipGroup = inflatedView.findViewById(R.id.fragment_new_day_tags_cg)

        newDayViewModel.tags.observe(this, Observer { tags ->
            tagsChipGroup.removeAllViews()

            tags.forEach { tag ->
                val chip = Chip(requireContext()).apply {
                    text = tag
                }

                val chipDrawable = ChipDrawable.createFromAttributes(requireContext(), null, 0, R.style.Widget_MaterialComponents_Chip_Filter)
                chip.setChipDrawable(chipDrawable)

                tagsChipGroup.addView(chip)
            }
        })

        val addDayButton: Button = inflatedView.findViewById(R.id.fragment_new_day_add_btn)
        addDayButton.setOnClickListener { view ->
            saveStateInViewModel()

            newDayViewModel.addNewDay()
        }

        newDayViewModel.checkedTagIds.observe(this, Observer { checkedTagIds ->
            checkedTagIds.forEach { tagId ->
                tagsChipGroup.check(tagId)
            }
        })

        descriptionEditText = inflatedView.findViewById(R.id.fragment_new_day_description_et)
        newDayViewModel.description.observe(this, Observer { description ->
            descriptionEditText.setText(description, TextView.BufferType.EDITABLE)
        })

        return inflatedView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        saveStateInViewModel()
    }

    private fun saveStateInViewModel() {
        newDayViewModel.apply {
            saveDescription(descriptionEditText.text.toString())
            saveCheckedTags(tagsChipGroup.checkedChipIds)
        }
    }

    private fun showDatePickerDialog(view: View) {
        val datePickerBuilder: MaterialDatePicker.Builder<Long> = MaterialDatePicker.Builder.datePicker()
        datePickerBuilder.setTitleText(R.string.date_picker_title)

        val picker = datePickerBuilder.build()
        picker.addOnPositiveButtonClickListener(newDayViewModel::onDatePicked)

        picker.show(fragmentManager!!, "datePicker")
    }
}
