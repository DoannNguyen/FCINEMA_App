package com.example.fcinema_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.fcinema_app.R;
import com.example.fcinema_app.models.TheLoaiModel;

import java.util.List;

public class TheLoaiAdapter extends BaseAdapter {

    private Context mContext;
    private List<TheLoaiModel> mList;

    public TheLoaiAdapter(Context context, List<TheLoaiModel> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        if(mList.size() > 0){
            return mList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        Button mButton;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.the_loai_button_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.mButton = view.findViewById(R.id.btnTheLoai);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mButton.setText(mList.get(i).getTenTheLoai());
        return view;
    }
}
