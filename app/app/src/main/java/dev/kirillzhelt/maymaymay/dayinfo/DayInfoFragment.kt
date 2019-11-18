package dev.kirillzhelt.maymaymay.dayinfo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dev.kirillzhelt.maymaymay.MainApplication
import dev.kirillzhelt.maymaymay.R
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class DayInfoFragment : Fragment() {

    private val dayInfoViewModel: DayInfoViewModel by viewModels {
        DayInfoViewModelFactory(MainApplication.daysRepository)
    }

    private val args: DayInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day_info, container, false)
    }
}
