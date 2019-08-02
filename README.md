# MaterialDropDown

![Alt Text](https://github.com/abhay551/MaterialDropDown/blob/master/app/src/main/res/drawable/screenshot.gif)

## GRADLE

1) Add it in your root build.gradle at the end of repositories:

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

2) Add the dependency

```
dependencies {
	        implementation 'com.github.abhay551:MaterialDropDown:VERSION-NUMBER'
	}
```

## USAGE

### For Simple Dropdown

#### XML

```
    <com.abhay.materialdropdown.MaterialDropDown
        android:id="@+id/dropdown"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="10dp" />
```

#### JAVA

```
       mDropDown.setOnItemSelectedListener(new OnDropDownItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position) {
               
            }
        });
        mDropDown.setDropdownList(getDummyList());
        mDropDown.setPositionSelection(0);
```

### For Customize Dropdown

#### XML

```
    <com.abhay.materialdropdown.MaterialDropDown
        android:id="@+id/dropdown"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="10dp" />
```


#### JAVA

##### create your own custom adapter with your custom model and view like below

```
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
```


##### set custom Adapter to MaterialDropDown
```

       mDropDownWithCustomLayout.setOnItemSelectedListener(new OnDropDownItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position) {
               
            }
        });
        mDropDownWithCustomLayout.setDropDownAdapter(new MyDropDownAdapter(R.layout.row_custom_dropdown, getDummyDataModels()));
        mDropDownWithCustomLayout.setCustomModelPositionSelection(getDummyDataModels().get(0).mTittle, 0);
```



