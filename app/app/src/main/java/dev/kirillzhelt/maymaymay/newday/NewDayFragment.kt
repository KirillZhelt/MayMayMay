package dev.kirillzhelt.maymaymay.newday

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
        newDayViewModel.pickedDate.observe(this, Observer { date ->
            dateTextView.text = date.toString()
        })

        return inflatedView
    }

    private fun showDatePickerDialog(view: View) {
        val datePickerBuilder: MaterialDatePicker.Builder<Long> = MaterialDatePicker.Builder.datePicker()
        datePickerBuilder.setTitleText(R.string.date_picker_title)

        val picker = datePickerBuilder.build()
        picker.addOnPositiveButtonClickListener(newDayViewModel::onDatePicked)

        picker.show(fragmentManager!!, "datePicker")
    }


}
