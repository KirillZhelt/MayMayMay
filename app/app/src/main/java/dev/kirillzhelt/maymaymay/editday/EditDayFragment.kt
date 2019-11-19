package dev.kirillzhelt.maymaymay.editday


import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import dev.kirillzhelt.maymaymay.MainApplication
import dev.kirillzhelt.maymaymay.R
import dev.kirillzhelt.maymaymay.daysmodel.Day
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class EditDayFragment : Fragment() {

    private val args: EditDayFragmentArgs by navArgs()

    private val editDayViewModel: EditDayViewModel by viewModels {
        EditDayViewModelFactory(args.day.date, MainApplication.daysRepository)
    }

    private lateinit var descriptionEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflatedView = inflater.inflate(R.layout.fragment_edit_day, container, false)

        val dateTextView: TextView = inflatedView.findViewById(R.id.fragment_edit_day_date_tv)
        val tagsChipGroup: ChipGroup = inflatedView.findViewById(R.id.fragment_edit_day_tags_cg)
        val gradeTextView: TextView = inflatedView.findViewById(R.id.fragment_edit_day_grade_title_tv)
        val saveDayButton: Button = inflatedView.findViewById(R.id.fragment_edit_day_save_day_btn)

        descriptionEditText = inflatedView.findViewById(R.id.fragment_edit_day_description_et)

        val day = args.day

        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
        dateTextView.text = dateFormat.format(day.date)

        descriptionEditText.setText(day.description)

        tagsChipGroup.removeAllViews()
        day.tags.forEach { tagText ->
            val chip = Chip(requireContext()).apply {
                text = tagText
            }

            val chipDrawable = ChipDrawable.createFromAttributes(requireContext(), null, 0,
                R.style.Widget_MaterialComponents_Chip_Filter)
            chip.setChipDrawable(chipDrawable)
            chip.isCheckable = false

            tagsChipGroup.addView(chip)
        }

        gradeTextView.text = day.grade.grade.toString()

        editDayViewModel.description.observe(this, Observer { description ->
            descriptionEditText.setText(description)
        })

        return inflatedView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        editDayViewModel.saveDescription(descriptionEditText.text.toString())
    }
}
