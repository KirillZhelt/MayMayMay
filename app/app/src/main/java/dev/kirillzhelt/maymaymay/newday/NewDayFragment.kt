package dev.kirillzhelt.maymaymay.newday

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.datepicker.MaterialDatePicker
import dev.kirillzhelt.maymaymay.MainApplication
import dev.kirillzhelt.maymaymay.R

/**
 * A simple [Fragment] subclass.
 */
class NewDayFragment: Fragment() {

    private val newDayViewModel: NewDayViewModel by viewModels { NewDayViewModelFactory(MainApplication.daysRepository) }

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

        gradeNumberPicker.setOnValueChangedListener(this::onGradeNumberPickerValueChanged)

        return inflatedView
    }

    private fun showDatePickerDialog(view: View) {
        val datePickerBuilder: MaterialDatePicker.Builder<Long> = MaterialDatePicker.Builder.datePicker()
        datePickerBuilder.setTitleText(R.string.date_picker_title)

        val picker = datePickerBuilder.build()
        picker.addOnPositiveButtonClickListener(newDayViewModel::onDatePicked)

        picker.show(fragmentManager!!, "datePicker")

        // TODO: resize edit text when to show all text, maybe make it scrollable
        // TODO: add button, when button is pressed, save day and go back to days list
    }

    private fun onGradeNumberPickerValueChanged(numberPicker: NumberPicker, oldValue: Int, newValue: Int) {
        newDayViewModel.onGradePicked(newValue)
    }
}
