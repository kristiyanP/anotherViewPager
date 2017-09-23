package io.kri.anotherViewPager;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kristiyan Petrov
 */
public class TabbedViewPagerAdapter extends PagerAdapter {

    private List<TabbedViewPagerItem> tabbedViewPagerItems;
    private TabLayout tabLayout;

    @Override
    public Object instantiateItem(final ViewGroup collection, final int position) {
        final TabbedViewPagerItem item = tabbedViewPagerItems.get(position);
        item.bindItem(collection.getContext(), collection);
        collection.addView(item.getItem());
        return item.getItem();
    }

    public List<TabbedViewPagerItem> getTabbedViewPagerItems() {
        if (tabbedViewPagerItems == null || tabbedViewPagerItems.isEmpty()) {
            throw new RuntimeException("Adapter is empty");
        }
        return tabbedViewPagerItems;
    }

    private void populateTabs() {
        for (int position = 0; position < tabbedViewPagerItems.size(); position++) {
            final TabbedViewPagerItem item = tabbedViewPagerItems.get(position);
            updateTab(position, item);
        }
    }

    private void updateTab(final int position, final TabbedViewPagerItem item) {
        if (tabLayout.getTabAt(position) == null)
            return;

        final View view = item.getTabWithCustomView(tabLayout.getContext());
        if (view != null) {
            tabLayout.getTabAt(position).setCustomView(view);
        } else {
            tabLayout.getTabAt(position).setText(item.getTitle());
        }
    }

    public TabbedViewPagerItem getItemAtPosition(final int position) {
        if (tabbedViewPagerItems == null || tabbedViewPagerItems.isEmpty()) {
            throw new RuntimeException("Adapter is empty");
        } else if (tabbedViewPagerItems.get(position) == null) {
            throw new RuntimeException("No item at position" + position);
        }
        return tabbedViewPagerItems.get(position);
    }

    public TabbedViewPagerAdapter addItem(final TabbedViewPagerItem item) {
        if (tabbedViewPagerItems == null) {
            tabbedViewPagerItems = new ArrayList<>();
        }
        this.tabbedViewPagerItems.add(item);
        notifyDataSetChanged();
        return this;
    }


    @Override
    public void destroyItem(final ViewGroup collection, final int position, final Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        if (tabbedViewPagerItems == null) {
            return 0;
        }
        return tabbedViewPagerItems.size();
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        if (tabbedViewPagerItems == null || tabbedViewPagerItems.isEmpty()) {
            throw new RuntimeException("Adapter is empty");
        } else if (tabbedViewPagerItems.get(position) == null) {
            throw new RuntimeException("No item at position" + position);
        }
        return tabbedViewPagerItems.get(position).getTitle();
    }

    public void setTabLayout(final TabLayout tabLayout) {
        this.tabLayout = tabLayout;
        populateTabs();
    }
}
