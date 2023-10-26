package com.example.fcinema_app.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;

import com.example.fcinema_app.MainActivity;
import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.NguoiDungCallback;
import com.example.fcinema_app.activities.DangNhapActivity;
import com.example.fcinema_app.activities.DoiMatKhauActivity;
import com.example.fcinema_app.activities.DoiThongTinActivity;
import com.example.fcinema_app.models.NguoiDung;

import de.hdodenhof.circleimageview.CircleImageView;


public class CaiDatFragment extends Fragment {

    private Button btnDoiMatKhau, btnSuaThogTin;
    private LinearLayout lnLogout;
    private TextView tvNameUser;
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

        btnSuaThogTin = view.findViewById(R.id.btnChinhSuaThongTin);
        btnDoiMatKhau = view.findViewById(R.id.btnDoiMatKhau);
        lnLogout=view.findViewById(R.id.lnLogout);
        tvNameUser=view.findViewById(R.id.tvNameST);
        imgUser=view.findViewById(R.id.imgAnhTaiKhoan);

        btnSuaThogTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DoiThongTinActivity.class) .putExtra("email",getEmail()));
            }
        });

        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DoiMatKhauActivity.class).putExtra("email",getEmail()).putExtra("matKhau",oldPass));
            }
        });

        lnLogout.setOnClickListener(v -> {
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
                } else {
                    tvNameUser.setText("Người dùng");
                }
                oldPass=nguoiDung.getMatKhau();

                if (nguoiDung.getAnh() != null && !nguoiDung.getAnh().isEmpty()) {
                    byte[] decodedBytes = Base64.decode(nguoiDung.getAnh(), Base64.DEFAULT);
                    Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                    imgUser.setImageBitmap(decodedBitmap);
                } else {
                    imgUser.setImageResource(R.drawable.imagepicker);
                }
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