package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.APIClient;
import com.example.fcinema_app.Utils.APIInterface;
import com.example.fcinema_app.models.NguoiDung;
import com.example.fcinema_app.models.RequestAuthEmail;
import com.example.fcinema_app.models.ResetPasswordRequest;
import com.google.android.material.snackbar.Snackbar;
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

    private CoordinatorLayout coordinatorLayout;
    private TextView tvDangNhap, tvResend,tvEmail;
    private TextInputEditText edEmail,edPassword,edRePass,edCode;
    private ImageView imgDissmiss;
    private Dialog dialog;
    private ProgressDialog progressDialog;
    private CountDownTimer countDownTimer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        coordinatorLayout=findViewById(R.id.coordinatorRegister);
        tvDangNhap = findViewById(R.id.tvDangNhapNgay);
        edEmail=findViewById(R.id.edEmailDangKy);
        edPassword=findViewById(R.id.edPassDangKy);
        edRePass=findViewById(R.id.edReturnPassDangKy);


        findViewById(R.id.btnRegister).setOnClickListener(v -> {
            if(validateRegis()>0){
                authEmail();
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
    private void dialogOTP(){
        dialog = new Dialog(DangKyActivity.this);
        dialog.setContentView(R.layout.layout_dialog_auth_email);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.getAttributes().dimAmount = 0.8f;
            window.getAttributes().flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        }

        imgDissmiss=dialog.findViewById(R.id.imgDissmissDialog);
        tvEmail=dialog.findViewById(R.id.tvEmailAuth);
        edCode=dialog.findViewById(R.id.edCodeEmailAuth);
        tvResend=dialog.findViewById(R.id.tvResentEmailAuth);

        String email=edEmail.getText().toString().trim();
        tvEmail.setText(email);

        dialog.findViewById(R.id.btnSentAuthEmail).setOnClickListener(v -> {
            int otpValue = Integer.parseInt(edCode.getText().toString().trim());
            saveUser(otpValue);

        });

        imgDissmiss.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
        startCountdownTimer();
    }
    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) millisUntilFinished / 1000;
                tvResend.setText(String.valueOf(secondsRemaining));
            }

            @Override
            public void onFinish() {
                tvResend.setText("Gửi lại code");
                tvResend.setOnClickListener(v -> {
                    authEmail();
                    startCountdownTimer();
                });
            }
        }.start();
    }


    private void saveUser(int otp){
        String email=edEmail.getText().toString().trim();
        String password=edPassword.getText().toString().trim();
        String hoten="";

        String anh = "";

        String dienThoai="";
        Date ngaySinh=new Date();
        String diaChi="";
        Integer hienThi=1;
        NguoiDung values=new NguoiDung(email,hoten,password,dienThoai,anh,ngaySinh,diaChi,hienThi);
        RequestAuthEmail requestAuthEmail=new RequestAuthEmail(otp,values);
        registerUser(requestAuthEmail);
    }

    private int validateRegis(){
        int check=1;
        String email=edEmail.getText().toString().trim();
        String password=edPassword.getText().toString().trim();
        String rePass=edRePass.getText().toString().trim();
        if(email.isEmpty() || password.isEmpty() ||rePass.isEmpty()){
            showSnackbar("Vui lòng nhập đủ các trường");
            check=-1;
        }else{
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                showSnackbar("Email định dạng không đúng");
                check=-1;
            }
            if(!password.equals(rePass)){
                showSnackbar("Xác nhận mật khẩu không trùng");
                check=-1;
            }
            if(password.length()<6){
                showSnackbar("Mật khẩu phải trên 6 ký tự");
                check=-1;
            }
        }
        return check;

    }
    private void authEmail(){
        showProgressDialog();
        String email=edEmail.getText().toString().trim();
        RequestAuthEmail requestAuthEmail=new RequestAuthEmail(email);
        APIInterface apiInterface=APIClient.getClient().create(APIInterface.class);
        Call<NguoiDung> call=apiInterface.authEmail(requestAuthEmail);
        call.enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                dismissProgressDialog();
                if(response.isSuccessful()){
                    dialogOTP();
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);
                        String errorMessage = jsonObject.getString("message");
                        showSnackbar(""+errorMessage);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {
                Log.i("Lỗi",t.getMessage());

            }
        });
    }
    private void registerUser(RequestAuthEmail requestAuthEmail){
        APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
        Call<ResponseBody>call=apiInterface.regiserUser(requestAuthEmail);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Intent iLogin = new Intent(DangKyActivity.this, DangNhapActivity.class);
                    startActivity(iLogin);
                    new Handler().postDelayed(() -> finish(), 1500);
                    showSnackbar("Đăng ký thành công");

                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);
                        String errorMessage = jsonObject.getString("message");
                        showSnackbar(""+errorMessage);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showSnackbar("Lỗi"+t.getMessage());
                Log.i("Lỗi",t.getMessage());

            }
        });

    }

    private void showSnackbar(String message){
        Snackbar.make(coordinatorLayout,message, Snackbar.LENGTH_SHORT)
                .show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}