package io.kri.anotherViewPager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * @author Kristiyan Petrov
 */
public abstract class TabbedViewPagerItem {

    protected ViewGroup layout;

    View getTabWithCustomView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab, null);
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(getTitle());
        return view;
    }
    
    public abstract String getTitle();

    public abstract int getLayoutResId();

    protected abstract void bind(Context context, ViewGroup parent);

    public final void bindItem(Context context, ViewGroup parent) {
        layout = (ViewGroup) LayoutInflater.from(context).inflate(getLayoutResId(), parent, false);
        bind(context, parent);
    }

    public View getItem() {
        return layout;
    }

    public View findViewById(int id) {
        return layout.findViewById(id);
    }
}
