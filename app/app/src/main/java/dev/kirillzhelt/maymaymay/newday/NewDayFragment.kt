package dev.kirillzhelt.maymaymay.newday

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import dev.kirillzhelt.maymaymay.MainApplication
import dev.kirillzhelt.maymaymay.R
import dev.kirillzhelt.maymaymay.utils.findCheckedChipTexts
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class NewDayFragment: Fragment() {

    private val newDayViewModel: NewDayViewModel
            by viewModels({ requireActivity() }) { NewDayViewModelFactory(MainApplication.daysRepository) }

    private lateinit var tagsChipGroup: ChipGroup
    private lateinit var descriptionEditText: EditText
    private lateinit var datePickerDialog: DatePickerDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflatedView = inflater.inflate(R.layout.fragment_new_day, container, false)

        val chooseDateButton: Button = inflatedView.findViewById(R.id.fragment_new_day_choose_date_btn)
        chooseDateButton.setOnClickListener {
            datePickerDialog.show(fragmentManager!!, "DatePickerDialog")
        }

        val addDayButton: Button = inflatedView.findViewById(R.id.fragment_day_info_grade_title_tv)

        val dateTextView: TextView = inflatedView.findViewById(R.id.fragment_new_day_date_tv)
        newDayViewModel.formattedPickedDate.observe(this, Observer { formattedDate ->
            if (formattedDate != null) {
                dateTextView.text = formattedDate
                addDayButton.isEnabled = true
            } else {
                dateTextView.text = getString(R.string.date_not_chosen)
                addDayButton.isEnabled = false
            }
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

        tagsChipGroup = inflatedView.findViewById(R.id.fragment_day_info_tags_cg)

        newDayViewModel.tags.observe(this, Observer { tags ->
            tagsChipGroup.removeAllViews()

            tags.forEach { tag ->
                val chip = Chip(requireContext()).apply {
                    text = tag.first
                }

                val chipDrawable = ChipDrawable.createFromAttributes(requireContext(),
                    null, 0, R.style.Widget_MaterialComponents_Chip_Filter)
                chip.setChipDrawable(chipDrawable)
                chip.isChecked = tag.second

                tagsChipGroup.addView(chip)
            }
        })

        addDayButton.setOnClickListener { view ->
            if (descriptionEditText.text.isEmpty()) {
                descriptionEditText.error = getString(R.string.description_empty_error)
            } else {
                descriptionEditText.error = null

                saveStateInViewModel()

                newDayViewModel.addNewDay()

                findNavController().navigate(NewDayFragmentDirections.actionNewDayFragmentToDaysListFragment())
            }
        }

        descriptionEditText = inflatedView.findViewById(R.id.fragment_new_day_description_et)
        newDayViewModel.description.observe(this, Observer { description ->
            descriptionEditText.setText(description, TextView.BufferType.EDITABLE)
        })

        datePickerDialog = DatePickerDialog.newInstance { _, year, monthOfYear, dayOfMonth ->
            newDayViewModel.onDatePicked(year, monthOfYear, dayOfMonth)
        }

        datePickerDialog.isThemeDark = false
        datePickerDialog.showYearPickerFirst(false)
        datePickerDialog.setTitle(getString(R.string.date_picker_title))
        datePickerDialog.setOkColor(Color.WHITE)
        datePickerDialog.setCancelColor(Color.WHITE)

        datePickerDialog.maxDate = Calendar.getInstance()

        inflatedView.findViewById<Button>(R.id.fragment_new_day_navigate_smile_fragment_btn).setOnClickListener {
            newDayViewModel.onNavigateSmileDetection()
        }

        newDayViewModel.navigateSmileDetection.observe(this, Observer { navigate ->
            if (navigate) {
                saveStateInViewModel()

                findNavController().navigate(NewDayFragmentDirections.actionNewDayFragmentToSmileDetectionFragment())

                newDayViewModel.onNavigateSmileDetectionComplete()
            }
        })

        newDayViewModel.dates.observe(this, Observer { dates ->
            datePickerDialog.disabledDays = dates.toTypedArray()
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
            saveCheckedTags(tagsChipGroup.findCheckedChipTexts())
        }
    }
}
