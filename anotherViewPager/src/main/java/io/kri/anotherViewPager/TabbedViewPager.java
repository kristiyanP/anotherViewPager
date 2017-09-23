package io.kri.anotherViewPager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DimenRes;
import android.support.annotation.FontRes;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import static io.kri.anotherViewPager.UIAndroidUtils.getDimenFromResource;


/**
 * @author Kristiyan Petrov
 */
public class TabbedViewPager extends LinearLayout {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private int currentPage;
    private int oldPage = 0;

    private int selectedTabFont = 0;
    private int defaultTabFont = 0;

    private int tabMarginStart = 0;
    private int tabMarginEnd = 0;
    private int tabMarginBottom = 0;
    private int tabMarginTop = 0;
    private int tabSelectedTextColor;
    private int tabDefaultTextColor = 0;

    public TabbedViewPager(final Context context) {
        this(context, null);
    }

    public TabbedViewPager(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabbedViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        currentPage = viewPager.getCurrentItem();
        final Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putInt("currentPage", currentPage);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;
            this.currentPage = bundle.getInt("currentPage");
            this.oldPage = currentPage;
            state = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(state);
    }

    public void handleTypedArray(final Context context, final AttributeSet attrs, int defStyleAttr) {
        if (attrs == null)
            return;
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TabbedViewPager, defStyleAttr, R.style.TabbedViewPagerStyle);

        tabMarginStart = (int) array.getDimension(R.styleable.TabbedViewPager_tabItemsMarginStart, getDimenFromResource(getContext(), R.dimen.space_16));
        tabMarginEnd = (int) array.getDimension(R.styleable.TabbedViewPager_tabItemsMarginEnd, 0);
        tabMarginBottom = (int) array.getDimension(R.styleable.TabbedViewPager_tabItemsMarginBottom, 0);
        tabMarginTop = (int) array.getDimension(R.styleable.TabbedViewPager_tabItemsMarginTop, 0);
        tabSelectedTextColor = array.getColor(R.styleable.TabbedViewPager_tabSelectedTextColor, Color.BLACK);
        selectedTabFont = array.getResourceId(R.styleable.TabbedViewPager_tabSelectedFont, 0);
        defaultTabFont = array.getResourceId(R.styleable.TabbedViewPager_tabDefaultFont, 0);
        array.recycle();
    }

    public TabbedViewPager setSelectedTabFont(@FontRes int font) {
        selectedTabFont = font;
        return this;
    }

    public TabbedViewPager setDefaultTabFont(int defaultTabFont) {
        this.defaultTabFont = defaultTabFont;
        return this;
    }

    private void setTabFont(int textViewId, @FontRes int font, int position) {
        if (font == 0)
            return;

        Typeface typeface = ResourcesCompat.getFont(getContext(), font);
        setTabFont(textViewId, typeface, position);
    }

    private void setTabFont(int textViewId, Typeface font, int position) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);

        if (tab != null) {
            View tabCustomView = tab.getCustomView();
            TextView title;
            if (tabCustomView != null)
                title = tabCustomView.findViewById(textViewId);
            else
                title = tabLayout.getChildAt(position).findViewById(android.R.id.title);

            title.setTypeface(font);
        }
    }

    private void setTabTextColor(int textViewId, @ColorInt int color, int position) {
        if (color == 0)
            return;

        TabLayout.Tab tab = tabLayout.getTabAt(position);

        if (tab != null) {
            View tabCustomView = tab.getCustomView();
            TextView title;
            if (tabCustomView != null)
                title = tabCustomView.findViewById(textViewId);
            else
                title = tabLayout.getChildAt(position).findViewById(android.R.id.title);
            if (tabDefaultTextColor == 0) {
                tabDefaultTextColor = title.getCurrentTextColor();
            }
            title.setTextColor(color);
        }
    }

    public void setAdapter(final TabbedViewPagerAdapter adapter) {
        viewPager.setAdapter(adapter);
        adapter.setTabLayout(tabLayout);
        viewPager.setCurrentItem(currentPage);
    }

    private void init(final Context context, final AttributeSet attrs, int defStyleAttr) {
        if (!isInEditMode()) {
            LinearLayout.inflate(getContext(), R.layout.tabbed_view_pager, this);
        }
        viewPager = findViewById(R.id.tabbed_pager);
        tabLayout = new TabLayout(getContext(), attrs, R.style.TabbedViewPagerStyle);
        addView(tabLayout, 0);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
                    ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
                    p.setMargins(tabMarginStart, tabMarginTop, tabMarginEnd, tabMarginBottom);
                    setTabFont(R.id.textView, defaultTabFont, i);
                    tab.requestLayout();
                }
                setTabTextColor(R.id.textView, tabSelectedTextColor, oldPage);
                setTabFont(R.id.textView, selectedTabFont, oldPage);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTabFont(R.id.textView, defaultTabFont, oldPage);
                setTabFont(R.id.textView, selectedTabFont, position);
                setTabTextColor(R.id.textView, tabDefaultTextColor, oldPage);
                setTabTextColor(R.id.textView, tabSelectedTextColor, position);
                oldPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setOrientation(VERTICAL);
        handleTypedArray(context, attrs, defStyleAttr);
    }


    public void addOnPageChangeListener(final ViewPager.OnPageChangeListener onPageChangeListener) {
        if (viewPager == null)
            return;
        viewPager.addOnPageChangeListener(onPageChangeListener);
    }

    public TabbedViewPager setTabsMargin(@DimenRes int start, @DimenRes int top, @DimenRes int end, @DimenRes int bottom) {
        tabMarginStart = (int) getDimenFromResource(getContext(), start);
        tabMarginTop = (int) getDimenFromResource(getContext(), top);
        tabMarginEnd = (int) getDimenFromResource(getContext(), end);
        tabMarginBottom = (int) getDimenFromResource(getContext(), bottom);
        return this;
    }

}
