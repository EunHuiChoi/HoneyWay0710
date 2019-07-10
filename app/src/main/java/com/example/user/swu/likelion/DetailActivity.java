package com.example.user.swu.likelion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    String TAG = this.getClass().getSimpleName();

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<String> datas = new ArrayList<>();
    private ArrayList<String> stations = new ArrayList<>();
    private ArrayList<Detail_Info> passing_station = new ArrayList<>(); //넘기는 것

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //datas = null;
        //stations = null;
        passing_station.clear();
        //passing_station = null;

        Intent intent = getIntent();
        datas  = intent.getStringArrayListExtra("DATA"); // 혼잡도
        //Log.d(TAG,"datas===>"+datas.toString());
        stations  = intent.getStringArrayListExtra("STATION"); //역이름



        //출발역과 도착역 사이의 역들, 혼잡도 passing_station에 넣기
        for (int i = 0; i< datas.size() ; i++) {
            passing_station.add(new Detail_Info(datas.get(i), stations.get(i)));
        }


        //출발역과 도착역 사이의 역들 출력하기
        Detail_Adapter myAdapter = new Detail_Adapter(passing_station);
        mRecyclerView.setAdapter(myAdapter);
    }

    /*public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),RealMainActivity.class);
        startActivity(intent);
        finish();
        //super.onBackPressed();
    }*/
}