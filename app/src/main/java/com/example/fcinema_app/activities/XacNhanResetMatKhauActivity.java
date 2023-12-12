package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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

public class XacNhanResetMatKhauActivity extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar mToolbar;
    private ImageView imgBack;
    private TextView tvEmail,tvResendCode;
    private TextInputEditText edCode,edNewPass,edComfirmpass;
    private CountDownTimer countDownTimer;
    private ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_reset_mat_khau);

        mToolbar = findViewById(R.id.toolbarDMK);
        imgBack=findViewById(R.id.imgBackFromComfirmResetPW);
        tvEmail=findViewById(R.id.tvEmailResetPassword);
        tvResendCode=findViewById(R.id.tvGuiLaiMa);
        edCode=findViewById(R.id.edCodeOTP);
        edNewPass=findViewById(R.id.edMatKhauMoi1);
        edComfirmpass=findViewById(R.id.edNhapLaiMatKhauMoi1);

        tvEmail.setText(getEmail());
        imgBack.setOnClickListener(v -> {
            startActivity(new Intent(XacNhanResetMatKhauActivity.this,DangNhapActivity.class));
            finish();
        });



        findViewById(R.id.btnComfirmResetPass).setOnClickListener(v -> {
            if(validate()>0){
                String email = getEmail();
                int resetToken = Integer.parseInt(edCode.getText().toString());
                String newPassword = edNewPass.getText().toString().trim();
                ResetPasswordRequest resetPasswordRequest=new ResetPasswordRequest(email,newPassword,resetToken);
                resetPasswordComfirm(resetPasswordRequest);
            }
        });


        startCountdownTimer();


    }
    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang xử lý...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) millisUntilFinished / 1000;
                tvResendCode.setText(String.valueOf(secondsRemaining));
            }

            @Override
            public void onFinish() {
                tvResendCode.setText("Gửi lại code");
                tvResendCode.setOnClickListener(v -> {
                    ResetPasswordRequest resetPasswordRequest=new ResetPasswordRequest(getEmail());
                    resetPasswordRequest(resetPasswordRequest);
                    startCountdownTimer();
                });
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private int validate(){
        int check=1;
        String code = edCode.getText().toString().trim();
        String newPassword = edNewPass.getText().toString().trim();
        String comfirmPass = edComfirmpass.getText().toString().trim();

        if(code.isEmpty() || newPassword.isEmpty() ||comfirmPass.isEmpty()){
            Toast.makeText(XacNhanResetMatKhauActivity.this, "Vui lòng nhập đủ các trường", Toast.LENGTH_SHORT).show();
            check=-1;

        }else{
            if(!newPassword.matches(comfirmPass)){
                Toast.makeText(XacNhanResetMatKhauActivity.this, "Xác nhận mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                check=-1;

            }
            if(newPassword.length()<6){
                Toast.makeText(XacNhanResetMatKhauActivity.this, "Mật khẩu phải trên 6 ký tự" , Toast.LENGTH_SHORT).show();
                check=-1;
            }
        }

        return check;
    }

    private void resetPasswordComfirm(ResetPasswordRequest resetPasswordRequest){
        showProgressDialog();
        APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
        Call<Void>call=apiInterface.comfirmRestMatKhau(resetPasswordRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    dismissProgressDialog();
                    Toast.makeText(XacNhanResetMatKhauActivity.this, "Mật khẩu đã được reset", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(XacNhanResetMatKhauActivity.this,DangNhapActivity.class));
                    finish();
                } else {
                    String errorBody = null;
                    try {
                        dismissProgressDialog();
                        errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);
                        String errorMessage = jsonObject.getString("message");
                        Toast.makeText(XacNhanResetMatKhauActivity.this, "Thất bại"+errorMessage, Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(XacNhanResetMatKhauActivity.this, "Lỗi"+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void resetPasswordRequest(ResetPasswordRequest resetPasswordRequest) {
        showProgressDialog();
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<NguoiDung> call = apiInterface.resetMatKhauRequest(resetPasswordRequest);
        call.enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                if (response.isSuccessful()) {
                    dismissProgressDialog();
                    Toast.makeText(XacNhanResetMatKhauActivity.this, "Reset code đã được gửi đến email"+getEmail(), Toast.LENGTH_SHORT).show();

                } else {
                    try {
                        dismissProgressDialog();
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);
                        String errorMessage = jsonObject.getString("message");
                        Toast.makeText(XacNhanResetMatKhauActivity.this, "Reset mật khẩu thất bại " + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(XacNhanResetMatKhauActivity.this, "Reset mật khẩu thất bại ", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }
            }

            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(XacNhanResetMatKhauActivity.this, "Lỗi" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Lỗi", t.getMessage());

            }
        });
    }
    private String getEmail(){
        Intent intent = getIntent();
        String iemail = intent.getStringExtra("email");
        return iemail;
    }
}