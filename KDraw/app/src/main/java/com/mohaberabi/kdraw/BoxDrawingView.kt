package com.mohaberabi.kdraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.PointF
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.os.bundleOf
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class BoxDrawingView(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {

    private var currentBox: DrawingBox? = null
    private val boxes = mutableListOf<DrawingBox>()

    private val boxPaint = Paint().apply {
        color = Color.BLACK
    }
    private val backgroundPaint = Paint().apply {
        color = Color.GRAY
    }

    companion object {
        private const val BOXES_KEY = "boxes_key"
    }


    override fun onSaveInstanceState(): Parcelable {
        val savedBox = Json.encodeToString(boxes)
        Log.d("box", savedBox.toString())
        return bundleOf(
            BOXES_KEY to savedBox,
            "superState" to super.onSaveInstanceState()
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            val json = state.getString(BOXES_KEY)
            json?.let {
                val savedPoints = Json.decodeFromString<List<DrawingBox>>(it)
                Log.d("box", savedPoints.toString())
                boxes.clear()
                boxes.addAll(savedPoints)
            }
            super.onRestoreInstanceState(state.getParcelable("superState"))

        } else {
            super.onRestoreInstanceState(state)
        }
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        val current = DrawPoint(event.x, event.y)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                currentBox = DrawingBox(current).also {
                    boxes.add(it)
                }
            }

            MotionEvent.ACTION_UP -> {
                updateBox(current)
                currentBox = null
            }

            MotionEvent.ACTION_MOVE -> updateBox(current)

            MotionEvent.ACTION_CANCEL -> currentBox = null
        }

        return true
    }

    private fun updateBox(point: DrawPoint) {
        currentBox?.let {
            it.end = point
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPaint(backgroundPaint)
        boxes.forEach { box ->
            canvas.drawRect(box.left, box.up, box.right, box.down, boxPaint)
        }
    }
}