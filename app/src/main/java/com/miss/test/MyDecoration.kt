package com.miss.test

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.min

/**
 *   Created by Vola on 2020/7/23.
 */
class MyDecoration() : RecyclerView.ItemDecoration() {

    private lateinit var context: Context
    private lateinit var headPaint: Paint
    private lateinit var textPaint: Paint
    private lateinit var textRect: Rect

    var groupHeaderHeight: Int = 0

    constructor(context: Context):this(){
        this.context = context
        groupHeaderHeight = dp2px(context,100)

        headPaint = Paint()
        headPaint.color = Color.RED

        textPaint = Paint()
        textPaint.color = Color.WHITE
        textPaint.textSize = 35F

        textRect = Rect()
    }


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (parent.adapter is StarAdapter) {
            var adapter: StarAdapter = parent.adapter as StarAdapter
            var count: Int = parent.childCount

            var left: Float = parent.paddingLeft.toFloat()
            var right: Float = (parent.width-parent.paddingRight).toFloat()

            for (i in 0 until count) {
                var view: View = parent.getChildAt(i)
                var position: Int = parent.getChildLayoutPosition(view)
                var isHeader: Boolean = adapter.isGroupHead(position)
                if (isHeader) {
                    c.drawRect(left, (view.top-groupHeaderHeight).toFloat(),right,
                        view.bottom.toFloat(),headPaint)

                    var groupName: String = adapter.getGroupName(position)

                    Log.e("aa","-----11-----textRect=${textRect.height()}")
                    textPaint.getTextBounds(groupName, 0, groupName.length, textRect)
                    Log.e("aa","-----22-----textRect=${textRect.height()}")
                    c.drawText(groupName, left + 20,
                        (view.top - groupHeaderHeight / 2 + textRect.height() / 2).toFloat(),
                        textPaint)
                }else{
                    //  分割线
                    c.drawRect(left, (view.top-4).toFloat(),
                        right, view.top.toFloat(),headPaint)
                }
            }
        }
    }


    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (parent.adapter is StarAdapter) {
            val adapter: StarAdapter = parent.adapter as StarAdapter
            val position: Int =
                (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            val itemView = parent.findViewHolderForAdapterPosition(position)!!.itemView

            val left: Float = parent.paddingLeft.toFloat()
            val right: Float = parent.width - parent.paddingRight.toFloat()
            val top: Float = parent.paddingTop.toFloat()

            val isHeader: Boolean = adapter.isGroupHead(position + 1)
            val groupName: String = adapter.getGroupName(position)

            if (isHeader) {
                val bottom: Int = min(groupHeaderHeight, itemView.bottom - parent.paddingTop)
                c.drawRect(left, top, right, top + bottom, headPaint)
                textPaint.getTextBounds(groupName, 0, groupName.length, textRect)
                c.drawText(
                    groupName,
                    left + 20,
                    top + bottom - groupHeaderHeight / 2 + textRect.height() / 2,
                    textPaint
                )
            } else {
                c.drawRect(left, top, right, top + groupHeaderHeight, headPaint)
                val groupName = adapter.getGroupName(position)
                textPaint.getTextBounds(groupName, 0, groupName.length, textRect)
                c.drawText(
                    groupName,
                    left + 20,
                    top + groupHeaderHeight / 2 + textRect.height() / 2,
                    textPaint
                )
            }

        }
    }


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.adapter is StarAdapter) {
            val adapter: StarAdapter = parent.adapter as StarAdapter
            val position: Int = parent.getChildLayoutPosition(view)
            if (adapter.isGroupHead(position)) {
                outRect.set(0, groupHeaderHeight, 0, 0)
            } else {
                outRect.set(0, 4, 0, 0)
            }
        }

    }

    private fun dp2px(context: Context, dpValue: Int): Int {
        var scale = context.resources.displayMetrics.density
        return (dpValue*scale*0.5).toInt()
    }

}