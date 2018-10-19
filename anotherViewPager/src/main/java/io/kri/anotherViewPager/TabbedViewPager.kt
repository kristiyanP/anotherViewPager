package io.kri.anotherViewPager

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DimenRes
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import io.kri.anotherViewPager.UIAndroidUtils.getDimenFromResource


/**
 * @author Kristiyan Petrov
 */
class TabbedViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private var viewPager: ViewPager? = null
    private var tabLayout: TabLayout? = null
    private var currentPage: Int = 0
    private var oldPage = 0

    private var selectedTabFont = 0
    private var defaultTabFont = 0

    private var tabMarginStart = 0
    private var tabMarginEnd = 0
    private var tabMarginBottom = 0
    private var tabMarginTop = 0
    private var tabSelectedTextColor: Int = 0
    private var tabDefaultTextColor = 0

    init {
        init(context, attrs, defStyleAttr)
    }

    public override fun onSaveInstanceState(): Parcelable? {
        currentPage = viewPager!!.currentItem
        val bundle = Bundle()
        bundle.putParcelable("superState", super.onSaveInstanceState())
        bundle.putInt("currentPage", currentPage)
        return bundle
    }

    public override fun onRestoreInstanceState(state: Parcelable?) {
        var parcelable = state
        if (parcelable is Bundle) {
            val bundle = state as Bundle?
            this.currentPage = bundle!!.getInt("currentPage")
            this.oldPage = currentPage
            parcelable = bundle.getParcelable("superState")
        }
        super.onRestoreInstanceState(parcelable)
    }

    fun handleTypedArray(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        if (attrs == null)
            return
        val array = context.obtainStyledAttributes(attrs, R.styleable.TabbedViewPager, defStyleAttr, R.style.TabbedViewPagerStyle)

        tabMarginStart = array.getDimension(R.styleable.TabbedViewPager_tabItemsMarginStart, getContext().getDimenFromResource(R.dimen.space_16)).toInt()
        tabMarginEnd = array.getDimension(R.styleable.TabbedViewPager_tabItemsMarginEnd, 0f).toInt()
        tabMarginBottom = array.getDimension(R.styleable.TabbedViewPager_tabItemsMarginBottom, 0f).toInt()
        tabMarginTop = array.getDimension(R.styleable.TabbedViewPager_tabItemsMarginTop, 0f).toInt()
        tabSelectedTextColor = array.getColor(R.styleable.TabbedViewPager_tabSelectedTextColor, Color.BLACK)
        selectedTabFont = array.getResourceId(R.styleable.TabbedViewPager_tabSelectedFont, 0)
        defaultTabFont = array.getResourceId(R.styleable.TabbedViewPager_tabDefaultFont, 0)
        array.recycle()
    }

    fun setSelectedTabFont(@FontRes font: Int): TabbedViewPager {
        selectedTabFont = font
        return this
    }

    fun setDefaultTabFont(defaultTabFont: Int): TabbedViewPager {
        this.defaultTabFont = defaultTabFont
        return this
    }

    private fun setTabFont(textViewId: Int, @FontRes font: Int, position: Int) {
        if (font == 0)
            return

        val typeface = ResourcesCompat.getFont(context, font)
        setTabFont(textViewId, typeface, position)
    }

    private fun setTabFont(textViewId: Int, font: Typeface?, position: Int) {
        val tab = tabLayout!!.getTabAt(position)

        if (tab != null) {
            val tabCustomView = tab.customView
            val title: TextView
            if (tabCustomView != null)
                title = tabCustomView.findViewById(textViewId)
            else
                title = tabLayout!!.getChildAt(position).findViewById(android.R.id.title)

            title.typeface = font
        }
    }

    private fun setTabTextColor(textViewId: Int, @ColorInt color: Int, position: Int) {
        if (color == 0)
            return

        val tab = tabLayout!!.getTabAt(position)

        if (tab != null) {
            val tabCustomView = tab.customView
            val title: TextView
            if (tabCustomView != null)
                title = tabCustomView.findViewById(textViewId)
            else
                title = tabLayout!!.getChildAt(position).findViewById(android.R.id.title)
            if (tabDefaultTextColor == 0) {
                tabDefaultTextColor = title.currentTextColor
            }
            title.setTextColor(color)
        }
    }

    fun setAdapter(adapter: TabbedViewPagerAdapter) {
        viewPager!!.adapter = adapter
        adapter.setTabLayout(tabLayout!!)
        viewPager!!.currentItem = currentPage
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        if (!isInEditMode) {
            LinearLayout.inflate(getContext(), R.layout.tabbed_view_pager, this)
        }
        viewPager = findViewById(R.id.tabbed_pager)
        tabLayout = TabLayout(getContext(), attrs, R.style.TabbedViewPagerStyle)
        addView(tabLayout, 0)
        tabLayout!!.setupWithViewPager(viewPager)
        viewPager!!.post {
            for (i in 0 until tabLayout!!.tabCount) {
                val tab = (tabLayout!!.getChildAt(0) as ViewGroup).getChildAt(i)
                val p = tab.layoutParams as ViewGroup.MarginLayoutParams
                p.setMargins(tabMarginStart, tabMarginTop, tabMarginEnd, tabMarginBottom)
                setTabFont(R.id.textView, defaultTabFont, i)
                tab.requestLayout()
            }
            setTabTextColor(R.id.textView, tabSelectedTextColor, oldPage)
            setTabFont(R.id.textView, selectedTabFont, oldPage)
        }

        viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                setTabFont(R.id.textView, defaultTabFont, oldPage)
                setTabFont(R.id.textView, selectedTabFont, position)
                setTabTextColor(R.id.textView, tabDefaultTextColor, oldPage)
                setTabTextColor(R.id.textView, tabSelectedTextColor, position)
                oldPage = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        orientation = LinearLayout.VERTICAL
        handleTypedArray(context, attrs, defStyleAttr)
    }


    fun addOnPageChangeListener(onPageChangeListener: ViewPager.OnPageChangeListener) {
        if (viewPager == null)
            return
        viewPager!!.addOnPageChangeListener(onPageChangeListener)
    }

    fun setTabsMargin(@DimenRes start: Int, @DimenRes top: Int, @DimenRes end: Int, @DimenRes bottom: Int): TabbedViewPager {
        tabMarginStart = context.getDimenFromResource(start).toInt()
        tabMarginTop = context.getDimenFromResource(top).toInt()
        tabMarginEnd = context.getDimenFromResource(end).toInt()
        tabMarginBottom = context.getDimenFromResource(bottom).toInt()
        return this
    }

}
