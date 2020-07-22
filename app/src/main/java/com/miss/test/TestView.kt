package com.miss.test

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 *   Created by Vola on 2020/7/14.
 */
class TestView : View {

    constructor(context: Context):super(context){}

    constructor(context: Context,attrs: AttributeSet)
            : super(context, attrs){}

    constructor(context: Context,attrs:AttributeSet,defStyleAttr:Int)
            : super(context, attrs,defStyleAttr){}


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var paint:Paint = Paint()
        paint.textSize = 80F
        var baseLine = 100


    }

    fun drawCenterLineX(canvas :Canvas){
        var paint:Paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color  = Color.RED
        paint.strokeWidth = 3F
//        canvas.drawLine(width/2,)
    }

}