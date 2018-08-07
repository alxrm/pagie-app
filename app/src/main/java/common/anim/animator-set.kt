package common.anim

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.ViewPropertyAnimator
import android.view.animation.Interpolator

/**
 * Created by alex
 */

fun <T : Animator> T.chainListener(listener: Animator.AnimatorListener): T =
    apply { addListener(listener) }

fun <T : Animator> T.chainInterpolator(interpolator: Interpolator): T =
    apply { this.interpolator = interpolator }

fun <T : Animator> T.chainDuration(d: Long): T =
    apply { duration = d }

fun <T : ValueAnimator> T.chainUpdateListener(ul: ValueAnimator.AnimatorUpdateListener): T =
    apply { addUpdateListener(ul) }

inline fun <reified T> ValueAnimator.onUpdate(crossinline f: (T) -> Unit): ValueAnimator =
    chainUpdateListener(animationUpdate<T>(f))

inline fun ValueAnimator.onUpdateFraction(
    crossinline f: (Float) -> Unit): ValueAnimator = apply {
  addUpdateListener {
    f(it.animatedFraction)
  }
}

fun <T : Animator> T.chainStart(): T =
    apply { start() }

fun ViewPropertyAnimator.chainStart(): ViewPropertyAnimator =
    apply { start() }

fun <T : Animator> T.chainDelay(d: Long): T =
    apply { startDelay = d }