package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.APIClient;
import com.example.fcinema_app.Utils.APIInterface;
import com.example.fcinema_app.models.NguoiDung;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoiMatKhauActivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar mToolbar;
    private ImageView imgBack;
    private TextInputEditText edOldPass,edNewPass,edComfirmPass;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau2);

        mToolbar = findViewById(R.id.toolbarDMK2);
        imgBack=findViewById(R.id.imgBackFromChangePass);
        edOldPass=findViewById(R.id.tnpMatKhauCu);
        edNewPass=findViewById(R.id.tnpMatKhauMoi2);
        edComfirmPass=findViewById(R.id.tnpNhapLaiMatKhauMoi2);

        imgBack.setOnClickListener(v -> {
            finish();
        });

        findViewById(R.id.btnSaveChangePass).setOnClickListener(v -> {
            if(validate()>0) {
                updatePassword();
                finish();
            }
        });

    }

    private void updatePassword(){
        APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
        String oldPass=edOldPass.getText().toString().trim();
        String newP=edNewPass.getText().toString().trim();
        NguoiDung nguoiDung=new NguoiDung(oldPass,newP,1);
        Call<Void> call=apiInterface.changeMatKhauNguoiDungByEmail(getEmail(),nguoiDung);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(DoiMatKhauActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);
                        String errorMessage = jsonObject.getString("message");
                        Toast.makeText(DoiMatKhauActivity.this, "" + errorMessage, Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(DoiMatKhauActivity.this,"Lỗi"+t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    private int validate(){
        int check=1;
        String oldP=edOldPass.getText().toString().trim();
        String newP=edNewPass.getText().toString().trim();
        String comfirmP=edComfirmPass.getText().toString().trim();

        if(oldP.isEmpty() || newP.isEmpty() || comfirmP.isEmpty()){
            Toast.makeText(DoiMatKhauActivity.this,"Vui lòng nhập đủ các trường",Toast.LENGTH_SHORT).show();
            check=-1;
        }else{
            if(!newP.equals(comfirmP)){
                Toast.makeText(DoiMatKhauActivity.this, "Xác nhận mật khẩu không trùng" , Toast.LENGTH_SHORT).show();
                check=-1;
            }
            if(newP.length()<6){
                Toast.makeText(DoiMatKhauActivity.this, "Mật khẩu phải trên 6 ký tự" , Toast.LENGTH_SHORT).show();
                check=-1;
            }
        }
        return check;
    }

    private String getEmail(){
        String email = getIntent().getStringExtra("email");
        return email;
    }
    private String getMK(){
        String matKhau = getIntent().getStringExtra("matKhau");
        return matKhau;
    }
}