package com.example.user.swu.likelion; //출발역에서 지하철 버튼 눌렀을 때

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

public class StationMapDActivity extends AppCompatActivity {

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
        //intent.putStringArrayListExtra("DepartArrive", DepartArrive);
        DepartArrive = intent.getStringArrayListExtra("DepartArrive");

        // 예진 플로팅버튼
        btnfabL = findViewById(R.id.btnfabL);


        btnfabL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Select2Activity.class);
                intent.putStringArrayListExtra("DepartArrive", DepartArrive);
                startActivity(intent); //출발역 설정 화면으로 돌아가기
            }
        });

        //노선도 확대 축소
        imgMap = findViewById(R.id.imgMap);
        mAttacher = new PhotoViewAttacher(imgMap);
        mAttacher.setScaleType(ImageView.ScaleType.CENTER_CROP); //화면에 꽉차는 옵션
    }// end OnCreate();

    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnfabL:
                break;
        }

    } // end onClick();
}
