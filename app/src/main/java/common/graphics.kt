package common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.RectF
import android.view.View

/**
 * Created by alex
 */

internal fun View.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()

internal fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()

internal fun View.dipF(value: Float): Float = value * resources.displayMetrics.density

internal fun Context.dipF(value: Float): Float = value * resources.displayMetrics.density

internal inline fun smoothPaint(color: Int = Color.WHITE,
    crossinline block: Paint.() -> Unit = {}): Paint =
    Paint().apply {
      isAntiAlias = true
      this.color = color
      block()
    }

internal inline fun filterPaint(color: Int = Color.BLACK,
    crossinline block: Paint.() -> Unit = {}): Paint =
    Paint().apply {
      isAntiAlias = true
      colorFilter = filterOf(color)

    }

internal fun filterOf(color: Int = Color.BLACK) =
    PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)

internal inline fun Canvas.transform(crossinline init: Canvas.() -> Unit) {
  save()
  init()
  restore()
}

internal fun rectFOf(left: Int, top: Int, right: Int, bottom: Int) = RectF(
    left.toFloat()
    , top.toFloat()
    , right.toFloat()
    , bottom.toFloat()
)

internal fun Int.withAlpha(alpha: Int): Int {
  require(alpha in 0x00..0xFF)
  return this and 0x00FFFFFF or (alpha shl 24)
}

internal fun Float.clamp(min: Float, max: Float) = Math.min(max, Math.max(this, min))