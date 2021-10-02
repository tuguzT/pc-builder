package io.github.tuguzt.pcbuilder.presentation.view.components.decorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginDecoration(private val spaceSize: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) = outRect.run {
        if (parent.getChildAdapterPosition(view) == 0) {
            top = spaceSize
        }
        left = spaceSize
        right = spaceSize
        bottom = spaceSize
    }
}
