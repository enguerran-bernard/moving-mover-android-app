package com.movingmover.oem.movingmover.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;

import com.movingmover.oem.movingmover.R;

import java.util.List;

public class IASelectorAdapter extends BaseAdapter{

    private Context mContext;
    private Spinner mSpinner;
    private LayoutInflater mInflater;
    private int mCheckedPosition = -1;
    private List<String> mNames;
    private List<Integer> mIconsRes;
    private final int mCount = 30;

    public IASelectorAdapter(Context context, Spinner spinner, List<String> names, List<Integer> iconsRes) {
        super();
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mSpinner = spinner;
        mNames = names;
        mIconsRes = iconsRes;
    }

    public void setCheckedPosition(int checkedPosition) {
        mCheckedPosition = checkedPosition;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup viewGroup) {
        final IASelectorItemHolder holder;
        if(view == null) {
            view = mInflater.inflate(R.layout.iaselector_row, viewGroup, false);
            holder = new IASelectorItemHolder();
            holder.mIcon = view.findViewById(R.id.selector_ia_icon);
            holder.mName = view.findViewById(R.id.selector_ia_name);
            holder.mCheckBox = view.findViewById(R.id.selector_ia_checkbox);
            view.setTag(holder);
        } else {
            holder = (IASelectorItemHolder) view.getTag();
        }
        holder.mIcon.setImageResource(R.drawable.ic_launcher_foreground);

        holder.mName.setText(mContext.getString(R.string.ia_name_default) + " " + position);
        holder.mCheckBox.setChecked(mCheckedPosition == position);
        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.mCheckBox.isChecked()) {
                    mCheckedPosition = position;
                    notifyDataSetChanged();
                    mSpinner.setSelection(position);
                }
            }
        });
        return view;
    }
}
