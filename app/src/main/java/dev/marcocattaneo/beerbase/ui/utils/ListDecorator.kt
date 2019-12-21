package dev.marcocattaneo.beerbase.ui.utils

import android.graphics.Rect
import android.view.View
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView

class ListDecorator(private val spacing: Int, private val spacingTop: Int, private val colspan: Int) : RecyclerView.ItemDecoration() {

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

        if (position < colspan) {
            outRect.top = spacingTop
        } else {
            outRect.top = halfSpacing
        }

        if (position % colspan != 0) {
            outRect.left = spacing
            outRect.right = spacing
        } else {
            outRect.right = spacing
            outRect.left = spacing
        }
        outRect.bottom = halfSpacing
    }

}