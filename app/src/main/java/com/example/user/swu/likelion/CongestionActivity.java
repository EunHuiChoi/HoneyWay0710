
package com.example.user.swu.likelion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CongestionActivity extends AppCompatActivity {


    ImageButton btnSend;//깃 확인용 아무말 
    //Button btnSend;

    TextView txtvCongest;
    String congestion;
    int timeResult;
    ImageView honjobdo;

    private ArrayList<String> datas = new ArrayList<>();
    private ArrayList<String> stations = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congestion);

        RelativeLayout layout =  findViewById(R.id.layout);

        honjobdo = (ImageView)findViewById(R.id.honjobdo);

        //앞 화면에서 받은 intent
        Intent intent = getIntent();
        //final String a = intent.getStringExtra("stationBean");
        final String formatTime = intent.getStringExtra("FORMATTIME"); //출발시간
        final String depart = intent.getStringExtra("DEPART"); //출발역
        final String arrive = intent.getStringExtra("ARRIVE"); //도착역
        congestion = intent.getStringExtra("CONGESTION"); //출발역 혼잡도
        timeResult = intent.getIntExtra("timeResult",0); //소요시간

        datas  = intent.getStringArrayListExtra("DATA"); // 출발역~도착역 혼잡도
        //Log.d(TAG,"datas===>"+datas.toString());
        stations  = intent.getStringArrayListExtra("STATION"); // 출발역~도착역 역이름

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


        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
                intent.putStringArrayListExtra("DATA",datas);
                intent.putStringArrayListExtra("STATION",stations);
                startActivity(intent);
            }
        });


        if ("매우여유".equals(congestion)){
            honjobdo.setImageResource(R.drawable.nulnul);
            layout.setBackgroundColor(getResources().getColor(R.color.color_nulnul));
        }else if ("여유".equals(congestion)){
            honjobdo.setImageResource(R.drawable.yuyou);
            layout.setBackgroundColor(getResources().getColor(R.color.color_yuyou));
        }else if ("보통".equals(congestion)){
            honjobdo.setImageResource(R.drawable.botong);
            layout.setBackgroundColor(getResources().getColor(R.color.color_botong));
        }else if ("혼잡".equals(congestion)){
            honjobdo.setImageResource(R.drawable.honjob);
            layout.setBackgroundColor(getResources().getColor(R.color.color_honjob));
        }else if ("매우혼잡".equals(congestion)){
            honjobdo.setImageResource(R.drawable.bbakbbak);
            layout.setBackgroundColor(getResources().getColor(R.color.color_bbackbback));
        }else {
            end_time.setVisibility(View.INVISIBLE);
            btnSend.setVisibility(View.INVISIBLE);
            honjobdo.setImageResource(R.drawable.nosubway);//'운행시간 아님'에 대한 이미지 코드 넣기
        }

    }
}