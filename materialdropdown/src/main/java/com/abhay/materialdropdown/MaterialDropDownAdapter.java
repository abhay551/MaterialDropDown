package com.abhay.materialdropdown;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class MaterialDropDownAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<T> mItems;
    private int mLayoutRes;

    private OnDropDownItemSelectedListener mOnDropDownItemSelectedListener;
    private InternalDropDownListener mInternalDropDownListener;

    public abstract void onBindView(View view, int position, T data);

    public abstract void onItemSelected(int position, T data);


    public MaterialDropDownAdapter(int layoutRes, List<T> items) {
        this.mItems = items;
        this.mLayoutRes = layoutRes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(mLayoutRes, viewGroup, false);
        return new MaterialDropDownViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        T data = mItems.get(position);
        if (data != null) {
            final MaterialDropDownViewHolder materialDropDownViewHolder = (MaterialDropDownViewHolder) holder;
            onBindView(materialDropDownViewHolder.getRootView(), position,data );
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemSelected(position, mItems.get(position));
                    if (mOnDropDownItemSelectedListener != null) {
                        mOnDropDownItemSelectedListener.onItemSelected(materialDropDownViewHolder.getRootView(), position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    void addItems(List<T> items) {
        mItems = items;
        this.notifyDataSetChanged();
    }

    void addItem(T item) {
        mItems.add(item);
        this.notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mItems.get(position);
    }

    public List<T> getItems(){
        return mItems;
    }

    static class MaterialDropDownViewHolder extends RecyclerView.ViewHolder {

        MaterialDropDownViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        View getRootView() {
            return itemView;
        }
    }

    void setOnItemSelectedListener(OnDropDownItemSelectedListener onDropDownItemSelectedListener) {
        this.mOnDropDownItemSelectedListener = onDropDownItemSelectedListener;
    }

    void setOnInternalItemSelectedListener(InternalDropDownListener internalDropDownListener) {
        this.mInternalDropDownListener = internalDropDownListener;
    }

    public void changeHeaderText(String s) {
        mInternalDropDownListener.onInternalDropDownItemSelected(s);
    }

}
