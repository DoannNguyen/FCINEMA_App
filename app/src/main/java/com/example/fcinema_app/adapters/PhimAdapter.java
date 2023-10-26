package com.example.fcinema_app.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fcinema_app.R;
import com.example.fcinema_app.models.PhimModel;

import java.util.ArrayList;
import java.util.List;

public class PhimAdapter extends BaseAdapter {

    private Context mContext;
    private List<PhimModel> mList;

    public PhimAdapter(Context context, List<PhimModel> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        if(mList != null){
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
        return i;
    }

    private class PhimViewHolder{
        ImageView mImageView;
        TextView tv;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PhimViewHolder holder = null;
        if(view == null){
            holder = new PhimViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.grid_item, viewGroup,false);
            holder.mImageView = view.findViewById(R.id.image);
            view.setTag(holder);
        }else{
            holder = (PhimViewHolder) view.getTag();
        }
        if(mList.get(i).getImage().length() == 0){
            holder.mImageView.setImageResource(R.drawable.poster);
        }else{
            byte[] decodedString = Base64.decode(mList.get(i).getImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.mImageView.setImageBitmap(decodedByte);
        }
        return view;
    }
}
