package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.APIClient;
import com.example.fcinema_app.Utils.APIInterface;
import com.example.fcinema_app.models.NguoiDung;
import com.example.fcinema_app.models.ResetPasswordRequest;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XacNhanMailActivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar mToolbar;
    private ImageView imgBack;
    private Button btnSend;
    private TextInputEditText edEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_mail);

        mToolbar = findViewById(R.id.toolbarQMK);
        btnSend = findViewById(R.id.btnSendOPT);
        imgBack=findViewById(R.id.imgBackFromRequestResetPW);
        edEmail=findViewById(R.id.edXacNhanEmail);

        imgBack.setOnClickListener(v -> {
            finish();
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=edEmail.getText().toString().trim();
                if(email.isEmpty()){
                    Toast.makeText(XacNhanMailActivity.this, "Vui lòng nhập email "+email, Toast.LENGTH_SHORT).show();
                }else{
                    if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        ResetPasswordRequest resetPasswordRequest=new ResetPasswordRequest(email);
                        resetPasswordRequest(resetPasswordRequest);
                    }else{
                        Toast.makeText(XacNhanMailActivity.this, "Email định dạng không đúng "+email, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void resetPasswordRequest(ResetPasswordRequest resetPasswordRequest){
        APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
        Call<NguoiDung> call=apiInterface.resetMatKhauRequest(resetPasswordRequest);
        call.enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(XacNhanMailActivity.this, "Reset code đã được gửi đến email"+edEmail.getText().toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(XacNhanMailActivity.this, XacNhanResetMatKhauActivity.class);
                    intent.putExtra("email", edEmail.getText().toString().trim());
                    startActivity(intent);
                    finish();
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);
                        String errorMessage = jsonObject.getString("message");
                        Toast.makeText(XacNhanMailActivity.this, "Reset mật khẩu thất bại" + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(XacNhanMailActivity.this, "Reset mật khẩu thất bại - " , Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }
            }

            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {
                Toast.makeText(XacNhanMailActivity.this, "Lỗi"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Lỗi",t.getMessage());

            }
        });
    }
}