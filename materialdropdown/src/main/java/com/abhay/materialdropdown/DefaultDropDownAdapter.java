package com.abhay.materialdropdown;

import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.util.List;

class DefaultDropDownAdapter extends MaterialDropDownAdapter<String> {

    private int mTextColor, mTextSize;

    DefaultDropDownAdapter(int layoutRes, List<String> items) {
        super(layoutRes, items);
    }

    @Override
    public void onBindView(View view, int position, String data) {
        TextView title = view.findViewById(R.id.title);
        title.setTextColor(mTextColor);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        title.setText(mItems.get(position));
    }

    @Override
    public void onItemSelected(int position, String data) {
        changeHeaderText(data);
    }

    void setTextColor(int textColor) {
        this.mTextColor = textColor;
    }

    void setTextSize(int textSize) {
        this.mTextSize = textSize;
    }
}
