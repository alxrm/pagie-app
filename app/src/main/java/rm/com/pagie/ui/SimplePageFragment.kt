package rm.com.pagie.ui

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import bindView
import com.yqritc.scalablevideoview.ScalableVideoView
import com.zhihu.matisse.Matisse
import common.isWriteExternalStorageGranted
import io.paperdb.Paper
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rm.com.pagie.Constants.ARG_PAGE_KEY
import rm.com.pagie.Constants.PERMISSION_STORAGE
import rm.com.pagie.R
import rm.com.pagie.R.layout
import rm.com.pagie.extensions.loadFromPath
import rm.com.pagie.extensions.mimeTypeOfPath
import rm.com.pagie.extensions.playFromPath
import rm.com.pagie.model.MediaType
import rm.com.pagie.model.MediaType.IMAGE
import rm.com.pagie.model.MediaType.VIDEO
import rm.com.pagie.model.PageData
import rm.com.pagie.model.PageFocusEvent
import rm.com.pagie.model.asMediaType


/**
 * Created by alex
 */

class SimplePageFragment : Fragment(), Page {

  private val image by bindView<ImageView>(R.id.page_simple_image)
  private val video by bindView<ScalableVideoView>(R.id.page_simple_video)
  private val empty by bindView<ImageView>(R.id.page_empty_add)
  private val contents by lazy { arrayOf(image, video, empty) }

  private val storage by lazy { Paper.book() }
  private val bus by lazy { EventBus.getDefault() }
  private val pageKey by lazy { arguments!!.getString(ARG_PAGE_KEY) }
  private val pageData by lazy { storage.read<PageData>(pageKey, PageData()) }

  companion object {
    fun make(pageKey: String): SimplePageFragment = SimplePageFragment().apply {
      arguments = Bundle().apply { putString(ARG_PAGE_KEY, pageKey) }
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater.inflate(layout.fragment_page_simple, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    updatePageContent()

    contents.forEach {
      it.setOnClickListener { _ ->
        pickMedia()
      }
    }
  }

  override fun onStart() {
    super.onStart()
    bus.register(this)
  }

  override fun onStop() {
    bus.unregister(this)
    super.onStop()
  }

  override fun onDestroyView() {
    contents.forEach { it.setOnClickListener(null) }
    super.onDestroyView()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (resultCode != RESULT_OK) {
      return
    }

    pageData.mediaPath = Matisse.obtainPathResult(data)!!.first()
    updatePageContent()
    savePageData()
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
      grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    if (requestCode == PERMISSION_STORAGE && isWriteExternalStorageGranted()) {
      pickMedia()
    }
  }

  override fun savePageData() {
    storage.write(pageKey, pageData)
  }

  override fun pickMedia() {
    if (!isWriteExternalStorageGranted()) {
      requestPermissions(arrayOf(WRITE_EXTERNAL_STORAGE), PERMISSION_STORAGE)
      return
    }

    super.pickMedia()
  }

  @Subscribe
  fun onPageFocusChanged(event: PageFocusEvent) {
    if (video.visibility == GONE) {
      return
    }

    if (event.selectedPageKey == pageKey) {
      video.start()
    } else if (video.isPlaying) {
      video.pause()
    }
  }

  private fun updatePageContent() {
    val path = pageData.mediaPath

    if (path.isEmpty()) {
      return
    }

    empty.visibility = GONE
    showMedia(path)
  }

  private fun showMedia(path: String) {
    val mime = path.mimeTypeOfPath()
    val type = mime.asMediaType()
    selectElementForType(type)

    when (type) {
      IMAGE -> image.loadFromPath(path)
      VIDEO -> video.playFromPath(path)
    }
  }

  private fun selectElementForType(type: MediaType) {
    video.visibility = if (type == VIDEO) VISIBLE else GONE
    image.visibility = if (type == IMAGE) VISIBLE else GONE
  }
}

