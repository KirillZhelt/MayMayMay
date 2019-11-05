package dev.kirillzhelt.maymaymay.newday


import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import androidx.fragment.app.viewModels
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

        chooseDateButton.setOnClickListener { view ->
            showDatePickerDialog(view)
        }

        return inflatedView
    }

    private fun showDatePickerDialog(view: View) {
        val datePickerBuilder: MaterialDatePicker.Builder<Long> = MaterialDatePicker.Builder.datePicker()
        datePickerBuilder.setTitleText("Hello world")

        val picker = datePickerBuilder.build()
        picker.show(fragmentManager!!, "datePicker")

    }


}
