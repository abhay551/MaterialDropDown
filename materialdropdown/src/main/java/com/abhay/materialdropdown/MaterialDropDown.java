package com.abhay.materialdropdown;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MaterialDropDown extends RelativeLayout {

    private Context mContext;
    private TextView mTitle;
    private ImageView mIcon;

    private PopupWindow mPopupWindow;
    private MaterialDropDownAdapter mDropDownAdapter = null;

    private View mRootPopupView;
    RecyclerView mRecyclerView;
    RelativeLayout mMainLayout;


    private int mHeaderTextColor, mHeaderTextSize, mHeaderBgColor, mTextColor, mTextSize, mArrowSrc, mDropDownBg;

    private OnDropDownItemSelectedListener mOnDropDownItemSelectedListener;

    private boolean mDownArrowEnabled = true;
    private int mSelectionPosition = 0;


    public MaterialDropDown(Context context) {
        this(context, null);
    }

    public MaterialDropDown(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialDropDown(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(attrs);
        init(context);
    }

    private void readAttrs(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.MaterialDropDown);
        mHeaderTextColor = array.getColor(R.styleable.MaterialDropDown_md_header_text_color, Color.BLACK);
        mHeaderTextSize = array.getDimensionPixelSize(R.styleable.MaterialDropDown_md_header_text_color, getResources().getDimensionPixelSize(R.dimen.default_text_size));
        mHeaderBgColor = array.getColor(R.styleable.MaterialDropDown_md_header_bg_color, Color.WHITE);
        mTextColor = array.getColor(R.styleable.MaterialDropDown_md_text_color, Color.BLACK);
        mTextSize = array.getDimensionPixelSize(R.styleable.MaterialDropDown_md_text_size, getResources().getDimensionPixelSize(R.dimen.default_text_size));
        mArrowSrc = array.getResourceId(R.styleable.MaterialDropDown_md_arrow_src, R.drawable.ic_arrow_drop_down);
        mDropDownBg = array.getResourceId(R.styleable.MaterialDropDown_md_dropdown_bg_color, R.drawable.dropdown_bg);
        array.recycle();
    }

    private void init(Context context) {
        this.mContext = context;
        final View view = LayoutInflater.from(context).inflate(R.layout.md_dropdown, this, true);
        mMainLayout = view.findViewById(R.id.main);
        mTitle = view.findViewById(R.id.title);
        mIcon = view.findViewById(R.id.icon);

        mMainLayout.setBackgroundColor(mHeaderBgColor);
        mTitle.setTextColor(mHeaderTextColor);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mHeaderTextSize);
        mIcon.setImageResource(mArrowSrc);

        mMainLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.setWidth(v.getWidth());
                if (!mPopupWindow.isShowing()) {
                    mDownArrowEnabled = false;
                    mPopupWindow.showAsDropDown(v, 0, Utils.convertDpToPixel(8, mContext));
                } else {
                    mDownArrowEnabled = true;
                    mPopupWindow.dismiss();
                }
                rotateArrow();

            }
        });
        initPopUpWindow();
    }

    private void initPopUpWindow() {
        mRootPopupView = LayoutInflater.from(mContext).inflate(R.layout.md_popup, this, false);
        mRecyclerView = mRootPopupView.findViewById(R.id.rvList);
        mPopupWindow = new PopupWindow(mContext);
        mPopupWindow.setContentView(mRootPopupView);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(mContext.getDrawable(mDropDownBg));
        mPopupWindow.setElevation(Utils.convertDpToPixel(8, mContext));
        mMainLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mPopupWindow.setWidth(mMainLayout.getWidth());
                mPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                mMainLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mDownArrowEnabled = true;
                rotateArrow();
            }
        });
        mRootPopupView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mPopupWindow.dismiss();
                return true;
            }
        });
    }

    private void rotateArrow() {
        if (mDownArrowEnabled) {
            mIcon.animate().rotation(0).start();
        } else {
            mIcon.animate().rotation(-180).start();
        }
    }

    private void changeHeaderText(String text) {
        if (!TextUtils.isEmpty(text))
            mTitle.setText(text);
    }

    public void setOnItemSelectedListener(OnDropDownItemSelectedListener onDropDownItemSelectedListener) {
        this.mOnDropDownItemSelectedListener = onDropDownItemSelectedListener;
    }

    private InternalDropDownListener mInternalDropDownListener = new InternalDropDownListener() {
        @Override
        public void onInternalDropDownItemSelected(String title) {
            changeHeaderText(title);
            mPopupWindow.dismiss();
        }
    };

    private void setInternalAdapter() {
        mDropDownAdapter.setOnInternalItemSelectedListener(mInternalDropDownListener);
        mDropDownAdapter.setOnItemSelectedListener(mOnDropDownItemSelectedListener);
        this.mRecyclerView.setAdapter(mDropDownAdapter);
        mPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void setDropdownList(List<String> dropdownList) {
        mDropDownAdapter = new DefaultDropDownAdapter(R.layout.md_popup_row, dropdownList);
        ((DefaultDropDownAdapter) mDropDownAdapter).setTextColor(mTextColor);
        ((DefaultDropDownAdapter) mDropDownAdapter).setTextSize(mTextSize);
        setInternalAdapter();
        setPositionSelection(mSelectionPosition);
    }

    public <T> void setDropDownAdapter(MaterialDropDownAdapter<T> dropDownAdapter) {
        this.mDropDownAdapter = dropDownAdapter;
        setInternalAdapter();
    }

    public void setPositionSelection(int positionSelection) {
        this.mSelectionPosition = positionSelection;
        if (mOnDropDownItemSelectedListener != null) {
            if (mDropDownAdapter != null && mSelectionPosition < mDropDownAdapter.getItems().size()) {
                if (mDropDownAdapter.getItem(mSelectionPosition) instanceof String) {
                    changeHeaderText((String) mDropDownAdapter.getItem(mSelectionPosition));
                }
                mOnDropDownItemSelectedListener.onItemSelected(mRecyclerView.getChildAt(mSelectionPosition), mSelectionPosition);
            }
        }
    }

    public void setCustomModelPositionSelection(String title, int positionSelection) {
        changeHeaderText(title);
        setPositionSelection(positionSelection);
    }

}
