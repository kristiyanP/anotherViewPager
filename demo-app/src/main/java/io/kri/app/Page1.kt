package io.kri.app

import android.content.Context
import android.view.ViewGroup

import io.kri.anotherViewPager.TabbedViewPagerItem
import io.kri.anotherViewPager.app.R

/**
 * @author Kristiyan Petrov
 */

class Page1 : TabbedViewPagerItem() {

    override val title = "Page1"
    override val layoutResId = R.layout.page_1

    override fun bind(context: Context, parent: ViewGroup) {}

}
