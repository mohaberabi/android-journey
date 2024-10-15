package com.mohaberabi.kdraw

import android.graphics.Point
import android.graphics.PointF
import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable
import kotlin.math.max
import kotlin.math.min

@Serializable
data class DrawingBox(
    val start: DrawPoint,
    var end: DrawPoint = start
) {
    val left: Float
        get() = min(start.x, end.x)
    val right: Float
        get() = max(start.x, end.x)
    val up: Float
        get() = min(start.y, end.y)
    val down: Float
        get() = max(start.y, end.y)


}

@Serializable
data class DrawPoint(
    var x: Float,
    var y: Float
)

fun DrawPoint.toPointF(): PointF = PointF(x, y)
