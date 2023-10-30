package com.example.fcinema_app.adapters;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.fcinema_app.R;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.fcinema_app.R;
import com.example.fcinema_app.models.PhimModel;

import java.util.List;

public class ImageSlideShowAdapter extends PagerAdapter {

    private Context context;
    private List<PhimModel> phimModelList;

    public ImageSlideShowAdapter(Context context, List<PhimModel> phimModelList) {
        this.context = context;
        this.phimModelList = phimModelList;
    }

    @Override
    public int getCount() {
        if(phimModelList!=null){
            return phimModelList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(container.getContext()).inflate(R.layout.layout_item_photo,container,false);
        ImageView imgPhoto=view.findViewById(R.id.imgSlider);

        PhimModel phimModel=phimModelList.get(position);
        if(phimModel!=null){
            byte[] decodedString = Base64.decode(phimModel.getImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            Glide.with(context).load(decodedByte).into(imgPhoto);
        }else{
            Glide.with(context).load(R.drawable.imagepicker).into(imgPhoto);
        }
        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}
