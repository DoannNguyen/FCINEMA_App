package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangNhapActivity extends AppCompatActivity {
    private Button btnLogin;
    private TextView tvDangKy, tvQuenMatKhau;
    private TextInputEditText edEmail,edPassword;
    private CheckBox ckRember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        btnLogin = findViewById(R.id.btnLogin);
        tvDangKy = findViewById(R.id.tvDangKyNgay);
        tvQuenMatKhau = findViewById(R.id.tvQuenMatKhau);
        edEmail=findViewById(R.id.edEmailDangNhap);
        edPassword=findViewById(R.id.edPassDangNhap);
        ckRember=findViewById(R.id.ckRemember);

        btnLogin.setOnClickListener(v -> {
            loginUser();

        });
        tvDangKy.setOnClickListener(v -> {
            startActivity(new Intent(DangNhapActivity.this, DangKyActivity.class));
            finish();

        });

        tvQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangNhapActivity.this, XacNhanMailActivity.class));
            }
        });

        restoringUser();
    }

    private void loginUser(){
        if(validateLogin()>0) {
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
            String email = edEmail.getText().toString().trim();
            String password = edPassword.getText().toString().trim();
            NguoiDung nguoiDung = new NguoiDung(email, password);
            Call<ResponseBody> call = apiInterface.loginUser(nguoiDung);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Intent iLogin = new Intent(DangNhapActivity.this, MainActivity.class);
                        iLogin.putExtra("email", email);
                        startActivity(iLogin);
                        rememberUser(email,password,ckRember.isChecked());
                        Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        try {
                            String errorBody = response.errorBody().string();
                            JSONObject jsonObject = new JSONObject(errorBody);
                            String errorMessage = jsonObject.getString("message");
                            Toast.makeText(DangNhapActivity.this, "" + errorMessage, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(DangNhapActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    Log.i("Lỗi", t.getMessage());

                }
            });
        }
    }

    private int validateLogin(){
        int check=1;
        String email=edEmail.getText().toString().trim();
        String password=edPassword.getText().toString().trim();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(DangNhapActivity.this, "Vui lòng nhập đủ các trường", Toast.LENGTH_SHORT).show();
            check=-1;
        }else{
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(DangNhapActivity.this, "Email định dạng không đúng", Toast.LENGTH_SHORT).show();
                check=-1;
            }
        }
        return check;


    }
    private void rememberUser(String email,String password,boolean status){
        SharedPreferences spf = getSharedPreferences("file", MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        String user = edEmail.getText().toString();
        String pass = edPassword.getText().toString();
        boolean ck = ckRember.isChecked();
        if (!ck) {
            editor.clear();
        } else {
            editor.putString("email", user);
            editor.putString("password", pass);
            editor.putBoolean("checked", ck);
        }
        editor.commit();
    }
    private void restoringUser(){
        SharedPreferences spf=getSharedPreferences("file",MODE_PRIVATE);
        boolean ck=spf.getBoolean("checked",false);
        if(ck){
            String user=spf.getString("email","");
            String pass=spf.getString("password","");
            edEmail.setText(user);
            edPassword.setText(pass);
            ckRember.setChecked(true);
        }
    }


}