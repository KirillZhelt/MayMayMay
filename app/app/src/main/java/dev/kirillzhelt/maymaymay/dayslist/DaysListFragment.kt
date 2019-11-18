package dev.kirillzhelt.maymaymay.dayslist


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.kirillzhelt.maymaymay.MainApplication
import dev.kirillzhelt.maymaymay.R

/**
 * A simple [Fragment] subclass.
 */
class DaysListFragment : Fragment() {

    private val daysListViewModel: DaysListViewModel by viewModels { DaysListViewModelFactory(MainApplication.daysRepository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflatedView = inflater.inflate(R.layout.fragment_days_list, container, false)

        val daysListRecyclerView: RecyclerView = inflatedView.findViewById(R.id.fragment_days_list_rv)

        val daysListAdapter = DaysListAdapter { day ->
            daysListViewModel.onNavigateDayInfo(day)
        }

        daysListRecyclerView.adapter = daysListAdapter

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback { day ->
            daysListViewModel.deleteDay(day)
        })

        itemTouchHelper.attachToRecyclerView(daysListRecyclerView)

        daysListViewModel.days.observe(this, Observer { days ->
            daysListAdapter.days = days
        })

        daysListViewModel.navigateDayInfo.observe(this, Observer { day ->
            day?.let {
                findNavController().navigate(DaysListFragmentDirections.actionDaysListFragmentToDayInfoFragment(it))
                daysListViewModel.onNavigateDayInfoComplete()
            }
        })

        val newDayFab: FloatingActionButton = inflatedView.findViewById(R.id.fragment_days_list_new_day_fab)
        newDayFab.setOnClickListener { view ->
            view.findNavController().navigate(DaysListFragmentDirections.actionDaysListFragmentToNewDayFragment())
        }

        return inflatedView
    }


}
