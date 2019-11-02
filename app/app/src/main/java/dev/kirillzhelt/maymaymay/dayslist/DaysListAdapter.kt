package dev.kirillzhelt.maymaymay.dayslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.kirillzhelt.maymaymay.R
import dev.kirillzhelt.maymaymay.daysmodel.Day
import java.text.SimpleDateFormat
import java.util.*

class DaysListAdapter: RecyclerView.Adapter<DaysListAdapter.DaysListViewHolder>() {

    var days: List<Day> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysListViewHolder {
        return DaysListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_item_day, parent, false))
    }

    override fun getItemCount(): Int {
        return days.size
    }

    override fun onBindViewHolder(holder: DaysListViewHolder, position: Int) {
        holder.bind(days[position])
    }

    class DaysListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val dateTextView: TextView = itemView.findViewById(R.id.rv_item_day_date_tv)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.rv_item_day_description_tv)
        private val gradeTextView: TextView = itemView.findViewById(R.id.rv_item_day_grade_tv)

        fun bind(day: Day) {
            val dateFormat = SimpleDateFormat("EEE, MMM d, yyyy", Locale.US)

            dateTextView.text = dateFormat.format(day.date)
            descriptionTextView.text = day.description
            gradeTextView.text = day.grade.grade.toString()
        }
    }

}