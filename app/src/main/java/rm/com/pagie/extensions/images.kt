package rm.com.pagie.extensions

import android.widget.ImageView
import rm.com.pagie.GlideApp

/**
 * Created by alex
 */

// this is not scalable at all, in a real world I wouldn't be doing stuff like that
fun ImageView.loadFromPath(path: String) {
  GlideApp.with(this).load(path).centerCrop().into(this)
}