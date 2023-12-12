package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fcinema_app.MainActivity;
import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.APIClient;
import com.example.fcinema_app.Utils.APIInterface;
import com.example.fcinema_app.models.NguoiDung;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
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
    private ImageView imgLogingWithGG;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 35;
    private ConstraintLayout mLayout;
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
        imgLogingWithGG = findViewById(R.id.imgLogingWithGG);
        mLayout = findViewById(R.id.img);

        btnLogin.setOnClickListener(v -> {
            showProgressDialog();
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

        imgLogingWithGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(DangNhapActivity.this, gso);
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        restoringUser();
    }
    private void showProgressDialog() {
        ProgressDialog progressDialog = new ProgressDialog(DangNhapActivity.this);
        progressDialog.setMessage("Đăng nhập..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new Handler().postDelayed(() -> progressDialog.dismiss(), 500);
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
                        //Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        showSnackBar(mLayout,"Đăng nhập thành công");
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
            //Toast.makeText(DangNhapActivity.this, "Vui lòng nhập đủ các trường", Toast.LENGTH_SHORT).show();
            showSnackBar(mLayout,"Vui lòng nhập đủ các trường");
            check=-1;
        }else{
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(DangNhapActivity.this, "Email định dạng không đúng", Toast.LENGTH_SHORT).show();
                showSnackBar(mLayout,"Email định dạng không đúng" );
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String email = account.getEmail();
            String hoTen = account.getDisplayName();
            String anh = "";
            int hienThi = 1;
            Log.e("TAG", "handleSignInResult: "+account.getPhotoUrl() );
            NguoiDung nguoiDung = new NguoiDung(email, anh, hoTen);
            registerUser(nguoiDung);
        } catch (ApiException e) {
            Log.e("TAG", "signInResult:failed code=" + e.getStatusCode());
        }
    }
    private void registerUser(NguoiDung nguoiDung){
        APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
        Call<ResponseBody>call= apiInterface.regisWithGG(nguoiDung);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Intent iLogin = new Intent(DangNhapActivity.this, MainActivity.class);
                    iLogin.putExtra("email", nguoiDung.getEmail());
                    startActivity(iLogin);
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
                Toast.makeText(DangNhapActivity.this, "Lôi" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Lỗi",t.getMessage());

            }
        });
    }
    private void showSnackBar(View view,String message){
        Snackbar snackbar = Snackbar.make(view,message,Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}