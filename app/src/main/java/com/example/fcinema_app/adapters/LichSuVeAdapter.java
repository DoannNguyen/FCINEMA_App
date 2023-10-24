package com.example.fcinema_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fcinema_app.R;
import com.example.fcinema_app.models.LichSuVeModel;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

public class LichSuVeAdapter extends BaseAdapter {

    private SimpleDateFormat mSimpleDateFormat;

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
        TextView maVe, trangThai, tenPhim, soluongVe, ngayChieu, gioChieu, soGhe;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null ){
            view = LayoutInflater.from(context).inflate(R.layout.lich_su_ve_item_layout, null);
            holder = new ViewHolder();
            holder.maVe = view.findViewById(R.id.maVeLSVItem);
            holder.trangThai = view.findViewById(R.id.daTTLSCItem);
            holder.tenPhim = view.findViewById(R.id.tenPhimLSVItem);
            holder.soluongVe = view.findViewById(R.id.soluongveLSVItem);
            holder.ngayChieu = view.findViewById(R.id.ngaychieuLSVItem);
            holder.gioChieu = view.findViewById(R.id.giochieuLSVItem);
            holder.soGhe = view.findViewById(R.id.sogheLSVItem);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
         holder.maVe.setText(list.get(i).getMaVe());
        if(list.get(i).getTrangThai() == 1){
            holder.trangThai.setText("Chưa thanh toán");
        }
        if(list.get(i).getTrangThai() == 0){
            holder.trangThai.setText("Đã thanh toán");
        }
        holder.tenPhim.setText(list.get(i).getTenPhim());
        holder.soluongVe.setText(list.get(i).getSoluongVe());
        holder.ngayChieu.setText(mSimpleDateFormat.format(list.get(i).getNgayChieu()));
        holder.gioChieu.setText(list.get(i).getThoiGian());
        holder.soGhe.setText(list.get(i).getSoGhe());


        return view;
    }


   private Context context;

    private List<LichSuVeModel> list;

    public LichSuVeAdapter(Context context, List<LichSuVeModel> list) {
        this.context = context;
        this.list = list;
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

}
