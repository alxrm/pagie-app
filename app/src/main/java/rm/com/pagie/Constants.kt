package rm.com.pagie

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

/**
 * Created by alex
 */

object Constants {
  const val REQUEST_PICK_MEDIA = 101
  const val PERMISSION_STORAGE = 102
  const val ARG_PAGE_KEY: String = "ARG_PAGE_KEY"
  const val KEY_STORAGE_PAGES: String = "KEY_STORAGE_PAGES"
}

val BACKGROUND_THREAD = Executors.newSingleThreadExecutor()!!
val MAIN_THREAD = Handler(Looper.getMainLooper())

fun background(action: () -> Unit) {
  BACKGROUND_THREAD.execute(action)
}

fun ui(action: () -> Unit) {
  MAIN_THREAD.post(action)
}