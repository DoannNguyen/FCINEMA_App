package com.example.fcinema_app.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.fcinema_app.MainActivity;
import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.NguoiDungCallback;
import com.example.fcinema_app.activities.DangNhapActivity;
import com.example.fcinema_app.activities.DoiMatKhauActivity;
import com.example.fcinema_app.activities.DoiThongTinActivity;
import com.example.fcinema_app.activities.GioiThieuActivity;
import com.example.fcinema_app.models.NguoiDung;
import com.google.android.material.snackbar.Snackbar;

import de.hdodenhof.circleimageview.CircleImageView;


public class CaiDatFragment extends Fragment {

    private LinearLayout lnSettings;
    private TextView tvNameUser,tvEmailUser,tvChangeInfor,tvChangePass,tvAbout,tvLogout,tvAlert;
    private ImageView imgUser;
    private String oldPass;

    public CaiDatFragment() {
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
        return inflater.inflate(R.layout.fragment_cai_dat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lnSettings=view.findViewById(R.id.lnSettings);
        tvNameUser=view.findViewById(R.id.tvNameST);
        tvEmailUser=view.findViewById(R.id.tvEmailST);
        imgUser=view.findViewById(R.id.imgAnhTaiKhoan);
        tvChangeInfor=view.findViewById(R.id.tvChangeInformation);
        tvChangePass=view.findViewById(R.id.tvChangePass);
        tvAbout=view.findViewById(R.id.tvAbout);
        tvAlert=view.findViewById(R.id.tvNotification);
        tvLogout=view.findViewById(R.id.tvLogout);

        tvChangeInfor.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), DoiThongTinActivity.class) .putExtra("email",getEmail()));

        });
        tvChangePass.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), DoiMatKhauActivity.class).putExtra("email",getEmail()).putExtra("matKhau",oldPass));

        });
        tvAbout.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), GioiThieuActivity.class));
        });
        tvAlert.setOnClickListener(v -> {
            Snackbar snackbar = Snackbar.make(lnSettings, "Tính năng đang phát triển", Snackbar.LENGTH_SHORT);
                    snackbar.setAnchorView(R.id.nav_view)
                    .show();
        });


        tvLogout.setOnClickListener(v -> {
            Intent iLogout=new Intent(getActivity(), DangNhapActivity.class);
            startActivity(iLogout);
            getActivity().finish();
        });


        getNguoiDung();

    }


    private void getNguoiDung(){
        MainActivity activity=(MainActivity) getActivity();
        activity.getNguoiDungByEmail(new NguoiDungCallback() {
            @Override
            public void onNguoiDungReceived(NguoiDung nguoiDung) {
                if ( nguoiDung.getHoTen() != null && !nguoiDung.getHoTen().isEmpty()) {
                    tvNameUser.setText(" " + nguoiDung.getHoTen());
                    tvEmailUser.setText(nguoiDung.getEmail());
                } else {
                    tvNameUser.setText("Người dùng");
                    tvEmailUser.setText("");

                }
                oldPass=nguoiDung.getMatKhau();


                String anh=nguoiDung.getAnh();
                Glide.with(imgUser.getContext())
                        .load(anh)
                        .centerCrop()
                        .placeholder(R.drawable.user_default)
                        .into(imgUser);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
    private String getEmail(){
        MainActivity activity=(MainActivity) getActivity();
        String email=activity.getEmail();
        return email;

    }

    @Override
    public void onResume() {
        super.onResume();
        getNguoiDung();
    }
}