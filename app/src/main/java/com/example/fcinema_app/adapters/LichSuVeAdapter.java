package com.example.fcinema_app.adapters;

import android.content.Context;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.fcinema_app.R;
import com.example.fcinema_app.activities.MuaVeActivity;
import com.example.fcinema_app.models.LichSuVeModel;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        TextView tvMaVe, tvTrangThai, tvTenPhim, tvSoLuongVe, tvNgayChieu, tvCaChieu, tvTenGhe,tvNgayMua,tvTongTien;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null ){
            view = LayoutInflater.from(context).inflate(R.layout.lich_su_ve_item_layout, null);
            holder = new ViewHolder();
            holder.tvMaVe = view.findViewById(R.id.tvmaVeLSVItem);
            holder.tvTrangThai = view.findViewById(R.id.daTTLSCItem);
            holder.tvTenPhim = view.findViewById(R.id.tvTenPhimLSVItem);
            holder.tvSoLuongVe = view.findViewById(R.id.soluongveLSVItem);
            holder.tvNgayChieu = view.findViewById(R.id.tvNgayChieuLSV);
            holder.tvNgayMua=view.findViewById(R.id.tvNgayMuaSVItem);
            holder.tvTenGhe = view.findViewById(R.id.sogheLSVItem);
            holder.tvCaChieu=view.findViewById(R.id.tvCaChieuLSV);
            holder.tvTongTien=view.findViewById(R.id.tvTongTienItemVe);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
         holder.tvMaVe.setText(list.get(i).getMaVe());
        if(list.get(i).getTrangThai() == 1){
            holder.tvTrangThai.setText("Chưa thanh toán");
            holder.tvTrangThai.setTextColor(ContextCompat.getColor(context, R.color.darkRed));
        }
        if(list.get(i).getTrangThai() == 0){
            holder.tvTrangThai.setText("Đã thanh toán");
            holder.tvTrangThai.setTextColor(ContextCompat.getColor(context, R.color.darKGreen));
        }
        if(list.get(i).getTrangThai() == 2){
            holder.tvTrangThai.setText("Hết hạn");
            holder.tvTrangThai.setTextColor(ContextCompat.getColor(context, R.color.earthy));
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String tongTienFormat=decimalFormat.format(Float.parseFloat(list.get(i).getTongTT()));

        holder.tvTenPhim.setText(list.get(i).getTenPhim());
        holder.tvSoLuongVe.setText("Số ghế: "+list.get(i).getSoluongVe());
        holder.tvNgayChieu.setText(mSimpleDateFormat.format(list.get(i).getNgayChieu()));
        holder.tvNgayMua.setText(mSimpleDateFormat.format(list.get(i).getNgayMua()));
        holder.tvCaChieu.setText(list.get(i).getCaChieu());
        holder.tvTongTien.setText(tongTienFormat+" đ");

        holder.tvTenGhe.setText(list.get(i).getSoGhe().replace("\"",""));


        return view;
    }


   private Context context;

    private List<LichSuVeModel> list;

    public LichSuVeAdapter(Context context, List<LichSuVeModel> list) {
        this.context = context;
        this.list = list;
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

//    public static String ConverterChair(String data){
//        String newData = data.replace("[","").replace("]","");
//        String[] convert = newData.split(",");
//        StringBuilder stringBuilder = new StringBuilder();
//        for (String value : convert){
//            stringBuilder.append(MuaVeActivity.ConverterChairName(Integer.parseInt(value)) +",");
//        }
//        if(stringBuilder.length() > 0){
//            stringBuilder.setLength(stringBuilder.length() - 1);
//        }
//        return stringBuilder.toString();
//    }

}
