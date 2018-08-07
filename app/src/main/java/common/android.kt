package common

import android.os.Build
import android.os.Looper
import android.os.StrictMode
import rm.com.pagie.BuildConfig

fun assertWorkerThread(): Unit = when {
  BuildConfig.DEBUG && isMainThread() -> error("wrong thread, buddy")
  else -> Unit
}

fun isMainThread(): Boolean =
    Looper.myLooper() == Looper.getMainLooper()

fun assertMainThread(): Unit = when {
  BuildConfig.DEBUG && !isMainThread() -> error("wrong thread, buddy")
  else -> Unit
}

fun threadPolicy(): StrictMode.ThreadPolicy =
    StrictMode.ThreadPolicy.Builder()
        .detectAll()
        .penaltyLog()
        .build()

fun vmPolicy(): StrictMode.VmPolicy =
    StrictMode.VmPolicy.Builder()
        .detectAll()
        .penaltyLog()
        .build()

val isDebugBuild: Boolean
  get() = BuildConfig.DEBUG

inline fun <T> whenSdk(level: Int, f: () -> T): T? = when {
  Build.VERSION.SDK_INT >= level -> f()
  else -> null
}