package com.example.fcinema_app.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.fcinema_app.R;
import com.example.fcinema_app.models.PhimSapChieuModel;

import java.util.List;

public class PhimSapChieuAdapter  extends BaseAdapter {

    private Context context;
    private List<PhimSapChieuModel> list;

    public PhimSapChieuAdapter(Context context, List<PhimSapChieuModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder{
        ImageView image;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.phim_sap_chieu_item_layout, null);
            holder = new ViewHolder();
            holder.image = view.findViewById(R.id.imageItemPSC);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
            if(list.get(i).getImage().length() == 0){
                holder.image.setImageResource(R.drawable.poster);
            }else{
                byte[] decodedString = Base64.decode(list.get(i).getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.image.setImageBitmap(decodedByte);
            }

        return view;
    }
}
