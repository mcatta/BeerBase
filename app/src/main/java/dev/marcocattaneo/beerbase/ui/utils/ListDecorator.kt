package dev.marcocattaneo.beerbase.ui.utils

import android.graphics.Rect
import android.view.View
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView

class ListDecorator(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)

        if (position == -1) {
            return
        }

        val halfSpacing = spacing / 2

        when (position) {
            0 -> {
                outRect.top = spacing
                outRect.bottom = halfSpacing
            }
            (parent.size - 1) -> {
                outRect.bottom = halfSpacing
                outRect.bottom = spacing
            }
            else -> {
                outRect.bottom = spacing
                outRect.bottom = spacing
            }
        }
        outRect.left = spacing
        outRect.right = spacing
    }

}