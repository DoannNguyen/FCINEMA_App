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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.OnMinusItemClick;
import com.example.fcinema_app.Utils.OnPlusItemClick;
import com.example.fcinema_app.models.DoAnModel;

import java.util.List;

public class DoAnAdapter extends BaseAdapter {

    private Context mContext;
    private List<DoAnModel> mList;
    private OnMinusItemClick mOnMinusItemClick;
    private OnPlusItemClick mOnPlusItemClick;

    public void setOnMinusItemClick(OnMinusItemClick onMinusItemClick) {
        mOnMinusItemClick = onMinusItemClick;
    }

    public void setOnPlusItemClick(OnPlusItemClick onPlusItemClick) {
        mOnPlusItemClick = onPlusItemClick;
    }

    public DoAnAdapter(Context context, List<DoAnModel> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        if (mList != null){
            return mList.size();
        }
        return 0;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mList.get(i).getIdDoAn();
    }

    private class ViewHolder {
        ImageView imgAnh, imgMinus, imgPlus;
        TextView tvTen, tvGia, tvSoLuong;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.do_an_item_layout,viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.tvSoLuong = view.findViewById(R.id.tvSoLuong);
            viewHolder.tvTen = view.findViewById(R.id.tvTenDoAn);
            viewHolder.tvGia = view.findViewById(R.id.tvGiaDoAn);
            viewHolder.imgAnh = view.findViewById(R.id.imgeAnhDoAn);
            viewHolder.imgMinus = view.findViewById(R.id.imgMinus);
            viewHolder.imgPlus = view.findViewById(R.id.imgPlus);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvTen.setText(mList.get(i).getTenDoAn());
        viewHolder.tvGia.setText(mList.get(i).getGiaDoAn()+"");
        String imageUrl = mList.get(i).getAnh();
        Glide.with(mContext)
                .load(imageUrl)
                .placeholder(R.drawable.img_default)
                .error(R.drawable.img_default)
                .into(viewHolder.imgAnh);
        ViewHolder finalViewHolder = viewHolder;
        viewHolder.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnPlusItemClick != null){
                    int soluong = Integer.parseInt(finalViewHolder.tvSoLuong.getText().toString());
                    finalViewHolder.tvSoLuong.setText((soluong+1)+"");
                    mList.get(i).setSoLuong(soluong+1);
                    mOnPlusItemClick.OnPlusClick();
                }
            }
        });
        viewHolder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnMinusItemClick != null){
                    int soluong = Integer.parseInt(finalViewHolder.tvSoLuong.getText().toString());

                    if(soluong - 1 < 0){
                        finalViewHolder.tvSoLuong.setText(0+"");
                        mList.get(i).setSoLuong(0);
                    }else{
                        finalViewHolder.tvSoLuong.setText((soluong-1)+"");
                        mList.get(i).setSoLuong(soluong-1);
                    }
                    mOnMinusItemClick.onMinusClick();
                }
            }
        });
        return view;
    }
    public float getSum(){
        float sum = 0;
        for(int i = 0 ; i < mList.size() ; i++){
            if(mList.get(i).getSoLuong() != 0 ){
                sum += mList.get(i).getGiaDoAn() * mList.get(i).getSoLuong();
            }
        }
        return sum;
    }
}
