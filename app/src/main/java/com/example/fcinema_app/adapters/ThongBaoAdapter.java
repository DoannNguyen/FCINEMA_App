package com.example.fcinema_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fcinema_app.R;
import com.example.fcinema_app.models.ThongBaoModel;

import java.util.List;

public class ThongBaoAdapter extends BaseAdapter {
    private Context context;
    private List<ThongBaoModel> list;

    public ThongBaoAdapter (Context context, List<ThongBaoModel> list){
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
        TextView tenPhim, thoiGian;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder  = null;
        if (view == null){
            view= LayoutInflater.from(context).inflate(R.layout.thong_bao_item_layout, null);
            holder = new ViewHolder();
            holder.tenPhim = view.findViewById(R.id.tenPhimTB);
            holder.thoiGian = view.findViewById(R.id.thoigianTB);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tenPhim.setText(list.get(i).getTenPhim());
        holder.thoiGian.setText(list.get(i).getThoiGian());


        return view;
    }
}
