package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.fcinema_app.R;
import com.example.fcinema_app.adapters.ThongBaoAdapter;
import com.example.fcinema_app.models.ThongBaoModel;

import java.util.ArrayList;
import java.util.List;

public class ThongBaoActivity extends AppCompatActivity {
    ListView listView;

    List<ThongBaoModel> list;

    ThongBaoAdapter thongBaoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao);

        listView = findViewById(R.id.listview2);
        list = new ArrayList<>();

        for (int i = 0; i < 20;i++ ){
            ThongBaoModel model = new ThongBaoModel("Ramba" +i, "20-12-2023- 22h");
            list.add(model);
        }
        thongBaoAdapter = new ThongBaoAdapter(ThongBaoActivity.this,list);
        listView.setAdapter(thongBaoAdapter);



    }

}