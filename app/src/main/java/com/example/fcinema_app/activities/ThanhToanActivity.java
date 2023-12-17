package com.example.fcinema_app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    private TextView tvTenPhim, tvThoiLuong, tvNgayChieu, tvPhongChieu, tvCaChieu, tvViTri, tvGia;
    private TextView tvSoLuong, tvTongTT, tvNoFood, tvTienDoAn, tvTienVe;
    private VeModel veModel;
    private ViTriGheModel viTriGheModel;
    private Button btnXacNhan;
    private RadioButton rdoTienMat, rdoZalopay;
    private ImageView imgPoster;
    private String token;
    private final StringBuilder stringBuilder = new StringBuilder();
    private String email;
    private ProgressDialog mProgressDialog;
    DecimalFormat decimalFormat=new DecimalFormat("###,###");
    private List<DoAnModel> models;

    @SuppressLint({"MissingInflatedId", "SimpleDateFormat", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(553, Environment.SANDBOX);

        onBindView();
        Dialog dialog = new Dialog(ThanhToanActivity.this);
        dialog.setContentView(R.layout.progress_dialog);
        mProgressDialog = new ProgressDialog(dialog, "Đang tải...");
        setupProgressDialog(mProgressDialog, dialog);
        RecyclerView listView = findViewById(R.id.lvDoAn2);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Bundle bundle = getIntent().getBundleExtra("value");
        assert bundle != null;
        PhimModel model = (PhimModel) bundle.getSerializable("phim");
        int soLuongGhe = (Integer) bundle.getInt("soLuongVe");
        email = bundle.getString("email");
        ArrayList<Integer> ghe = bundle.getIntegerArrayList("ghe");
         models = (List<DoAnModel>) bundle.getSerializable("doAn");
        DoAnAdapter2 anAdapter2 = new DoAnAdapter2(ThanhToanActivity.this, models);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(anAdapter2);

        JsonArray jsonArray = new JsonArray();
        for (Integer value : ghe) {
            stringBuilder.append(MuaVeActivity.ConverterChairName(value)).append(", ");
            jsonArray.add(MuaVeActivity.ConverterChairName(value));
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 2);
            String[] jsonObject = stringBuilder.toString().split(", ");
        }
        int giaDoAn = 0;

        if(models.size() != 0){
            for( int i = 0 ; i < models.size() ; i++){
                giaDoAn += (models.get(i).getSoLuong()* models.get(i).getGiaDoAn());
            }
            tvNoFood.setVisibility(View.GONE);
        }
        tvTienDoAn.setText(decimalFormat.format(giaDoAn)+"đ");
       if(model != null && soLuongGhe != 0){
           String formatGiaVe=decimalFormat.format(Float.parseFloat(model.getGiaPhim()));
           String formatTong=decimalFormat.format((soLuongGhe*Integer.parseInt(model.getGiaPhim())+giaDoAn));
            tvTienVe.setText(decimalFormat.format((soLuongGhe*Integer.parseInt(model.getGiaPhim())))+"đ");
           String imageUrl = model.getImage();
           Glide.with(this)
                   .load(imageUrl)
                   .placeholder(R.drawable.img_default)
                   .error(R.drawable.img_default)
                   .into(imgPoster);

           if(model.getTenPhim().length() < 12){
               tvTenPhim.setText(model.getTenPhim());
           }else{
               tvTenPhim.setText(model.getTenPhim()+"...");
           }
           tvThoiLuong.setText(model.getThoiLuong());
           tvNgayChieu.setText(new SimpleDateFormat("yyyy-MM-dd").format(model.getNgayChieu()));
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
            JSONObject data = orderApi.createOrder(""+(soLuongGhe*Integer.parseInt(model.getGiaPhim())+giaDoAn));
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
        veModel.setNgayMua(simpleDateFormat.format(Calendar.getInstance().getTime()));
        veModel.setTongTien((soLuongGhe*Integer.parseInt(model.getGiaPhim()))+giaDoAn);
        veModel.setIdLichChieu(model.getIdLichChieu());
        veModel.setTrangThai(1);
        veModel.setPhuongThucTT("Tiền mặt");
        veModel.setEmail(email);

        viTriGheModel = new ViTriGheModel();
        viTriGheModel.setTenGhe(stringBuilder.toString());
        viTriGheModel.setIdPhongChieu(model.getIdPhongChieu());
        viTriGheModel.setIdVe(veModel.getIdVe());

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

    private void onBindView() {
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
        tvNoFood = findViewById(R.id.tvNoFood);
        tvTienDoAn = findViewById(R.id.tvTienDoAnTT);
        tvTienVe = findViewById(R.id.tvTienVe);
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
                    new Handler().postDelayed(() -> {
                        mProgressDialog.DialogDismiss();
                        Toast.makeText(ThanhToanActivity.this, "Đặt vé thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ThanhToanActivity.this,MainActivity.class);
                        intent.putExtra("email", email);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    },750);
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
                veModel.setTrangThai(0);
                veModel.setPhuongThucTT("ZaloPay");
                AddVeAndViTriGhe();
            }

            @Override
            public void onPaymentCanceled(String s, String s1) {

            }

            @Override
            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {

            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
    private void setupProgressDialog(ProgressDialog progressDialog,Dialog dialog){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width =  WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = 300;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setDimAmount(0.7f);
        progressDialog.setCancelable(false);
    }
}