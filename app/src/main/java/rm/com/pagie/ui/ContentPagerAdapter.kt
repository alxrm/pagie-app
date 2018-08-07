package rm.com.pagie.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter

/**
 * Created by alex
 */

class ContentPagerAdapter(
    fm: FragmentManager,
    private val pageKeys: MutableList<String> = mutableListOf()
) : FragmentStatePagerAdapter(fm) {

  override fun getItem(position: Int): Fragment? = SimplePageFragment.make(pageKeys[position])

  override fun getCount(): Int = pageKeys.size

  override fun getItemPosition(obj: Any): Int {
    val position = pageKeys.indexOf(obj)
    return if (position == -1) PagerAdapter.POSITION_NONE else position
  }
}