
package com.example.user.swu.likelion;

import android.content.Intent;
import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.swu.likelion.adapter.Detail_Adapter;

import java.util.ArrayList;

public class CongestionActivity extends AppCompatActivity {


    ImageButton btnSend;
    //Button btnSend;

    TextView txtvCongest;
    String congestion;
    int timeResult;
    ImageView honjobdo;

    RecyclerView mRecyclerView; //세부혼잡도
    RecyclerView.LayoutManager mLayoutManager; //세부혼잡도

    private ArrayList<String> datas = new ArrayList<>();
    private ArrayList<String> stations = new ArrayList<>();
    private ArrayList<Detail_Info> passing_station = new ArrayList<>(); //세부혼잡도 //넘기는 것

    ImageView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congestion);

        RelativeLayout layout =  findViewById(R.id.layout);

        honjobdo = (ImageView)findViewById(R.id.honjobdo);

        //앞 화면에서 받은 intent
        Intent intent = getIntent();
        //final String a = intent.getStringExtra("stationBean");
        //final int arrive_line_num = intent.getIntExtra("arrive_line_num",0); //도착역 호선 세영
        final String formatTime = intent.getStringExtra("FORMATTIME"); //출발시간
        final String depart = intent.getStringExtra("DEPART"); //출발역
        final String arrive = intent.getStringExtra("ARRIVE"); //도착역
        congestion = intent.getStringExtra("CONGESTION"); //출발역 혼잡도
        //timeResult = intent.getIntExtra("timeResult",0); //소요시간




        datas  = intent.getStringArrayListExtra("DATA"); // 출발역~도착역 혼잡도
        //Log.d(TAG,"datas===>"+datas.toString());
        stations  = intent.getStringArrayListExtra("STATION"); // 출발역~도착역 역이름


        //5개 배열
        structInfo[] infos;

        intent = getIntent();
        Object[] objects = (Object[])intent.getSerializableExtra("INFOS");

        infos = new structInfo[objects.length];
        for(int i=0;i<objects.length;i++){
            infos[i] = (structInfo)objects[(objects.length-1-i)];
        }

        for(int i=0;i<infos.length;i++){
            infos[i].print();
        }

        timeResult = infos[0].totalTime;

        //출발시간 화면에 띄우기
        TextView depart_time = (TextView)findViewById(R.id.depart_time);
        depart_time.setText(formatTime);

        //출발역 화면에 띄우기
        TextView depart_text = (TextView)findViewById(R.id.depart_text);
        depart_text.setText(depart);

        //도착역 화면에 띄우기
        TextView arrive_text = findViewById(R.id.arrive_text);
        arrive_text.setText(arrive);

        //소요시간 화면에 띄우기
        TextView end_time = (TextView)findViewById(R.id.end_time);
        end_time.setText("약 " + timeResult + "분 소요");

        //출발역의 혼잡도 화면에 띄우기
        //txtvCongest = findViewById(R.id.txtvCongest);
        //txtvCongest.setText(congestion);

        Log.d("Wog", "첫번째 혼잡도" + infos[0].congestions[0]);


        if ("매우여유".equals(infos[0].congestions[0])){
            honjobdo.setImageResource(R.drawable.nulnul);
            layout.setBackgroundColor(getResources().getColor(R.color.color_nulnul));
        }else if ("여유".equals(infos[0].congestions[0])){
            honjobdo.setImageResource(R.drawable.yuyou);
            layout.setBackgroundColor(getResources().getColor(R.color.color_yuyou));
        }else if ("보통".equals(infos[0].congestions[0])){
            honjobdo.setImageResource(R.drawable.botong);
            layout.setBackgroundColor(getResources().getColor(R.color.color_botong));
        }else if ("혼잡".equals(infos[0].congestions[0])){
            honjobdo.setImageResource(R.drawable.honjob);
            layout.setBackgroundColor(getResources().getColor(R.color.color_honjob));
        }else if ("매우혼잡".equals(infos[0].congestions[0])){
            honjobdo.setImageResource(R.drawable.bbakbbak);
            layout.setBackgroundColor(getResources().getColor(R.color.color_bbackbback));
        }else {
            end_time.setVisibility(View.INVISIBLE);
            honjobdo.setImageResource(R.drawable.nosubway);//'운행시간 아님'에 대한 이미지 코드 넣기
        }




        //세부 혼잡도
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        passing_station.clear();

        //출발역과 도착역 사이의 역들, 혼잡도 passing_station에 넣기
        for (int i = 0; i< infos[0].congestions.length ; i++) {
            //passing_station.add(new Detail_Info(infos[0].stations[0], infos[0].congestions[i]));
            passing_station.add(new Detail_Info(infos[0].congestions[i],infos[0].stations[i], infos[0].hoseons[i]));
        }


        //출발역과 도착역 사이의 역들 출력하기
        Detail_Adapter myAdapter = new Detail_Adapter(passing_station);
        mRecyclerView.setAdapter(myAdapter);

        /////////////////////이미지
        list = findViewById(R.id.list);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FiveRoadActivity.class);
                startActivity(intent);
            }
        });
    }
}