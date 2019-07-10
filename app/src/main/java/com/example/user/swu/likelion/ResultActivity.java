package com.example.user.swu.likelion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ResultActivity extends AppCompatActivity {
    TextView txtvone;
    TextView txtvtwo;
    TextView txtvthree;

    TextView txtvone2;
    TextView txtvtwo2;
    TextView txtvthree2;

    ArrayList<String> datas = new ArrayList<>();
    ArrayList<String> stations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txtvone = findViewById(R.id.txtvone);
        txtvtwo = findViewById(R.id.txtvtwo);
        txtvthree = findViewById(R.id.txtvthree);
        txtvone2 = findViewById(R.id.txtvone2);
        txtvtwo2 = findViewById(R.id.txtvtwo2);
        txtvthree2 = findViewById(R.id.txtvthree2);

        Intent intent = getIntent();
        datas  = intent.getStringArrayListExtra("DATA");
        stations  = intent.getStringArrayListExtra("STATION");


        txtvone.setText(stations.get(0));
        txtvone2.setText(datas.get(0));
        txtvtwo.setText(stations.get(1));
        txtvtwo2.setText(datas.get(1));
        txtvthree.setText(stations.get(2));
        txtvthree2.setText(datas.get(2));

    }
}
