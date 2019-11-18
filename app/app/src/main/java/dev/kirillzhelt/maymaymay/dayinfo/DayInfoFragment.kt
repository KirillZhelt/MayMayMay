package dev.kirillzhelt.maymaymay.dayinfo

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
class DayInfoFragment : Fragment() {

    private val args: DayInfoFragmentArgs by navArgs()

    private val dayInfoViewModel: DayInfoViewModel by viewModels {
        DayInfoViewModelFactory(args.day, MainApplication.daysRepository)
    }

    private lateinit var dateTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var tagsChipGroup: ChipGroup
    private lateinit var gradeTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflatedView = inflater.inflate(R.layout.fragment_day_info, container, false)

        dateTextView = inflatedView.findViewById(R.id.fragment_day_info_date_tv)
        descriptionTextView = inflatedView.findViewById(R.id.fragment_day_info_description_tv)
        tagsChipGroup = inflatedView.findViewById(R.id.fragment_day_info_tags_cg)
        gradeTextView = inflatedView.findViewById(R.id.day_info_fragment_grade_tv)

        bind(args.day)

        dayInfoViewModel.day.observe(this, Observer { day ->
            bind(day)
        })

        return inflatedView
    }

    private fun bind(day: Day) {
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
        dateTextView.text = dateFormat.format(day.date)

        descriptionTextView.text = day.description

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
    }
}
