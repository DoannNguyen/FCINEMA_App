package com.example.fcinema_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fcinema_app.R;
import com.example.fcinema_app.models.DoAnModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class DoAnAdapter2 extends RecyclerView.Adapter<DoAnAdapter2.ViewHolder> {
    private final Context mContext;
    private final List<DoAnModel> mList;
    private static final NumberFormat formatter = new DecimalFormat("###,###,##0");

    public DoAnAdapter2(Context context, List<DoAnModel> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.do_an_item_layout2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DoAnModel item = mList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvTen, tvSoLuong, tvGia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tvTenDoAn2);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong2);
            img = itemView.findViewById(R.id.imgeAnhDoAn2);
            tvGia = itemView.findViewById(R.id.tvGiaDoAn2);
        }

        public void bind(DoAnModel item) {
            tvTen.setText(item.getTenDoAn());
            tvSoLuong.setText("SL: " + item.getSoLuong());
            tvGia.setText(formatter.format(item.getGiaDoAn()) + "đ");

            String imageUrl = item.getAnh();
            Glide.with(itemView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default)
                    .into(img);
        }
    }
}
