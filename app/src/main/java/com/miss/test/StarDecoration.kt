package com.miss.test

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.min

/**
 *   Created by Vola on 2020/7/22.
 */
class StarDecoration() : RecyclerView.ItemDecoration() {

    lateinit var context: Context
    private var groupHeaderHeight: Int =0
    lateinit var headPaint: Paint
    lateinit var textPaint: Paint
    lateinit var textRect: Rect

    constructor(context: Context):this(){
        this.context = context

        groupHeaderHeight = dp2px(context,100)

        headPaint = Paint()
        headPaint.color = Color.RED

        textPaint = Paint()
        textPaint.color =Color.WHITE
        textPaint.textSize = 50F

        textRect = Rect()

    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        //  parent.getAdapter() instanceof StarAdapter ===> parent.adapter is StarAdapter
        if (parent.adapter is StarAdapter) {
            var adapter :StarAdapter = parent.adapter as StarAdapter
            //  当前屏幕 item 个数
            var count:Int =parent.childCount
            var left: Int = parent.paddingLeft
            var right: Int = parent.width - parent.paddingRight
            for (i in 0 until count) {
                // 获取对应i的View
                var view: View = parent.getChildAt(i)
                // 获取View的布局位置
                var position: Int = parent.getChildLayoutPosition(view)
                // 是否是头部
                var isHeader:Boolean = adapter.isGroupHead(position)
                if (isHeader && view.top - groupHeaderHeight - parent.paddingTop >= 0) {
                    c.drawRect(
                        left.toFloat(), (view.top - groupHeaderHeight).toFloat(),
                        right.toFloat(),
                        view.bottom.toFloat(), headPaint
                    )
                    var groupName: String = adapter.getGroupName(position)
                    textPaint.getTextBounds(groupName, 0, groupName.length, textRect)
                    c.drawText(
                        groupName,
                        (left + 20).toFloat(),
                        (view.top - groupHeaderHeight / 2 + textRect.height() / 2).toFloat(),
                        textPaint
                    )
                } else {
                    //  分割线
                    c.drawRect(left.toFloat(), (view.top-4).toFloat(),
                        right.toFloat(), view.top.toFloat(),headPaint)
                }
            }


        }

    }


    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (parent.adapter is StarAdapter) {
            var adapter: StarAdapter = parent.adapter as StarAdapter
            // 返回可见区域第一个item
            // 返回可见区域内的第一个item的position
            var position:Int =(parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            // 获取对应position的View
            val itemView = parent.findViewHolderForAdapterPosition(position)!!.itemView

            var left: Int = parent.paddingLeft
            var right: Int = parent.width - parent.paddingRight
            var top: Int = parent.paddingTop
            var isHeader: Boolean = adapter.isGroupHead(position + 1)
            var groupName: String = adapter.getGroupName(position)
            if (isHeader) {
                var bottom: Int = min(groupHeaderHeight, itemView.bottom - parent.paddingTop)
                c.drawRect(
                    left.toFloat(), top.toFloat(), right.toFloat(),
                    (top + bottom).toFloat(), headPaint
                )
                textPaint.getTextBounds(groupName, 0, groupName.length, textRect)
                c.drawText(
                    groupName,
                    (left + 20).toFloat(),
                    (top + bottom - groupHeaderHeight / 2 + textRect.height() / 2).toFloat(),
                    textPaint
                )
            } else {
                c.drawRect(
                    left.toFloat(),
                    top.toFloat(),
                    right.toFloat(),
                    top + groupHeaderHeight.toFloat(),
                    headPaint
                )
                val groupName = adapter.getGroupName(position)
                textPaint.getTextBounds(groupName, 0, groupName.length, textRect)
                c.drawText(
                    groupName,
                    left + 20.toFloat(),
                    top + groupHeaderHeight / 2 + (textRect.height() / 2).toFloat(),
                    textPaint
                )
            }
        }
    }


    /**
     *      每个item的偏移量，在顶部留出空间
     */
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.adapter is StarAdapter) {
            var adapter : StarAdapter = parent.adapter as StarAdapter
            var position: Int = parent.getChildLayoutPosition(view)
            if (adapter.isGroupHead(position)) {
                //  如果是头部，留更多空间
                outRect.set(0, groupHeaderHeight, 0, 0)
            } else {
                //  如果不是头部，留更小空间
                outRect.set(0, 4, 0, 0)
            }
        }
    }

    private fun dp2px(context: Context, dpValue: Int): Int {
        var scale = context.resources.displayMetrics.density
        return (dpValue*scale*0.5).toInt()
    }

}