package com.example.githubsearch.viewpager

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.viewpager.UserListAdapter.Companion.VIEW_TYPE_USER

class CustomDividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 1f
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount

        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            val viewHolderType = parent.getChildViewHolder(child).itemViewType

            if (viewHolderType == VIEW_TYPE_USER) {
                val params = child.layoutParams as RecyclerView.LayoutParams
                val left = child.left + params.leftMargin.toFloat()
                val right = child.right - params.rightMargin.toFloat()
                val top = (child.bottom + params.bottomMargin).toFloat()

                c.drawLine(left, top, right, top, paint)
            }
        }
    }
}
