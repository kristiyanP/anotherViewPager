# AnotherViewPager [![Build Status](https://travis-ci.org/kristiyanP/anotherViewPager.svg?branch=master)](https://travis-ci.org/kristiyanP/anotherViewPager) [![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16) [![Download](https://api.bintray.com/packages/petrovkristiyan/maven/anotherViewPager/images/download.svg)](https://bintray.com/petrovkristiyan/maven/anotherViewPager/_latestVersion)
![preview](https://raw.github.com/kristiyanP/anotherViewPager/master/preview.gif)

# Include in your project
```gradle
compile 'io.kri:anotherViewPager:1.0.1'
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

    <TextView
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
    android:orientation="vertical"
    tools:context="io.kri.anotherViewPager.app.MainActivity">

    <io.kri.anotherViewPager.TabbedViewPager
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:tabSelectedFont="@font/quicksand_bold" />

</LinearLayout>
```

## In Java or in Kotlin (see below)

### Create viewPager item
```java
public class Page1 extends TabbedViewPagerItem{
    @Override
    public String getTitle() {
        return "Title Page1";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.layout_page1;
    }

    @Override
    protected void bind(Context context, ViewGroup parent) {
        // customize the look programatically
        TextView title = parent.findViewById(R.id.title);
        title.setTextColor(Color.BLUE);
    }
}
```

### Set MainActivity.java
```java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewPager);
        TabbedViewPagerAdapter adapter = new TabbedViewPagerAdapter()
                .addItem(new Page1());

        viewPager.setAdapter(adapter);
    }
}
```

## In Kotlin

### Create viewPager item
```kotlin
class Page1 : TabbedViewPagerItem() {

    override fun getTitle(): String {
        return "Title Page1"
    }

    override fun getLayoutResId(): Int {
        return R.layout.page_1
    }

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

## Further Cutomization
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

