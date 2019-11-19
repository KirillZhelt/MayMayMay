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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import dev.kirillzhelt.maymaymay.MainApplication
import dev.kirillzhelt.maymaymay.R
import dev.kirillzhelt.maymaymay.newday.NewDayFragmentDirections
import dev.kirillzhelt.maymaymay.utils.findCheckedChipTexts
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class EditDayFragment : Fragment() {

    private val args: EditDayFragmentArgs by navArgs()

    private val editDayViewModel: EditDayViewModel by viewModels {
        EditDayViewModelFactory(args.day, MainApplication.daysRepository)
    }

    private lateinit var descriptionEditText: EditText
    private lateinit var tagsChipGroup: ChipGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflatedView = inflater.inflate(R.layout.fragment_edit_day, container, false)

        val dateTextView: TextView = inflatedView.findViewById(R.id.fragment_edit_day_date_tv)
        val gradeTextView: TextView = inflatedView.findViewById(R.id.fragment_edit_day_grade_title_tv)
        val saveDayButton: Button = inflatedView.findViewById(R.id.fragment_edit_day_save_day_btn)

        descriptionEditText = inflatedView.findViewById(R.id.fragment_edit_day_description_et)
        tagsChipGroup = inflatedView.findViewById(R.id.fragment_edit_day_tags_cg)

        val day = args.day

        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
        dateTextView.text = dateFormat.format(day.date)

        descriptionEditText.setText(day.description)

        editDayViewModel.tags.observe(this, Observer { tags ->
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

        gradeTextView.text = day.grade.grade.toString()

        editDayViewModel.description.observe(this, Observer { description ->
            descriptionEditText.setText(description)
        })

        saveDayButton.setOnClickListener {
            if (descriptionEditText.text.isEmpty()) {
                descriptionEditText.error = getString(R.string.description_empty_error)
            } else {
                descriptionEditText.error = null

                saveStateInViewModel()

                editDayViewModel.updateDay()

                findNavController().navigateUp()
            }

            saveStateInViewModel()

            editDayViewModel.updateDay()
        }

        return inflatedView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        saveStateInViewModel()
    }

    private fun saveStateInViewModel() {
        editDayViewModel.apply {
            saveDescription(descriptionEditText.text.toString())
            saveCheckedTags(tagsChipGroup.findCheckedChipTexts())
        }
    }
}
