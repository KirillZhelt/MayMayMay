package dev.kirillzhelt.maymaymay.dayslist


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
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
        val view = inflater.inflate(R.layout.fragment_days_list, container, false)

        val daysListRecyclerView: RecyclerView = view.findViewById(R.id.fragment_days_list_rv)
        val daysListAdapter = DaysListAdapter()
        daysListRecyclerView.adapter = daysListAdapter

        daysListViewModel.days.observe(this, Observer { days ->
            daysListAdapter.days = days
        })

        return view
    }


}
