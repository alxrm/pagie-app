package rm.com.pagie.ui

import android.support.v4.app.Fragment
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import rm.com.pagie.Constants
import rm.com.pagie.Glide4Engine

/**
 * Created by alex
 */

// potentially could be more than just 1 type of pages,
// so it's easier to split common logic to mixin
interface Page {

  // could be extended, so it's possible to add a target we attaching the media to
  fun pickMedia() {
    Matisse.from(this as Fragment) //
        .choose(MimeType.ofAll()) //
        .spanCount(3) //
        .maxSelectable(1) //
        .imageEngine(Glide4Engine()) //
        .forResult(Constants.REQUEST_PICK_MEDIA)
  }

  fun savePageData()
}