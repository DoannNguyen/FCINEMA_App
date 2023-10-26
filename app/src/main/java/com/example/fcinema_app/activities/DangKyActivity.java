package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fcinema_app.MainActivity;
import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.APIClient;
import com.example.fcinema_app.Utils.APIInterface;
import com.example.fcinema_app.models.NguoiDung;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKyActivity extends AppCompatActivity {

    private TextView tvDangNhap;
    private TextInputEditText edEmail,edPassword,edRePass;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        tvDangNhap = findViewById(R.id.tvDangNhapNgay);
        edEmail=findViewById(R.id.edEmailDangKy);
        edPassword=findViewById(R.id.edPassDangKy);
        edRePass=findViewById(R.id.edReturnPassDangKy);


        findViewById(R.id.btnRegister).setOnClickListener(v -> {
            if(validateRegis()>0){
                saveUser();
            }
        });
        tvDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangKyActivity.this,DangNhapActivity.class));
                finish();
            }
        });

    }

    private void saveUser(){
        String email=edEmail.getText().toString().trim();
        String password=edPassword.getText().toString().trim();
        String hoten="";

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user1);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        String base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);

        String dienThoai="";
        Date ngaySinh=new Date();
        String diaChi="";
        Integer hienThi=1;
        NguoiDung nguoiDung=new NguoiDung(email,hoten,password,dienThoai,base64Image,ngaySinh,diaChi,hienThi);
        registerUser(nguoiDung);
    }

    private int validateRegis(){
        int check=1;
        String email=edEmail.getText().toString().trim();
        String password=edPassword.getText().toString().trim();
        String rePass=edRePass.getText().toString().trim();
        if(email.isEmpty() || password.isEmpty() ||rePass.isEmpty()){
            Toast.makeText(DangKyActivity.this, "Vui lòng nhập đủ các trường" , Toast.LENGTH_SHORT).show();
            check=-1;
        }else{
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(DangKyActivity.this, "Email định dạng không đúng" , Toast.LENGTH_SHORT).show();
                check=-1;
            }
            if(!password.equals(rePass)){
                Toast.makeText(DangKyActivity.this, "Xác nhận mật khẩu không trùng" , Toast.LENGTH_SHORT).show();
                check=-1;
            }
        }
        return check;

    }
    private void registerUser(NguoiDung nguoiDung){
        APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
        Call<ResponseBody>call=apiInterface.regiserUser(nguoiDung);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Intent iLogin = new Intent(DangKyActivity.this, DangNhapActivity.class);
                    startActivity(iLogin);
                    Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);
                        String errorMessage = jsonObject.getString("message");
                        Toast.makeText(DangKyActivity.this, "" + errorMessage, Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DangKyActivity.this, "Lôi" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Lỗi",t.getMessage());

            }
        });

    }
}