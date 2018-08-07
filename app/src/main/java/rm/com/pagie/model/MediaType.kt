package rm.com.pagie.model

import rm.com.pagie.model.MediaType.IMAGE
import rm.com.pagie.model.MediaType.VIDEO

/**
 * Created by alex
 */

// enums matter
enum class MediaType {
  IMAGE, VIDEO
}

fun String.asMediaType(): MediaType =
    when (substring(0, indexOf("/"))) {
      "image" -> IMAGE
      "video" -> VIDEO
      else -> error("We're not dealing with media mime type here")
    }
