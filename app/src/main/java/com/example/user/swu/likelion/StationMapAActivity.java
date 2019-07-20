package com.example.user.swu.likelion; //도착역에서 지하철 버튼 눌렀을 때

import android.content.Intent;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

import java.util.ArrayList;

public class StationMapAActivity extends AppCompatActivity {

    //예진 플로팅 버튼
    private Boolean isFabOpen = false;
    private FloatingActionButton btnfabL;
    public static ArrayList<String> DepartArrive = new ArrayList<>();

    //노선도 이미지
    ImageView imgMap;
    PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_map);

        //Intent i = new Intent();
        Intent intent = getIntent();
        DepartArrive = intent.getStringArrayListExtra("DepartArrive");

        // 예진 플로팅버튼
        btnfabL = findViewById(R.id.btnfabL);


        btnfabL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectActivity.class);
                intent.putStringArrayListExtra("DepartArrive", DepartArrive);
                //i.putExtra("DEPART", four[depart]);
                startActivity(intent); //도착역 설정 화면으로 돌아가기
            }
        });

        //노선도 확대 축소
        imgMap = findViewById(R.id.imgMap);
        mAttacher = new PhotoViewAttacher(imgMap);

    }// end OnCreate();

    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnfabL:
                break;
        }

    } // end onClick();
}