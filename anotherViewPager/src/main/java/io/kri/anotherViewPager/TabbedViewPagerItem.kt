package io.kri.anotherViewPager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


/**
 * @author Kristiyan Petrov
 */
abstract class TabbedViewPagerItem {

    protected var layout: ViewGroup? = null

    abstract val title: String

    abstract val layoutResId: Int

    val item: View?
        get() = layout

    internal fun getTabWithCustomView(context: Context): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.tab, null)
        val textView = view.findViewById<TextView>(R.id.textView)
        textView?.text = title
        return view
    }

    protected abstract fun bind(context: Context, parent: ViewGroup)

    fun bindItem(context: Context, parent: ViewGroup) {
        layout = LayoutInflater.from(context).inflate(layoutResId, parent, false) as ViewGroup
        bind(context, layout!!)
    }

    fun findViewById(id: Int): View? {
        return layout?.findViewById(id)
    }
}
