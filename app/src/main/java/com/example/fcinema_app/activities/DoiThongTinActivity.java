package com.example.fcinema_app.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.LocaleList;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.fcinema_app.MainActivity;
import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.APIClient;
import com.example.fcinema_app.Utils.APIInterface;
import com.example.fcinema_app.Utils.NguoiDungCallback;
import com.example.fcinema_app.models.NguoiDung;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.cloudinary.android.MediaManager;
public class DoiThongTinActivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar mToolbar;
    private ImageView imgBack;
    private TextInputEditText edHoTen,edNgaySinh,edEmail,edDienThoai,edDiaChi;
    private ImageView imgPicker;
    private ProgressDialog progressDialog;
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd");

    private String imgUrl,imgOld;

    int mYear,mMonth,mDate;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_thong_tin);

        mToolbar = findViewById(R.id.toolbarDTT);
        imgBack=findViewById(R.id.imgBackFromChangeIF);
        edHoTen=findViewById(R.id.tnpHoTen);
        edNgaySinh=findViewById(R.id.tnpNgaySinh);
        edEmail=findViewById(R.id.tnpEmail);
        edDienThoai=findViewById(R.id.tnpDienThoai);
        edDiaChi=findViewById(R.id.tnpDiaChi);
        imgPicker=findViewById(R.id.imgThemAnh);

        imgBack.setOnClickListener(v -> {
            finish();
        });
        imgPicker.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            chooseImage.launch(intent);

        });
        edNgaySinh.setOnClickListener(v -> {
            try {
                showDatePicker(edNgaySinh, DoiThongTinActivity.this);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        findViewById(R.id.btnUpdateUser).setOnClickListener(v -> {
            try {
                saveUpdateNguoiDung();
                finish();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        edEmail.setEnabled(false);
        getNguoiDung();

    }
    private void saveUpdateNguoiDung() throws ParseException {
        if(validate()>0){
            imgUrl = (imgUrl == null) ? imgOld : imgUrl;
            String anh=imgUrl;
            String hoTen=edHoTen.getText().toString().trim();
            Date ngaySinh= sdf.parse(edNgaySinh.getText().toString());
            String dienThoai=edDienThoai.getText().toString().trim();
            String diaChi=edDiaChi.getText().toString().trim();

            NguoiDung nguoiDung=new NguoiDung(hoTen,dienThoai,anh,ngaySinh,diaChi);
            updateNguoiDung(nguoiDung);

        }
    }


    public static Locale getLocaleForCalendarInstance(Context context) {
        Locale locale = new Locale("vi","VN");
        LocaleList locales = new LocaleList(locale);
        context.getResources().getConfiguration().setLocales(locales);
        context.getResources().updateConfiguration(context.getResources().getConfiguration(),
                context.getResources().getDisplayMetrics());

        return locale;
    }
    public void showDatePicker(EditText editText, Context context) throws ParseException {
        Locale locale = new Locale("vi", "VN");
        Locale.setDefault(locale);

        Calendar calendar = Calendar.getInstance(getLocaleForCalendarInstance(context));

        if (editText != null && !TextUtils.isEmpty(editText.getText())) {
            Date currentDate = sdf.parse(editText.getText().toString());

            if (currentDate != null) {
                calendar.setTime(currentDate);
            }
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,R.style.CustomDatePickerDialog,
                (view, year, monthOfYear, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    String selectedDate = sdf.format(calendar.getTime());

                    editText.setText(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.setOnShowListener(dialog -> {
            Button positiveButton = datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            Button negativeButton = datePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE);

            if (positiveButton != null) {
                positiveButton.setText("Chọn");
                positiveButton.setTextColor(Color.BLACK);
            }

            if (negativeButton != null) {
                negativeButton.setText("Hủy");
                negativeButton.setTextColor(Color.BLACK);
            }
        });
        datePickerDialog.show();
    }
    private int validate(){
        int check=1;
        String hoTen=edHoTen.getText().toString().trim();
        String ngaySinh=edNgaySinh.getText().toString().trim();
        String dienThoai=edDienThoai.getText().toString().trim();
        String diaChi=edDiaChi.getText().toString().trim();
        if(hoTen.isEmpty() || ngaySinh.isEmpty() || dienThoai.isEmpty() || diaChi.isEmpty()){
            Toast.makeText(DoiThongTinActivity.this, "Vui lòng nhập đủ các trường" , Toast.LENGTH_SHORT).show();
            check=-1;
        }
        return check;

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
    private void updateNguoiDung(NguoiDung nguoiDung){
        APIInterface apiInterface=APIClient.getClient().create(APIInterface.class);
        Call<Void> call=apiInterface.changeThongTinNguoiDungByEmail(getEmail(),nguoiDung);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(DoiThongTinActivity.this, "Cập nhật thông tin thành công" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }
    private void getNguoiDung(){
        APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
        Call<NguoiDung>call=apiInterface.getNguoiDungByEmail(getEmail());
        call.enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                if(response.isSuccessful()){
                    NguoiDung nguoiDung=new NguoiDung();
                    nguoiDung= response.body();
                    imgOld=nguoiDung.getAnh();

                    Log.i("ẢNH",nguoiDung.getAnh());
                    edEmail.setText(nguoiDung.getEmail());
                    if(nguoiDung.getHoTen()!=null){
                        edHoTen.setText(""+nguoiDung.getHoTen());
                    }
                    if(nguoiDung.getNgaySinh()!=null){
                        edNgaySinh.setText(""+sdf.format(nguoiDung.getNgaySinh()));
                    }
                    if(nguoiDung.getDienThoai()!=null){
                        edDienThoai.setText(""+nguoiDung.getDienThoai());
                    }
                    if(nguoiDung.getDiaChi()!=null){
                        edDiaChi.setText(""+nguoiDung.getDiaChi());
                    }

                    Glide.with(imgPicker.getContext())
                            .load(imgOld)
                            .centerCrop()
                            .placeholder(R.drawable.img_default)
                            .into(imgPicker);
                }
            }

            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {

            }
        });
    }

    private void uploadImage(Uri path){
        initCloudinary();
        MediaManager.get().upload(path)
                .option("folder", "storage/client")
                .callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                showProgressDialog();
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {


            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                dismissProgressDialog();
                String link=resultData.get("secure_url").toString();
                imgUrl =link;
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {

            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {

            }
        }).dispatch();

    }

    private void initCloudinary(){
        Map config=new HashMap();
        config.put("cloud_name","dxkvtjsiq");
        config.put("secure",true);
        config.put("api_key","631347248512345");
        config.put("api_secret","Y7n46nP4XGfJBXpptqn4G5UPep0");
        MediaManager.init(this,config);
    }

    ActivityResultLauncher<Intent> chooseImage= registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()== Activity.RESULT_OK){
                        Intent data=result.getData();
                        Uri uri=data.getData();
                        uploadImage(uri);
                        if(null!= uri){
                            imgPicker.setImageURI(uri);
                        }
                    }
                }
            }
    );


    private String getEmail(){
        String email = getIntent().getStringExtra("email");
        return email;
    }


}