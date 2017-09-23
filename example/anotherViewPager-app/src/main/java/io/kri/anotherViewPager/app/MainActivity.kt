package io.kri.anotherViewPager.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.kri.anotherViewPager.TabbedViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = TabbedViewPagerAdapter()
                .addItem(Page1())
                .addItem(Page2())

        viewPager.setAdapter(adapter)
    }
}
