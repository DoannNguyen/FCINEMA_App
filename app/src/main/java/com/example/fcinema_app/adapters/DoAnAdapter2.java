package com.example.fcinema_app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fcinema_app.R;
import com.example.fcinema_app.models.DoAnModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class DoAnAdapter2 extends BaseAdapter {
    private final Context mContext;
    private final List<DoAnModel> mList;
    private final NumberFormat formatter = new DecimalFormat("###,###,##0");

    public DoAnAdapter2(Context context, List<DoAnModel> list) {
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
        return mList.get(i).getIdDoAn();
    }

    private static class  ViewHolder{
        ImageView img;
        TextView tvTen, tvSoLuong, tvGia;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.do_an_item_layout2, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.tvTen = view.findViewById(R.id.tvTenDoAn2);
            viewHolder.tvSoLuong = view.findViewById(R.id.tvSoLuong2);
            viewHolder.img = view.findViewById(R.id.imgeAnhDoAn2);
            viewHolder.tvGia = view.findViewById(R.id.tvGiaDoAn2);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvTen.setText(mList.get(i).getTenDoAn());
        viewHolder.tvSoLuong.setText("SL: "+mList.get(i).getSoLuong()+"");
        viewHolder.tvGia.setText(formatter.format(mList.get(i).getGiaDoAn())+"đ");
        String imageUrl = mList.get(i).getAnh();
        Glide.with(mContext)
                .load(imageUrl)
                .placeholder(R.drawable.img_default)
                .error(R.drawable.img_default)
                .into(viewHolder.img);

        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
