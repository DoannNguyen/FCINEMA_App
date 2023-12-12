package com.example.fcinema_app.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fcinema_app.MainActivity;
import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.APIClient;
import com.example.fcinema_app.Utils.APIInterface;
import com.example.fcinema_app.activities.ChiTietVeActivity;
import com.example.fcinema_app.activities.TimKiemVeActivity;
import com.example.fcinema_app.adapters.LichSuVeAdapter;
import com.example.fcinema_app.models.LichSuVeModel;
import com.example.fcinema_app.models.ProgressDialog;
import com.example.fcinema_app.models.VeModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LichSuVeFragment extends Fragment {

    private ListView listView;
    private List<LichSuVeModel> list = new ArrayList<>();;
    private List<LichSuVeModel> originalList = new ArrayList<>();;

    private LichSuVeAdapter lichSuVeAdapter;

    private TextView tvNoItem;
    private Dialog mDialog;
    private ProgressDialog mProgressDialog;
    private BottomSheetDialog bottomSheetDialog;
    private ImageView imgSearch,imgFilter;

    public LichSuVeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lich_su_ve, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.listview);
        tvNoItem = view.findViewById(R.id.tvNoItem);
        imgFilter=view.findViewById(R.id.imgFilterTicket);
        imgSearch=view.findViewById(R.id.imgSearchTicket);

        // Khởi tạo dialog lọc theo trạng thái
        bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.layout_filter_ticket);
        bottomSheetDialog.setCancelable(false);

        mDialog = new Dialog(requireContext());
        mDialog.setContentView(R.layout.progress_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width =  WindowManager.LayoutParams.MATCH_PARENT;
        lp.height =  400;
        mDialog.getWindow().setAttributes(lp);
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mDialog.getWindow().setDimAmount(0.7f);
        mProgressDialog = new ProgressDialog(mDialog, "Đang tải...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.DialogShowing();
        getVeDat();
        lichSuVeAdapter = new LichSuVeAdapter(getContext(),list);
        listView.setAdapter(lichSuVeAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), ChiTietVeActivity.class);
                intent.putExtra("LCV", list.get(i));
                startActivity(intent);
            }
        });

        imgSearch.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), TimKiemVeActivity.class).putExtra("email",getEmail()));
        });
        imgFilter.setOnClickListener(v -> {
            showBottomSheetDialog();
        });


    }

    // Hiển thi bottom sheet lọc vé theo trạng thái
    private void showBottomSheetDialog() {
        TextView tvCancel = bottomSheetDialog.findViewById(R.id.tvCancelFilterTicket);
        TextView tvAll = bottomSheetDialog.findViewById(R.id.tvFilterAllTicket);
        TextView tvUnpaid = bottomSheetDialog.findViewById(R.id.tvUnpaidTicket);
        TextView tvPaid = bottomSheetDialog.findViewById(R.id.tvPaidTicket);
        TextView tvExpired = bottomSheetDialog.findViewById(R.id.tvExpiredTicket);

        tvCancel.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
        });

        tvAll.setOnClickListener(v -> {
            list.clear();
            list.addAll(originalList);

            if (lichSuVeAdapter != null) {
                lichSuVeAdapter.notifyDataSetChanged();
            }

            bottomSheetDialog.dismiss();
        });

        tvUnpaid.setOnClickListener(v -> {
            filterListByStatus(1);
            bottomSheetDialog.dismiss();
        });

        tvPaid.setOnClickListener(v -> {
            filterListByStatus(0);
            bottomSheetDialog.dismiss();
        });

        tvExpired.setOnClickListener(v -> {
            filterListByStatus(2);
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }

    private void filterListByStatus(int status) {
        List<LichSuVeModel> filterList = new ArrayList<>();
        for (LichSuVeModel ve : originalList) {
            if (ve.getTrangThai() >= 0 && ve.getTrangThai() == status) {
                filterList.add(ve);
            }
        }

        list.clear();
        list.addAll(filterList);

        if (lichSuVeAdapter != null) {
            lichSuVeAdapter.notifyDataSetChanged();
        }
        updateVisibility();
    }
    private void updateVisibility() {
        if (list.isEmpty()) {
            tvNoItem.setVisibility(View.VISIBLE);
        } else {
            tvNoItem.setVisibility(View.GONE);
        }
    }
    private void getVeDat() {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<LichSuVeModel>> call = apiInterface.getVeDat(getEmail());
        call.enqueue(new Callback<List<LichSuVeModel>>() {
            @Override
            public void onResponse(Call<List<LichSuVeModel>> call, Response<List<LichSuVeModel>> response) {
                if(response.isSuccessful()){
                    if(response.body().size() != 0){
                        list.clear();
                        assert response.body() != null;
                        originalList.addAll(response.body());
                        list.addAll(originalList);
                        lichSuVeAdapter.notifyDataSetChanged();
                        updateVisibility();
                    }
                    new Handler().postDelayed(() -> mProgressDialog.DialogDismiss(),750);
                }else{
                    Log.e("TAG", "onResponse: " );
                }
            }

            @Override
            public void onFailure(Call<List<LichSuVeModel>> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage() );
            }
        });
    }
    private String getEmail(){
        MainActivity activity = (MainActivity) getActivity();
        if(activity != null){
           return activity.getEmail();
        }
        return null;
    }
}