package common.anim

import android.animation.ValueAnimator
import android.graphics.drawable.ColorDrawable
import android.view.View

/**
 * Created by alex
 */

fun ColorDrawable.animateAlpha(from: Int, to: Int): ValueAnimator =
    animateInt(from, to) {
      alpha = it
    }

fun View.animateBackgroundColor(from: Int, to: Int): ValueAnimator =
    (background as ColorDrawable).animateColor(from, to)

fun ColorDrawable.animateColor(from: Int, to: Int): ValueAnimator =
    animateColor(from, to) { color = it }

fun View.animateTranslationX(from: Float, to: Float): ValueAnimator =
    animateFloat(from, to) {
      translationX = it
    }