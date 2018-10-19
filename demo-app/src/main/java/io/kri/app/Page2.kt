package io.kri.app

import android.content.Context
import android.view.ViewGroup

import io.kri.anotherViewPager.TabbedViewPagerItem
import io.kri.anotherViewPager.app.R

/**
 * @author Kristiyan Petrov
 */

class Page2 : TabbedViewPagerItem() {

    override val title = "Page2"
    override val layoutResId = R.layout.page_2

    override fun bind(context: Context, parent: ViewGroup) {

    }

}