package com.abhay.samplematerialdropdown;

import android.view.View;
import android.widget.TextView;

import com.abhay.materialdropdown.MaterialDropDownAdapter;

import java.util.List;

public class MyDropDownAdapter extends MaterialDropDownAdapter<DataModel> {

    public MyDropDownAdapter(int layoutRes, List<DataModel> items) {
        super(layoutRes, items);
    }

    @Override
    public void onBindView(View view, int position, DataModel data) {
        TextView title = view.findViewById(R.id.title);
        TextView index = view.findViewById(R.id.index);
        title.setText(data.mTittle);
        index.setText(data.mIndex+"");
    }

    @Override
    public void onItemSelected(int position, DataModel data) {
        changeHeaderText(data.mTittle);
    }
}
