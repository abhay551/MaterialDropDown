package com.abhay.samplematerialdropdown;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.abhay.materialdropdown.MaterialDropDown;
import com.abhay.materialdropdown.OnDropDownItemSelectedListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MaterialDropDown mDropDown;
    MaterialDropDown mDropDownWithCustomLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDropDown = findViewById(R.id.dropdown);
        mDropDownWithCustomLayout = findViewById(R.id.dropdown_with_custom_layout);

        mDropDown.setOnItemSelectedListener(new OnDropDownItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position) {
                Toast.makeText(MainActivity.this, "Dropdown clicked " + position, Toast.LENGTH_SHORT).show();
            }
        });
        mDropDown.setDropdownList(getDummyList());
        mDropDown.setPositionSelection(9);


        mDropDownWithCustomLayout.setOnItemSelectedListener(new OnDropDownItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position) {
                Toast.makeText(MainActivity.this, "Dropdown clicked " + position, Toast.LENGTH_SHORT).show();
            }
        });
        mDropDownWithCustomLayout.setDropDownAdapter(new MyDropDownAdapter(R.layout.row_custom_dropdown, getDummyDataModels()));
        mDropDownWithCustomLayout.setCustomModelPositionSelection(getDummyDataModels().get(0).mTittle, 0);
    }

    private ArrayList<String> getDummyList() {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            strings.add("Item " + i);
        }
        return strings;
    }

    private ArrayList<DataModel> getDummyDataModels() {
        ArrayList<DataModel> dataModels = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            DataModel dataModel = new DataModel();
            dataModel.mIndex = i;
            dataModel.mTittle = "Item " + i;
            dataModels.add(dataModel);
        }
        return dataModels;
    }
}
