package io.kri.anotherViewPager

import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.PagerAdapter
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList

/**
 * @author Kristiyan Petrov
 */
class TabbedViewPagerAdapter : PagerAdapter() {

    private var tabbedViewPagerItems: MutableList<TabbedViewPagerItem>? = null
    private var tabLayout: TabLayout? = null

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val item = tabbedViewPagerItems!![position]
        item.bindItem(collection.context, collection)
        collection.addView(item.item)
        return item.item!!
    }

    fun getTabbedViewPagerItems(): List<TabbedViewPagerItem> {
        if (tabbedViewPagerItems == null || tabbedViewPagerItems!!.isEmpty()) {
            throw RuntimeException("Adapter is empty")
        }
        return tabbedViewPagerItems!!
    }

    private fun populateTabs() {
        for (position in tabbedViewPagerItems!!.indices) {
            val item = tabbedViewPagerItems!![position]
            updateTab(position, item)
        }
    }

    private fun updateTab(position: Int, item: TabbedViewPagerItem) {
        if (tabLayout!!.getTabAt(position) == null)
            return

        val view = item.getTabWithCustomView(tabLayout!!.context)
        if (view != null)
            tabLayout!!.getTabAt(position)!!.customView = view
        else
            tabLayout!!.getTabAt(position)!!.text = item.title
    }

    fun getItemAtPosition(position: Int): TabbedViewPagerItem {
        if (tabbedViewPagerItems == null || tabbedViewPagerItems!!.isEmpty()) {
            throw RuntimeException("Adapter is empty")
        } else if (position >= tabbedViewPagerItems!!.size) {
            throw RuntimeException("No item at position$position")
        }
        return tabbedViewPagerItems!![position]
    }

    fun addItem(item: TabbedViewPagerItem): TabbedViewPagerAdapter {
        if (tabbedViewPagerItems == null) tabbedViewPagerItems = ArrayList()
        tabbedViewPagerItems!!.add(item)
        notifyDataSetChanged()
        return this
    }


    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return tabbedViewPagerItems?.size ?: 0
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (tabbedViewPagerItems == null || tabbedViewPagerItems!!.isEmpty()) {
            throw RuntimeException("Adapter is empty")
        } else if (position >= tabbedViewPagerItems!!.size) {
            throw RuntimeException("No item at position$position")
        }
        return tabbedViewPagerItems!![position].title
    }

    fun setTabLayout(tabLayout: TabLayout) {
        this.tabLayout = tabLayout
        populateTabs()
    }
}
