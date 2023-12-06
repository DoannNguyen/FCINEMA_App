package com.example.fcinema_app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fcinema_app.MainActivity;
import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.APIClient;
import com.example.fcinema_app.Utils.APIInterface;
import com.example.fcinema_app.adapters.DoAnAdapter2;
import com.example.fcinema_app.models.CreateOrder;
import com.example.fcinema_app.models.DoAnModel;
import com.example.fcinema_app.models.PhimModel;
import com.example.fcinema_app.models.ProgressDialog;
import com.example.fcinema_app.models.RequestData;
import com.example.fcinema_app.models.VeModel;
import com.example.fcinema_app.models.ViTriGheModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class ThanhToanActivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar mToolbar;
    private TextView tvTenPhim, tvThoiLuong, tvNgayChieu, tvPhongChieu, tvCaChieu, tvViTri, tvGia, tvSoLuong, tvTongTT;
    private SimpleDateFormat mSimpleDateFormat;
    private VeModel veModel;
    private ViTriGheModel viTriGheModel;
    private Button btnXacNhan;
    private Gson mGson;
    private JsonArray jsonArray;
    private RadioButton rdoTienMat, rdoZalopay;
    private ImageView imgPoster;
    private String token;
    private String[] jsonObject;
    private StringBuilder stringBuilder = new StringBuilder();
    private String email;
    private Dialog mDialog;
    private ProgressDialog mProgressDialog;
    DecimalFormat decimalFormat=new DecimalFormat("###,###");
    private List<DoAnModel> models;
    private DoAnAdapter2 mAnAdapter2;

    @SuppressLint({"MissingInflatedId", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(553, Environment.SANDBOX);

        tvTenPhim =findViewById(R.id.tvTenPhimXNDV);
        tvThoiLuong = findViewById(R.id.tvThoiLuongXNDV);
        tvNgayChieu = findViewById(R.id.tvNgayChieuXNDV);
        tvPhongChieu = findViewById(R.id.tvPhongChieuXNDV);
        tvCaChieu = findViewById(R.id.tvCaChieuXNDV);
        tvViTri = findViewById(R.id.tvVidTriGhe);
        tvGia = findViewById(R.id.tvGIaiVeXNDV);
        tvSoLuong = findViewById(R.id.tvSoLuongXNDV);
        tvTongTT = findViewById(R.id.tvTongThanhToanXNDV);
        btnXacNhan = findViewById(R.id.btnXacNhanDatVe);
        rdoTienMat = findViewById(R.id.rdoTienMat);
        rdoZalopay = findViewById(R.id.rdoZaloPay);
        imgPoster=findViewById(R.id.imgPosterPayment);
        mDialog = new Dialog(ThanhToanActivity.this);
        mDialog.setContentView(R.layout.progress_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width =  WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = 300;
        mDialog.getWindow().setAttributes(lp);
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mDialog.getWindow().setDimAmount(0.7f);
        mProgressDialog = new ProgressDialog(mDialog, "Đang tải...");
        mProgressDialog.setCancelable(false);
        ListView listView = findViewById(R.id.lvDoAn2);

        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Bundle bundle = getIntent().getBundleExtra("value");
        assert bundle != null;
        PhimModel model = (PhimModel) bundle.getSerializable("phim");
        int soLuongGhe = (Integer) bundle.getInt("soLuongVe");
        email = bundle.getString("email");
        ArrayList<Integer> ghe = bundle.getIntegerArrayList("ghe");
         models = (List<DoAnModel>) bundle.getSerializable("doAn");
        mAnAdapter2 = new DoAnAdapter2(ThanhToanActivity.this, models);
        listView.setAdapter(mAnAdapter2);

        jsonArray = new JsonArray();
        for (Integer value : ghe) {
            stringBuilder.append(MuaVeActivity.ConverterChairName(value)).append(", ");
            jsonArray.add(MuaVeActivity.ConverterChairName(value));
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 2);
            jsonObject = stringBuilder.toString().split(", ");
        }
        Log.e("TAG", "onCreate: "+ stringBuilder.toString());
        int giaDoAn = 0;

        if(models != null){
            for( int i = 0 ; i < models.size() ; i++){
                giaDoAn += (models.get(i).getSoLuong()* models.get(i).getGiaDoAn());
            }
        }

       if(model != null && soLuongGhe != 0){
           String formatGiaVe=decimalFormat.format(Float.parseFloat(model.getGiaPhim()));
           String formatTong=decimalFormat.format((soLuongGhe*Integer.parseInt(model.getGiaPhim())+giaDoAn));

           String imageUrl = model.getImage();
           Glide.with(this)
                   .load(imageUrl)
                   .placeholder(R.drawable.img_default)
                   .error(R.drawable.img_default)
                   .into(imgPoster);

           tvTenPhim.setText(model.getTenPhim());
           tvThoiLuong.setText(model.getThoiLuong());
           tvNgayChieu.setText(mSimpleDateFormat.format(model.getNgayChieu()));
           tvPhongChieu.setText(model.getTenPhong());
           tvCaChieu.setText(model.getCaChieu());
           tvViTri.setText(stringBuilder.toString());
           tvGia.setText(formatGiaVe+"đ");
           tvSoLuong.setText(""+soLuongGhe);
           tvTongTT.setText(formatTong+"đ");
       }

        CreateOrder orderApi = new CreateOrder();
        try {
            assert model != null;
            JSONObject data = orderApi.createOrder(""+(soLuongGhe*Integer.parseInt(model.getGiaPhim())));
            String code = data.getString("returncode");
            if (code.equals("1")) {
                token = data.getString("zptranstoken").toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

       veModel = new VeModel();
       veModel.setIdVe("VIE"+ new Date().getTime());
        veModel.setSoVe(soLuongGhe);
        veModel.setNgayMua(mSimpleDateFormat.format(Calendar.getInstance().getTime()));
        veModel.setTongTien((soLuongGhe*Integer.parseInt(model.getGiaPhim()))+giaDoAn);
        veModel.setIdLichChieu(model.getIdLichChieu());
        veModel.setTrangThai(1);
        veModel.setPhuongThucTT("Tiền mặt");
        veModel.setEmail(email);

        viTriGheModel = new ViTriGheModel();
        viTriGheModel.setTenGhe(stringBuilder.toString());
        viTriGheModel.setIdPhongChieu(model.getIdPhongChieu());
        viTriGheModel.setIdVe(veModel.getIdVe());

        mToolbar = findViewById(R.id.toolbarTT);

        findViewById(R.id.imgBackFromPaymentRequest).setOnClickListener(v -> {
            finish();

        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rdoTienMat.isChecked()){
                    AddVeAndViTriGhe();
                }
                if(rdoZalopay.isChecked()){
                    PayByZalopay();
                }
            }
        });
    }

    private void AddVeAndViTriGhe(){
        RequestData requestData = new RequestData(veModel, viTriGheModel, stringBuilder.toString(), models);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        mProgressDialog.DialogShowing();
        Call<ResponseBody> call = apiInterface.addDevice(requestData);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    new Handler().postDelayed(() -> mProgressDialog.DialogDismiss(),250);
                    Toast.makeText(ThanhToanActivity.this, "Đặt vé thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ThanhToanActivity.this,MainActivity.class);
                    intent.putExtra("email", email);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage());
            }
        });
    }

    private void PayByZalopay(){
        ZaloPaySDK.getInstance().payOrder(ThanhToanActivity.this, token, "demozpdk://app", new PayOrderListener() {
            @Override
            public void onPaymentSucceeded(String s, String s1, String s2) {
                Toast.makeText(getApplicationContext(), "thanh cong", Toast.LENGTH_SHORT).show();
                veModel.setTrangThai(0);
                veModel.setPhuongThucTT("ZaloPay");
                AddVeAndViTriGhe();
            }

            @Override
            public void onPaymentCanceled(String s, String s1) {
                Toast.makeText(getApplicationContext(), "huy", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {

                Toast.makeText(getApplicationContext(), "loi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}