# AnotherViewPager [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AnotherViewPager-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6281) [![Build Status](https://travis-ci.org/kristiyanP/anotherViewPager.svg?branch=master)](https://travis-ci.org/kristiyanP/anotherViewPager)  [![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16) [![Download](https://api.bintray.com/packages/petrovkristiyan/maven/anotherViewPager/images/download.svg)](https://bintray.com/petrovkristiyan/maven/anotherViewPager/_latestVersion)
![preview](https://raw.github.com/kristiyanP/anotherViewPager/master/preview.gif)

# Include in your project
```gradle
implementation 'io.kri:anotherViewPager:1.0.2'
```

# How to use
create layout_page1.xml
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <androidx.appcompat.widget.AppCompatTextView
        android:id="+@id/title"
        android:text="page1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>

</LinearLayout>
```

create activity_main.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <io.kri.anotherViewPager.TabbedViewPager
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:tabSelectedFont="@font/quicksand_bold" />

</LinearLayout>
```

### Create viewPager item
```kotlin
class Page1 : TabbedViewPagerItem() {

    override val title = "Page1"
    override val layoutResId = R.layout.page_1

    override fun bind(context: Context, parent: ViewGroup) {
         // customize the look programatically
         val title = findViewById(R.id.title)
         title.setBackgroundColor(Color.BLUE)
    }

}
```

### Set MainAcitivty
```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = TabbedViewPagerAdapter()
                .addItem(Page1())

        viewPager.setAdapter(adapter)
    }

}
```

# Style customization

## Further Customization
possible attributes to set to the tabbedViewPager
```xml
<attr name="tabItemsMarginStart" format="dimension" />
<attr name="tabItemsMarginEnd" format="dimension" />
<attr name="tabItemsMarginBottom" format="dimension" />
<attr name="tabItemsMarginTop" format="dimension" />
<attr name="tabSelectedFont" format="reference"/>
<attr name="tabDefaultFont" format="reference"/>
<attr name="tabSelectedTextColor" />
```

## More customizations
If you want to have another style just implement the following styles, they will overwrite the default ones
```xml
<style name="TabbedViewPagerStyle">
<!-- style for the whole viewPager !-->
</style>

<style name="TabbedViewPagerTabTextStyle">
<!-- style for the title !-->
</style>
```

