package rm.com.pagie.extensions

import android.webkit.MimeTypeMap
import timber.log.Timber

/**
 * Created by alex
 */

fun String.mimeTypeOfPath(): String {
  return MimeTypeMap.getFileExtensionFromUrl(this)
      ?.let { MimeTypeMap.getSingleton().getMimeTypeFromExtension(it) }
      ?.also { Timber.d("Mime type is: $it") }
      ?: error("It's not a path or it's not correct, sorry")
}