package dev.kirillzhelt.maymaymay.dayslist

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dev.kirillzhelt.maymaymay.daysmodel.Day

class SwipeToDeleteCallback(private val icon: Drawable,
                            private val onSwipedListener: (Day) -> Unit):
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val background = ColorDrawable(Color.RED)

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView = viewHolder.itemView

        if (dX < 0) {
            background.setBounds(itemView.right + dX.toInt() - BACKGROUND_CORNER_OFFSET, itemView.top, itemView.right,
                itemView.bottom)

            val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
            val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
            val iconBottom = iconTop + icon.intrinsicHeight

            val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
            val iconRight = itemView.right - iconMargin

            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        }

        background.draw(c)

        icon.draw(c)
    }

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

    companion object {
        const val BACKGROUND_CORNER_OFFSET = 20
    }
}