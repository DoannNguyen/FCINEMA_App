package com.example.fcinema_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.fcinema_app.R;
import com.example.fcinema_app.models.PhimModel;

import java.util.ArrayList;
import java.util.List;

public class PhimAdapter extends BaseAdapter implements Filterable {

    private final Context mContext;
    private List<PhimModel> mList;
    private final List<PhimModel> list;
    Costumfilter mCostumfilter;

    public PhimAdapter(Context context, List<PhimModel> list) {
        mContext = context;
        mList = list;
        this.list = list;
    }

    @Override
    public int getCount() {
        if(mList.size() != 0){
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

    @Override
    public Filter getFilter() {
        if(mCostumfilter == null){
            mCostumfilter = new Costumfilter();
    }
        return mCostumfilter;
    }

    private static class PhimViewHolder{
        ImageView mImageView;
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
        Glide.with(holder.mImageView.getContext())
                .load(mList.get(i).getImage())
                .centerCrop()
                .placeholder(R.drawable.default_img)
                .error(
                        Glide.with(holder.mImageView)
                                .load(mList.get(i).getImage()))
                .into(holder.mImageView);
        return view;
    }
    class Costumfilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
                constraint = constraint.toString().toLowerCase();
                ArrayList<PhimModel> filters = new ArrayList<>();
                if(constraint.length() != 0){
                    for (int i = 0; i < list.size(); i++) {
                        //probleme peut etre ici
                        if (list.get(i).getTenPhim().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filters.add(list.get(i));
                        }
                    }
                    results.count = filters.size();
                    results.values = filters;
                }else {
                    results.count = list.size();
                    results.values = list;
                }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mList = (List<PhimModel>) filterResults.values;
            notifyDataSetChanged();
        }
    }}
