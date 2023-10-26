package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

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
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoiThongTinActivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar mToolbar;
    private ImageView imgBack;
    private TextInputEditText edHoTen,edNgaySinh,edEmail,edDienThoai,edDiaChi;
    private ImageView imgPicker;
    private static final int PICK_IMAGE_REQUEST = 1;
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd");
    private String base64Image,imgOld;

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
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });
        edNgaySinh.setOnClickListener(v -> {
            Calendar c=Calendar.getInstance();
            mYear=c.get(Calendar.YEAR);
            mMonth=c.get(Calendar.MONTH);
            mDate=c.get(Calendar.DATE);
            DatePickerDialog d=new DatePickerDialog(DoiThongTinActivity.this,0,ngaySinh,mYear,mMonth,mDate);
            d.show();
        });
        findViewById(R.id.btnUpdateUser).setOnClickListener(v -> {
            try {
                saveUpdateNguoiDung();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        edEmail.setEnabled(false);
        getNguoiDung();

    }
    private void saveUpdateNguoiDung() throws ParseException {
        if(validate()>0){
            base64Image = (base64Image == null) ? imgOld : base64Image;
            String anh=base64Image;
            String hoTen=edHoTen.getText().toString().trim();
            Date ngaySinh= sdf.parse(edNgaySinh.getText().toString());
            String dienThoai=edDienThoai.getText().toString().trim();
            String diaChi=edDiaChi.getText().toString().trim();

            NguoiDung nguoiDung=new NguoiDung(hoTen,dienThoai,anh,ngaySinh,diaChi);
            updateNguoiDung(nguoiDung);

        }
    }


    DatePickerDialog.OnDateSetListener ngaySinh=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear=year;
            mMonth=month;
            mDate=dayOfMonth;
            GregorianCalendar c=new GregorianCalendar(mYear,mMonth,mDate);
            edNgaySinh.setText(sdf.format(c.getTime()));

        }
    };
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
                    byte[] decodedBytes = Base64.decode(nguoiDung.getAnh(), Base64.DEFAULT);
                    Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

                    if (decodedBitmap != null) {
                        imgPicker.setImageBitmap(decodedBitmap);
                    } else {
                        imgPicker.setImageResource(R.drawable.imagepicker);
                    }
                }
            }

            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            base64Image = resizeImageAndConvertToBase64(selectedImageUri);
            if (base64Image != null) {
                byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                imgPicker.setImageBitmap(decodedBitmap);
            }
        }
    }

    private String resizeImageAndConvertToBase64(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            String base64Image = resizeImageAndConvertToBase64(inputStream);
            inputStream.close();
            return base64Image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private String resizeImageAndConvertToBase64(InputStream inputStream) throws IOException {
        int maxWidth = 512;
        int maxHeight = 512;

        Bitmap originalBitmap = BitmapFactory.decodeStream(inputStream);

        float scale = Math.min((float) maxWidth / originalBitmap.getWidth(), (float) maxHeight / originalBitmap.getHeight());

        int newWidth = Math.round(originalBitmap.getWidth() * scale);
        int newHeight = Math.round(originalBitmap.getHeight() * scale);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 95, baos);
        byte[] imageBytes = baos.toByteArray();
        String base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        originalBitmap.recycle();
        resizedBitmap.recycle();

        return base64Image;
    }


    private String getEmail(){
        String email = getIntent().getStringExtra("email");
        return email;
    }


}