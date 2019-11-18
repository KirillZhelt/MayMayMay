package dev.kirillzhelt.maymaymay.dayslist

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dev.kirillzhelt.maymaymay.daysmodel.Day

class SwipeToDeleteCallback(private val onSwipedListener: (Day) -> Unit): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val dayViewHolder = viewHolder as DaysListAdapter.DaysListViewHolder

        dayViewHolder.currentDay?.let { day -> onSwipedListener(day) }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }
}