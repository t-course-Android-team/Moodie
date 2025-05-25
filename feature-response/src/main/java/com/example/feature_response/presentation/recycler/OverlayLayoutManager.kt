package com.example.feature_response.presentation.recycler

import android.content.Context
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OverlayLayoutManager(context: Context) : LinearLayoutManager(context) {

    private var animateRemovals = false
    private var recyclerViewWidth = 0
    private var recyclerViewHeight = 0

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        if (recycler == null || state?.itemCount == 0) return
        if (recyclerViewWidth == 0) {
            recyclerViewWidth = width - paddingLeft - paddingRight
            recyclerViewHeight = height - paddingTop - paddingBottom
        }
        detachAndScrapAttachedViews(recycler)
        for (i in itemCount - 1 downTo 0) {
            val view = recycler.getViewForPosition(i)
            addView(view)
            measureChildWithMargins(view, 0, 0)
            val width = getDecoratedMeasuredWidth(view)
            val height = getDecoratedMeasuredHeight(view)
            val left = (recyclerViewWidth - width) / 2
            val top = (recyclerViewHeight - height) / 2
            layoutDecorated(view, left, top, left + width, top + height)
            if (animateRemovals) {
                view.animate()
                    .translationX(i * 10f)
                    .translationY(i * 10f)
                    .setInterpolator(DecelerateInterpolator())
                    .setDuration(300)
                    .alpha(1.0f)
                    .start()
            } else {
                view.translationZ = i * -10f
                view.translationX = i * 10f
                view.translationY = i * 10f
            }
        }
    }

    override fun canScrollVertically(): Boolean = false

    override fun onItemsRemoved(recyclerView: RecyclerView, positionStart: Int, itemCount: Int) {
        super.onItemsRemoved(recyclerView, positionStart, itemCount)
        animateRemovals = true
        requestLayout()
        recyclerView.postDelayed({ animateRemovals = false }, 350)
    }

    fun initializeLayout() {
        animateRemovals = false
    }

}