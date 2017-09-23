package io.kri.anotherViewPager.app

import android.content.Context
import android.view.ViewGroup

import io.kri.anotherViewPager.TabbedViewPagerItem

/**
 * @author Kristiyan Petrov
 */

class Page2 : TabbedViewPagerItem() {

    override fun getTitle(): String {
        return "Page2"
    }

    override fun getLayoutResId(): Int {
        return R.layout.page_2
    }

    override fun bind(context: Context, parent: ViewGroup) {

    }

}