package dev.kirillzhelt.maymaymay.newday


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import dev.kirillzhelt.maymaymay.MainApplication
import dev.kirillzhelt.maymaymay.R
import dev.kirillzhelt.maymaymay.datepicker.DatePickerDialogFragment

/**
 * A simple [Fragment] subclass.
 */
class NewDayFragment : Fragment() {

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
        val datePickerDialogFragment = DatePickerDialogFragment()
        datePickerDialogFragment.show(fragmentManager!!, "datePicker")
    }

}
