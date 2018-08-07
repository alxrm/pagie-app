package common

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.AnyRes
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.View


/**
 * Created by alex
 */

private val TV = TypedValue()

@ColorInt
fun Context.color(@ColorRes color: Int): Int = ContextCompat.getColor(this, color)

fun Context.drawable(@DrawableRes res: Int): Drawable = ContextCompat.getDrawable(this, res)!!

@AnyRes
fun Context.attr(@AttrRes attr: Int): Int = theme.resolveAttribute(attr, TV, true).let {
  assertMainThread()
  TV.resourceId
}

@ColorInt
fun Context.attrColor(@AttrRes attr: Int): Int = theme.resolveAttribute(attr, TV, true).let {
  assertMainThread()
  TV.data
}

@AnyRes
fun View.attr(@AttrRes attr: Int): Int = context.attr(attr)

@ColorInt
fun View.color(@ColorRes color: Int): Int = context.color(color)
