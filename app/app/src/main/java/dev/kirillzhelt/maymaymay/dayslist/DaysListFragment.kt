package dev.kirillzhelt.maymaymay.dayslist


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import dev.kirillzhelt.maymaymay.MainApplication
import dev.kirillzhelt.maymaymay.R

/**
 * A simple [Fragment] subclass.
 */
class DaysListFragment : Fragment() {

    private lateinit var daysListViewModel: DaysListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_days_list, container, false)

        daysListViewModel = DaysListViewModel(MainApplication.daysRepository)

        val dayInfoButton: Button = view.findViewById(R.id.fragment_days_list_day_info_btn)
        val newDayButton: Button = view.findViewById(R.id.fragment_days_list_new_day_btn)

        dayInfoButton.setOnClickListener {
            val action = DaysListFragmentDirections.actionDaysListFragmentToDayInfoFragment()
            view.findNavController().navigate(action)
        }

        newDayButton.setOnClickListener {
            val action = DaysListFragmentDirections.actionDaysListFragmentToNewDayFragment()
            view.findNavController().navigate(action)
        }

        return view
    }


}
