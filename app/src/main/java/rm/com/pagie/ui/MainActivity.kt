package rm.com.pagie.ui

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import bindView
import common.dip
import io.paperdb.Paper
import org.greenrobot.eventbus.EventBus
import rm.com.pagie.Constants.KEY_STORAGE_PAGES
import rm.com.pagie.R
import rm.com.pagie.model.PageFocusEvent
import java.util.UUID

class MainActivity : AppCompatActivity(), OnPageChangeListener {
  private val pager by bindView<ViewPager>(R.id.root_pager)
  private val add by bindView<FloatingActionButton>(R.id.root_add)
  private val remove by bindView<ImageView>(R.id.root_remove)
  private val empty by bindView<TextView>(R.id.root_empty_text)

  // yep, should be dimen resources, i know, let it slip just for now
  private val pageMargin by lazy { dip(32) }

  private val storage by lazy { Paper.book() }
  private val bus by lazy { EventBus.getDefault() }
  private val pageKeys = storage.read<MutableList<String>>(KEY_STORAGE_PAGES, mutableListOf())
  private val pagerAdapter by lazy { ContentPagerAdapter(supportFragmentManager, pageKeys) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupPager()

    add.setOnClickListener { addPage() }
    remove.setOnClickListener { removePage() }

    updateViewsForContents()
  }

  override fun onDestroy() {
    add.setOnClickListener(null)
    remove.setOnClickListener(null)
    pager.removeOnPageChangeListener(this)
    super.onDestroy()
  }

  override fun onPageScrollStateChanged(state: Int) = Unit

  override fun onPageScrolled(position: Int, positionOffset: Float,
      positionOffsetPixels: Int) = Unit

  override fun onPageSelected(position: Int) {
    bus.post(PageFocusEvent(pageKeys[position]))
  }

  private fun setupPager() {
    pager.setPadding(pageMargin, 0, pageMargin, 0)
    pager.clipChildren = false
    pager.clipToPadding = false
    pager.adapter = pagerAdapter
    pager.offscreenPageLimit = 1
    pager.addOnPageChangeListener(this)
  }

  private fun addPage() {
    val nextPageKey = UUID.randomUUID().toString()

    pageKeys += nextPageKey
    pagerAdapter.notifyDataSetChanged()
    storage.write(KEY_STORAGE_PAGES, pageKeys)
    pager.setCurrentItem(pagerAdapter.count, true)

    updateViewsForContents()
  }

  private fun removePage() {
    val removedKey = pageKeys.removeAt(pager.currentItem)

    pagerAdapter.notifyDataSetChanged()
    storage.write(KEY_STORAGE_PAGES, pageKeys)
    storage.delete(removedKey)

    updateViewsForContents()
  }

  private fun updateViewsForContents() {
    empty.visibility = if (pagerAdapter.count == 0) VISIBLE else GONE
    remove.visibility = if (pagerAdapter.count > 0) VISIBLE else GONE
    pager.visibility = if (pagerAdapter.count > 0) VISIBLE else GONE
  }
}
