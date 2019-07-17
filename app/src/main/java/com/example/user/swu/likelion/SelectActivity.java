package com.example.user.swu.likelion;
//도착역 설정 페이지
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.user.swu.likelion.data.Data;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SelectActivity extends AppCompatActivity implements ModifyFragment.ModifytListener {

    Button btn_next;
    ImageButton btnModify;

    public static ArrayList<String> DepartArrive = new ArrayList<>();


    //[하은 시간]
    // 현재시간을 msec 으로 구한다.
    long now = System.currentTimeMillis();
    // 현재시간을 date 변수에 저장한다.
    Date date = new Date(now);
    // formatDay 변수에 요일 값 저장 // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
    SimpleDateFormat sdfDay = new SimpleDateFormat("E");
    String formatDay = sdfDay.format(date);

    // formatHour 변수에 시간 값 저장
    SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
    String formatHour = sdfHour.format(date);

    // formatTime 변수에 시간 값 저장
    SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
    String formatTime = sdfTime.format(date);

    //[태영 DB]
    String TAG = this.getClass().getSimpleName();
    FirebaseDatabase database;

    String[] four = {"당고개","상계","노원","창동","쌍문","수유","미아","미아사거리","길음",
            "성신여대입구","한성대입구", "혜화","동대문","동대문역사문화공원","충무로","명동",
            "회현","서울역","숙대입구","삼각지","신용산","이촌","동작","총신대입구(이수)","사당","남태령"}; //4호선 노선도 배열
    ArrayList<String> datas = new ArrayList<>();
    ArrayList<String> stations = new ArrayList<>();
    int depart = 0; //출발역 초기화
    int arrive = 0; //도착역 초기화
    String day; //요일 초기화
    String time; //시간 초기화
    String direction; //방면(상행하행) 초기화

    //[하은 소요시간 계산]
    int[] totalTime = {2, 2, 2, 2, 3, 2, 2, 2, 3, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 1, 2, 4, 3, 2, 1,0};
    //당고개~노원으로 시작, 사당~남태령 끝 갯수를 맞추기위해 마지막 0하나 추가
    //시작역 + 끝역-1로 더해야함
    int timeResult = 0;


    ProgressDialog dialog;

    String congestion = null;

    //예진 리스트 변수
    private ListView lstStation1, lstStation2, lstStation3, lstStation4,
            lstStation5, lstStation6, lstStation7, lstStation8;
    private Button btnS1, btnS2, btnS3, btnS4, btnS5, btnS6, btnS7, btnS8;

    //예진 플로팅 버튼
    private Boolean isFabOpen = false;
    private FloatingActionButton btnfabS;


    ///////////////////////////////////////////////태영 ModifyFragment
    String hour;
    String minute;
    TextView txtvTime;

    public void onFInishModify(String h, String m, String d){
        hour = h;
        minute = m;
        formatTime = hour+":"+minute;

        if(d.equals("토요일")){
            day="sat";
        }else if(d.equals("공휴일")){
            day="sun";
        }else{
            day="week";
        }


        txtvTime.setText(day+" "+formatTime);

        Log.d(TAG,formatTime+","+day);
        time = "time"+hour;
    }

    ////////////////////////////////태영 도착역 txtv
    public static TextView txtvArrive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        Intent intent = getIntent();
        DepartArrive = intent.getStringArrayListExtra("DepartArrive");

        TextView departure_stn;
        departure_stn = (TextView)findViewById(R.id.departure_stn);
        departure_stn.setText(DepartArrive.get(0));

        txtvArrive = findViewById(R.id.txtvArrive);
        txtvArrive.setVisibility(View.INVISIBLE);

        if(DepartArrive.size()>=2){
            txtvArrive.setText(DepartArrive.get(1));
        }

        /* 메인에서 출발역 받아서 넣는 코드
        TextView departure_stn;
        departure_stn = (TextView)findViewById(R.id.departure_stn);
        Intent intent = getIntent();

        String closest = intent.getExtras().getString("departure_stn");
        DepartArrive.add(closest); //출발역 임의로 넣음(추후 수정)
        departure_stn.setText(closest);
        */

        btn_next = (Button) findViewById(R.id.btn_next); //선택완료 버튼 (디자인 수정할 예정)

        //예진 리스트
        lstStation1 = findViewById(R.id.lstStation1);
        lstStation2 = findViewById(R.id.lstStation2);
        lstStation3 = findViewById(R.id.lstStation3);
        lstStation4 = findViewById(R.id.lstStation4);
        lstStation5 = findViewById(R.id.lstStation5);
        lstStation6 = findViewById(R.id.lstStation6);
        lstStation7 = findViewById(R.id.lstStation7);
        lstStation8 = findViewById(R.id.lstStation8);
        btnS1 = findViewById(R.id.btnS1);
        btnS2 = findViewById(R.id.btnS2);
        btnS3 = findViewById(R.id.btnS3);
        btnS4 = findViewById(R.id.btnS4);
        btnS5 = findViewById(R.id.btnS5);
        btnS6 = findViewById(R.id.btnS6);
        btnS7 = findViewById(R.id.btnS7);
        btnS8 = findViewById(R.id.btnS8);
        btnModify = findViewById(R.id.btnModify);

        //객체 정보(1호선)
        //객체 정보(1호선)
        StationBean s1_1 = new StationBean();
        s1_1.setImgSeoul(R.drawable.img_seoul);
        s1_1.setTxtStationName("서울역");
        s1_1.setImgStationNum(R.drawable.img_station1);
        s1_1.setImgStationNum2(R.drawable.img_station4);

        StationBean s1_2 = new StationBean();
        s1_2.setImgSeoul(R.drawable.img_seoul);
        s1_2.setTxtStationName("시청");
        s1_2.setImgStationNum(R.drawable.img_station1);
        s1_2.setImgStationNum2(R.drawable.img_station2);

        StationBean s1_3 = new StationBean();
        s1_3.setImgSeoul(R.drawable.img_seoul);
        s1_3.setTxtStationName("종각");
        s1_3.setImgStationNum(R.drawable.img_station1);

        StationBean s1_4 = new StationBean();
        s1_4.setImgSeoul(R.drawable.img_seoul);
        s1_4.setTxtStationName("종로3가");
        s1_4.setImgStationNum(R.drawable.img_station1);
        s1_4.setImgStationNum2(R.drawable.img_station3);
        s1_4.setImgStationNum3(R.drawable.img_station5);

        StationBean s1_5 = new StationBean();
        s1_5.setImgSeoul(R.drawable.img_seoul);
        s1_5.setTxtStationName("종로5가");
        s1_5.setImgStationNum(R.drawable.img_station1);

        StationBean s1_6 = new StationBean();
        s1_6.setImgSeoul(R.drawable.img_seoul);
        s1_6.setTxtStationName("동대문");
        s1_6.setImgStationNum(R.drawable.img_station1);
        s1_6.setImgStationNum2(R.drawable.img_station4);

        StationBean s1_7 = new StationBean();
        s1_7.setImgSeoul(R.drawable.img_seoul);
        s1_7.setTxtStationName("신설동");
        s1_7.setImgStationNum(R.drawable.img_station1);
        s1_7.setImgStationNum2(R.drawable.img_station2);

        StationBean s1_8 = new StationBean();
        s1_8.setImgSeoul(R.drawable.img_seoul);
        s1_8.setTxtStationName("제기동");
        s1_8.setImgStationNum(R.drawable.img_station1);

        StationBean s1_9 = new StationBean();
        s1_9.setImgSeoul(R.drawable.img_seoul);
        s1_9.setTxtStationName("청량리");
        s1_9.setImgStationNum(R.drawable.img_station1);

        StationBean s1_10 = new StationBean();
        s1_10.setImgSeoul(R.drawable.img_seoul);
        s1_10.setTxtStationName("동묘앞");
        s1_10.setImgStationNum(R.drawable.img_station1);
        s1_10.setImgStationNum2(R.drawable.img_station6);


        //객체 정보(2호선)
        StationBean s2_1 = new StationBean();
        s2_1.setImgSeoul(R.drawable.img_seoul);
        s2_1.setTxtStationName("시청");
        s2_1.setImgStationNum(R.drawable.img_station1);
        s2_1.setImgStationNum2(R.drawable.img_station2);

        StationBean s2_2 = new StationBean();
        s2_2.setImgSeoul(R.drawable.img_seoul);
        s2_2.setTxtStationName("을지로입구");
        s2_2.setImgStationNum(R.drawable.img_station2);

        StationBean s2_3 = new StationBean();
        s2_3.setImgSeoul(R.drawable.img_seoul);
        s2_3.setTxtStationName("을지로3가");
        s2_3.setImgStationNum(R.drawable.img_station2);
        s2_3.setImgStationNum2(R.drawable.img_station3);

        StationBean s2_4 = new StationBean();
        s2_4.setImgSeoul(R.drawable.img_seoul);
        s2_4.setTxtStationName("을지로4가");
        s2_4.setImgStationNum(R.drawable.img_station2);
        s2_4.setImgStationNum2(R.drawable.img_station5);

        StationBean s2_5 = new StationBean();
        s2_5.setImgSeoul(R.drawable.img_seoul);
        s2_5.setTxtStationName("동대문역사문화공원");
        s2_5.setImgStationNum(R.drawable.img_station2);
        s2_5.setImgStationNum2(R.drawable.img_station4);
        s2_5.setImgStationNum3(R.drawable.img_station5);

        StationBean s2_6 = new StationBean();
        s2_6.setImgSeoul(R.drawable.img_seoul);
        s2_6.setTxtStationName("신당");
        s2_6.setImgStationNum(R.drawable.img_station2);
        s2_6.setImgStationNum2(R.drawable.img_station6);

        StationBean s2_7 = new StationBean();
        s2_7.setImgSeoul(R.drawable.img_seoul);
        s2_7.setTxtStationName("상왕십리");
        s2_7.setImgStationNum(R.drawable.img_station2);

        StationBean s2_8 = new StationBean();
        s2_8.setImgSeoul(R.drawable.img_seoul);
        s2_8.setTxtStationName("왕십리");
        s2_8.setImgStationNum(R.drawable.img_station2);
        s2_8.setImgStationNum2(R.drawable.img_station5);

        StationBean s2_9 = new StationBean();
        s2_9.setImgSeoul(R.drawable.img_seoul);
        s2_9.setTxtStationName("한양대");
        s2_9.setImgStationNum(R.drawable.img_station2);

        StationBean s2_10 = new StationBean();
        s2_10.setImgSeoul(R.drawable.img_seoul);
        s2_10.setTxtStationName("뚝섬");
        s2_10.setImgStationNum(R.drawable.img_station2);

        StationBean s2_11 = new StationBean();
        s2_11.setImgSeoul(R.drawable.img_seoul);
        s2_11.setTxtStationName("성수");
        s2_11.setImgStationNum(R.drawable.img_station2);

        StationBean s2_12 = new StationBean();
        s2_12.setImgSeoul(R.drawable.img_seoul);
        s2_12.setTxtStationName("건대입구");
        s2_12.setImgStationNum(R.drawable.img_station2);
        s2_12.setImgStationNum2(R.drawable.img_station7);

        StationBean s2_13 = new StationBean();
        s2_13.setImgSeoul(R.drawable.img_seoul);
        s2_13.setTxtStationName("구의");
        s2_13.setImgStationNum(R.drawable.img_station2);

        StationBean s2_14 = new StationBean();
        s2_14.setImgSeoul(R.drawable.img_seoul);
        s2_14.setTxtStationName("강변");
        s2_14.setImgStationNum(R.drawable.img_station2);

        StationBean s2_15 = new StationBean();
        s2_15.setImgSeoul(R.drawable.img_seoul);
        s2_15.setTxtStationName("잠실나루");
        s2_15.setImgStationNum(R.drawable.img_station2);

        StationBean s2_16 = new StationBean();
        s2_16.setImgSeoul(R.drawable.img_seoul);
        s2_16.setTxtStationName("잠실");
        s2_16.setImgStationNum(R.drawable.img_station2);
        s2_16.setImgStationNum2(R.drawable.img_station8);

        StationBean s2_17 = new StationBean();
        s2_17.setImgSeoul(R.drawable.img_seoul);
        s2_17.setTxtStationName("잠실새내");
        s2_17.setImgStationNum(R.drawable.img_station2);

        StationBean s2_18 = new StationBean();
        s2_18.setImgSeoul(R.drawable.img_seoul);
        s2_18.setTxtStationName("종합운동장");
        s2_18.setImgStationNum(R.drawable.img_station2);
        s2_18.setImgStationNum2(R.drawable.img_station9);

        StationBean s2_19 = new StationBean();
        s2_19.setImgSeoul(R.drawable.img_seoul);
        s2_19.setTxtStationName("삼성");
        s2_19.setImgStationNum(R.drawable.img_station2);

        StationBean s2_20 = new StationBean();
        s2_20.setImgSeoul(R.drawable.img_seoul);
        s2_20.setTxtStationName("선릉");
        s2_20.setImgStationNum(R.drawable.img_station2);

        StationBean s2_21 = new StationBean();
        s2_21.setImgSeoul(R.drawable.img_seoul);
        s2_21.setTxtStationName("역삼");
        s2_21.setImgStationNum(R.drawable.img_station2);

        StationBean s2_22 = new StationBean();
        s2_22.setImgSeoul(R.drawable.img_seoul);
        s2_22.setTxtStationName("강남");
        s2_22.setImgStationNum(R.drawable.img_station2);

        StationBean s2_23 = new StationBean();
        s2_23.setImgSeoul(R.drawable.img_seoul);
        s2_23.setTxtStationName("교대");
        s2_23.setImgStationNum(R.drawable.img_station2);
        s2_23.setImgStationNum2(R.drawable.img_station3);

        StationBean s2_24 = new StationBean();
        s2_24.setImgSeoul(R.drawable.img_seoul);
        s2_24.setTxtStationName("서초");
        s2_24.setImgStationNum(R.drawable.img_station2);

        StationBean s2_25 = new StationBean();
        s2_25.setImgSeoul(R.drawable.img_seoul);
        s2_25.setTxtStationName("방배");
        s2_25.setImgStationNum(R.drawable.img_station2);

        StationBean s2_26 = new StationBean();
        s2_26.setImgSeoul(R.drawable.img_seoul);
        s2_26.setTxtStationName("사당");
        s2_26.setImgStationNum(R.drawable.img_station2);
        s2_26.setImgStationNum2(R.drawable.img_station4);

        StationBean s2_27 = new StationBean();
        s2_27.setImgSeoul(R.drawable.img_seoul);
        s2_27.setTxtStationName("낙성대");
        s2_27.setImgStationNum(R.drawable.img_station2);

        StationBean s2_28 = new StationBean();
        s2_28.setImgSeoul(R.drawable.img_seoul);
        s2_28.setTxtStationName("서울대입구");
        s2_28.setImgStationNum(R.drawable.img_station2);

        StationBean s2_29 = new StationBean();
        s2_29.setImgSeoul(R.drawable.img_seoul);
        s2_29.setTxtStationName("봉천");
        s2_29.setImgStationNum(R.drawable.img_station2);

        StationBean s2_30 = new StationBean();
        s2_30.setImgSeoul(R.drawable.img_seoul);
        s2_30.setTxtStationName("신림");
        s2_30.setImgStationNum(R.drawable.img_station2);

        StationBean s2_31 = new StationBean();
        s2_31.setImgSeoul(R.drawable.img_seoul);
        s2_31.setTxtStationName("신대방");
        s2_31.setImgStationNum(R.drawable.img_station2);

        StationBean s2_32 = new StationBean();
        s2_32.setImgSeoul(R.drawable.img_seoul);
        s2_32.setTxtStationName("구로디지털단지");
        s2_32.setImgStationNum(R.drawable.img_station2);

        StationBean s2_33 = new StationBean();
        s2_33.setImgSeoul(R.drawable.img_seoul);
        s2_33.setTxtStationName("대림");
        s2_33.setImgStationNum(R.drawable.img_station2);
        s2_33.setImgStationNum2(R.drawable.img_station7);

        StationBean s2_34 = new StationBean();
        s2_34.setImgSeoul(R.drawable.img_seoul);
        s2_34.setTxtStationName("신도림");
        s2_34.setImgStationNum(R.drawable.img_station1);
        s2_34.setImgStationNum2(R.drawable.img_station2);

        StationBean s2_35 = new StationBean();
        s2_35.setImgSeoul(R.drawable.img_seoul);
        s2_35.setTxtStationName("문래");
        s2_35.setImgStationNum(R.drawable.img_station2);

        StationBean s2_36 = new StationBean();
        s2_36.setImgSeoul(R.drawable.img_seoul);
        s2_36.setTxtStationName("영등포구청");
        s2_36.setImgStationNum(R.drawable.img_station2);
        s2_36.setImgStationNum2(R.drawable.img_station5);

        StationBean s2_37 = new StationBean();
        s2_37.setImgSeoul(R.drawable.img_seoul);
        s2_37.setTxtStationName("당산");
        s2_37.setImgStationNum(R.drawable.img_station2);
        s2_37.setImgStationNum2(R.drawable.img_station9);

        StationBean s2_38 = new StationBean();
        s2_38.setImgSeoul(R.drawable.img_seoul);
        s2_38.setTxtStationName("합정");
        s2_38.setImgStationNum(R.drawable.img_station2);
        s2_38.setImgStationNum2(R.drawable.img_station6);

        StationBean s2_39 = new StationBean();
        s2_39.setImgSeoul(R.drawable.img_seoul);
        s2_39.setTxtStationName("홍대입구");
        s2_39.setImgStationNum(R.drawable.img_station2);

        StationBean s2_40 = new StationBean();
        s2_40.setImgSeoul(R.drawable.img_seoul);
        s2_40.setTxtStationName("신촌");
        s2_40.setImgStationNum(R.drawable.img_station2);

        StationBean s2_41 = new StationBean();
        s2_41.setImgSeoul(R.drawable.img_seoul);
        s2_41.setTxtStationName("이대");
        s2_41.setImgStationNum(R.drawable.img_station2);

        StationBean s2_42 = new StationBean();
        s2_42.setImgSeoul(R.drawable.img_seoul);
        s2_42.setTxtStationName("아현");
        s2_42.setImgStationNum(R.drawable.img_station2);

        StationBean s2_43 = new StationBean();
        s2_43.setImgSeoul(R.drawable.img_seoul);
        s2_43.setTxtStationName("충정로");
        s2_43.setImgStationNum(R.drawable.img_station2);
        s2_43.setImgStationNum2(R.drawable.img_station5);

        StationBean s2_44 = new StationBean();
        s2_44.setImgSeoul(R.drawable.img_seoul);
        s2_44.setTxtStationName("용답");
        s2_44.setImgStationNum(R.drawable.img_station2);

        StationBean s2_45 = new StationBean();
        s2_45.setImgSeoul(R.drawable.img_seoul);
        s2_45.setTxtStationName("신답");
        s2_45.setImgStationNum(R.drawable.img_station2);

        StationBean s2_46 = new StationBean();
        s2_46.setImgSeoul(R.drawable.img_seoul);
        s2_46.setTxtStationName("신설동");
        s2_46.setImgStationNum(R.drawable.img_station1);
        s2_46.setImgStationNum2(R.drawable.img_station2);

        StationBean s2_47 = new StationBean();
        s2_47.setImgSeoul(R.drawable.img_seoul);
        s2_47.setTxtStationName("용두");
        s2_47.setImgStationNum(R.drawable.img_station2);

        StationBean s2_48 = new StationBean();
        s2_48.setImgSeoul(R.drawable.img_seoul);
        s2_48.setTxtStationName("도림천");
        s2_48.setImgStationNum(R.drawable.img_station2);

        StationBean s2_49 = new StationBean();
        s2_49.setImgSeoul(R.drawable.img_seoul);
        s2_49.setTxtStationName("양천구청");
        s2_49.setImgStationNum(R.drawable.img_station2);

        StationBean s2_50 = new StationBean();
        s2_50.setImgSeoul(R.drawable.img_seoul);
        s2_50.setTxtStationName("신정네거리");
        s2_50.setImgStationNum(R.drawable.img_station2);

        StationBean s2_51 = new StationBean();
        s2_51.setImgSeoul(R.drawable.img_seoul);
        s2_51.setTxtStationName("까치산");
        s2_51.setImgStationNum(R.drawable.img_station2);
        s2_51.setImgStationNum2(R.drawable.img_station5);

        //객체 정보(3호선)
        StationBean s3_1 = new StationBean();
        s3_1.setImgSeoul(R.drawable.img_seoul);
        s3_1.setTxtStationName("지축");
        s3_1.setImgStationNum(R.drawable.img_station3);

        StationBean s3_2 = new StationBean();
        s3_2.setImgSeoul(R.drawable.img_seoul);
        s3_2.setTxtStationName("구파발");
        s3_2.setImgStationNum(R.drawable.img_station3);

        StationBean s3_3 = new StationBean();
        s3_3.setImgSeoul(R.drawable.img_seoul);
        s3_3.setTxtStationName("연신내");
        s3_3.setImgStationNum(R.drawable.img_station3);
        s3_3.setImgStationNum2(R.drawable.img_station6);

        StationBean s3_4 = new StationBean();
        s3_4.setImgSeoul(R.drawable.img_seoul);
        s3_4.setTxtStationName("불광");
        s3_4.setImgStationNum(R.drawable.img_station3);
        s3_4.setImgStationNum2(R.drawable.img_station6);

        StationBean s3_5 = new StationBean();
        s3_5.setImgSeoul(R.drawable.img_seoul);
        s3_5.setTxtStationName("녹번");
        s3_5.setImgStationNum(R.drawable.img_station3);

        StationBean s3_6 = new StationBean();
        s3_6.setImgSeoul(R.drawable.img_seoul);
        s3_6.setTxtStationName("홍제");
        s3_6.setImgStationNum(R.drawable.img_station3);

        StationBean s3_7 = new StationBean();
        s3_7.setImgSeoul(R.drawable.img_seoul);
        s3_7.setTxtStationName("무악재");
        s3_7.setImgStationNum(R.drawable.img_station3);

        StationBean s3_8 = new StationBean();
        s3_8.setImgSeoul(R.drawable.img_seoul);
        s3_8.setTxtStationName("독립문");
        s3_8.setImgStationNum(R.drawable.img_station3);

        StationBean s3_9 = new StationBean();
        s3_9.setImgSeoul(R.drawable.img_seoul);
        s3_9.setTxtStationName("경복궁");
        s3_9.setImgStationNum(R.drawable.img_station3);

        StationBean s3_10 = new StationBean();
        s3_10.setImgSeoul(R.drawable.img_seoul);
        s3_10.setTxtStationName("안국");
        s3_10.setImgStationNum(R.drawable.img_station3);

        StationBean s3_11 = new StationBean();
        s3_11.setImgSeoul(R.drawable.img_seoul);
        s3_11.setTxtStationName("종로3가");
        s3_11.setImgStationNum(R.drawable.img_station1);
        s3_11.setImgStationNum2(R.drawable.img_station3);
        s3_11.setImgStationNum3(R.drawable.img_station5);

        StationBean s3_12 = new StationBean();
        s3_12.setImgSeoul(R.drawable.img_seoul);
        s3_12.setTxtStationName("을지로3가");
        s3_12.setImgStationNum(R.drawable.img_station2);
        s3_12.setImgStationNum2(R.drawable.img_station3);

        StationBean s3_13 = new StationBean();
        s3_13.setImgSeoul(R.drawable.img_seoul);
        s3_13.setTxtStationName("충무로");
        s3_13.setImgStationNum(R.drawable.img_station3);
        s3_13.setImgStationNum2(R.drawable.img_station4);

        StationBean s3_14 = new StationBean();
        s3_14.setImgSeoul(R.drawable.img_seoul);
        s3_14.setTxtStationName("동대입구");
        s3_14.setImgStationNum(R.drawable.img_station3);

        StationBean s3_15 = new StationBean();
        s3_15.setImgSeoul(R.drawable.img_seoul);
        s3_15.setTxtStationName("약수");
        s3_15.setImgStationNum(R.drawable.img_station3);
        s3_15.setImgStationNum2(R.drawable.img_station6);

        StationBean s3_16 = new StationBean();
        s3_16.setImgSeoul(R.drawable.img_seoul);
        s3_16.setTxtStationName("금호");
        s3_16.setImgStationNum(R.drawable.img_station3);

        StationBean s3_17 = new StationBean();
        s3_17.setImgSeoul(R.drawable.img_seoul);
        s3_17.setTxtStationName("옥수");
        s3_17.setImgStationNum(R.drawable.img_station3);

        StationBean s3_18 = new StationBean();
        s3_18.setImgSeoul(R.drawable.img_seoul);
        s3_18.setTxtStationName("압구정");
        s3_18.setImgStationNum(R.drawable.img_station3);

        StationBean s3_19 = new StationBean();
        s3_19.setImgSeoul(R.drawable.img_seoul);
        s3_19.setTxtStationName("신사");
        s3_19.setImgStationNum(R.drawable.img_station3);

        StationBean s3_20 = new StationBean();
        s3_20.setImgSeoul(R.drawable.img_seoul);
        s3_20.setTxtStationName("잠원");
        s3_20.setImgStationNum(R.drawable.img_station3);

        StationBean s3_21 = new StationBean();
        s3_21.setImgSeoul(R.drawable.img_seoul);
        s3_21.setTxtStationName("고속터미널");
        s3_21.setImgStationNum(R.drawable.img_station3);
        s3_21.setImgStationNum2(R.drawable.img_station7);
        s3_21.setImgStationNum3(R.drawable.img_station9);

        StationBean s3_22 = new StationBean();
        s3_22.setImgSeoul(R.drawable.img_seoul);
        s3_22.setTxtStationName("교대");
        s3_22.setImgStationNum(R.drawable.img_station2);
        s3_22.setImgStationNum2(R.drawable.img_station3);

        StationBean s3_23 = new StationBean();
        s3_23.setImgSeoul(R.drawable.img_seoul);
        s3_23.setTxtStationName("남부터미널");
        s3_23.setImgStationNum(R.drawable.img_station3);

        StationBean s3_24 = new StationBean();
        s3_24.setImgSeoul(R.drawable.img_seoul);
        s3_24.setTxtStationName("양재");
        s3_24.setImgStationNum(R.drawable.img_station3);

        StationBean s3_25 = new StationBean();
        s3_25.setImgSeoul(R.drawable.img_seoul);
        s3_25.setTxtStationName("매봉");
        s3_25.setImgStationNum(R.drawable.img_station3);

        StationBean s3_26 = new StationBean();
        s3_26.setImgSeoul(R.drawable.img_seoul);
        s3_26.setTxtStationName("도곡");
        s3_26.setImgStationNum(R.drawable.img_station3);

        StationBean s3_27 = new StationBean();
        s3_27.setImgSeoul(R.drawable.img_seoul);
        s3_27.setTxtStationName("대치");
        s3_27.setImgStationNum(R.drawable.img_station3);

        StationBean s3_28 = new StationBean();
        s3_28.setImgSeoul(R.drawable.img_seoul);
        s3_28.setTxtStationName("학여울");
        s3_28.setImgStationNum(R.drawable.img_station3);

        StationBean s3_29 = new StationBean();
        s3_29.setImgSeoul(R.drawable.img_seoul);
        s3_29.setTxtStationName("대청");
        s3_29.setImgStationNum(R.drawable.img_station3);

        StationBean s3_30 = new StationBean();
        s3_30.setImgSeoul(R.drawable.img_seoul);
        s3_30.setTxtStationName("일원");
        s3_30.setImgStationNum(R.drawable.img_station3);

        StationBean s3_31 = new StationBean();
        s3_31.setImgSeoul(R.drawable.img_seoul);
        s3_31.setTxtStationName("수서");
        s3_31.setImgStationNum(R.drawable.img_station3);

        StationBean s3_32 = new StationBean();
        s3_32.setImgSeoul(R.drawable.img_seoul);
        s3_32.setTxtStationName("가락시장");
        s3_32.setImgStationNum(R.drawable.img_station3);
        s3_32.setImgStationNum2(R.drawable.img_station8);

        StationBean s3_33 = new StationBean();
        s3_33.setImgSeoul(R.drawable.img_seoul);
        s3_33.setTxtStationName("경찰병원");
        s3_33.setImgStationNum(R.drawable.img_station3);

        StationBean s3_34 = new StationBean();
        s3_34.setImgSeoul(R.drawable.img_seoul);
        s3_34.setTxtStationName("오금");
        s3_34.setImgStationNum(R.drawable.img_station3);
        s3_34.setImgStationNum2(R.drawable.img_station5);

        //객체 정보(4호선)
        StationBean s4_1 = new StationBean();
        s4_1.setImgSeoul(R.drawable.img_seoul);
        s4_1.setTxtStationName("당고개");
        s4_1.setImgStationNum(R.drawable.img_station4);

        StationBean s4_2 = new StationBean();
        s4_2.setImgSeoul(R.drawable.img_seoul);
        s4_2.setTxtStationName("상계");
        s4_2.setImgStationNum(R.drawable.img_station4);

        StationBean s4_3 = new StationBean();
        s4_3.setImgSeoul(R.drawable.img_seoul);
        s4_3.setTxtStationName("노원");
        s4_3.setImgStationNum(R.drawable.img_station4);
        s4_3.setImgStationNum2(R.drawable.img_station7);

        StationBean s4_4 = new StationBean();
        s4_4.setImgSeoul(R.drawable.img_seoul);
        s4_4.setTxtStationName("창동");
        s4_4.setImgStationNum(R.drawable.img_station1);
        s4_4.setImgStationNum2(R.drawable.img_station4);

        StationBean s4_5 = new StationBean();
        s4_5.setImgSeoul(R.drawable.img_seoul);
        s4_5.setTxtStationName("쌍문");
        s4_5.setImgStationNum(R.drawable.img_station4);

        StationBean s4_6 = new StationBean();
        s4_6.setImgSeoul(R.drawable.img_seoul);
        s4_6.setTxtStationName("수유");
        s4_6.setImgStationNum(R.drawable.img_station4);

        StationBean s4_7 = new StationBean();
        s4_7.setImgSeoul(R.drawable.img_seoul);
        s4_7.setTxtStationName("미아");
        s4_7.setImgStationNum(R.drawable.img_station4);

        StationBean s4_8 = new StationBean();
        s4_8.setImgSeoul(R.drawable.img_seoul);
        s4_8.setTxtStationName("미아사거리");
        s4_8.setImgStationNum(R.drawable.img_station4);

        StationBean s4_9 = new StationBean();
        s4_9.setImgSeoul(R.drawable.img_seoul);
        s4_9.setTxtStationName("길음");
        s4_9.setImgStationNum(R.drawable.img_station4);

        StationBean s4_10 = new StationBean();
        s4_10.setImgSeoul(R.drawable.img_seoul);
        s4_10.setTxtStationName("성신여대입구");
        s4_10.setImgStationNum(R.drawable.img_station4);

        StationBean s4_11 = new StationBean();
        s4_11.setImgSeoul(R.drawable.img_seoul);
        s4_11.setTxtStationName("한성대입구");
        s4_11.setImgStationNum(R.drawable.img_station4);

        StationBean s4_12 = new StationBean();
        s4_12.setImgSeoul(R.drawable.img_seoul);
        s4_12.setTxtStationName("혜화");
        s4_12.setImgStationNum(R.drawable.img_station4);

        StationBean s4_13 = new StationBean();
        s4_13.setImgSeoul(R.drawable.img_seoul);
        s4_13.setTxtStationName("동대문");
        s4_13.setImgStationNum(R.drawable.img_station1);
        s4_13.setImgStationNum2(R.drawable.img_station4);

        StationBean s4_14 = new StationBean();
        s4_14.setImgSeoul(R.drawable.img_seoul);
        s4_14.setTxtStationName("동대문역사문화공원");
        s4_14.setImgStationNum(R.drawable.img_station2);
        s4_14.setImgStationNum2(R.drawable.img_station4);
        s4_14.setImgStationNum3(R.drawable.img_station5);

        StationBean s4_15 = new StationBean();
        s4_15.setImgSeoul(R.drawable.img_seoul);
        s4_15.setTxtStationName("충무로");
        s4_15.setImgStationNum(R.drawable.img_station3);
        s4_15.setImgStationNum2(R.drawable.img_station4);

        StationBean s4_16 = new StationBean();
        s4_16.setImgSeoul(R.drawable.img_seoul);
        s4_16.setTxtStationName("명동");
        s4_16.setImgStationNum(R.drawable.img_station4);

        StationBean s4_17 = new StationBean();
        s4_17.setImgSeoul(R.drawable.img_seoul);
        s4_17.setTxtStationName("회현");
        s4_17.setImgStationNum(R.drawable.img_station4);

        StationBean s4_18 = new StationBean();
        s4_18.setImgSeoul(R.drawable.img_seoul);
        s4_18.setTxtStationName("서울역");
        s4_18.setImgStationNum(R.drawable.img_station1);
        s4_18.setImgStationNum2(R.drawable.img_station4);

        StationBean s4_19 = new StationBean();
        s4_19.setImgSeoul(R.drawable.img_seoul);
        s4_19.setTxtStationName("숙대입구");
        s4_19.setImgStationNum(R.drawable.img_station4);

        StationBean s4_20 = new StationBean();
        s4_20.setImgSeoul(R.drawable.img_seoul);
        s4_20.setTxtStationName("삼각지");
        s4_20.setImgStationNum(R.drawable.img_station4);
        s4_20.setImgStationNum2(R.drawable.img_station6);

        StationBean s4_21 = new StationBean();
        s4_21.setImgSeoul(R.drawable.img_seoul);
        s4_21.setTxtStationName("신용산");
        s4_21.setImgStationNum(R.drawable.img_station4);

        StationBean s4_22 = new StationBean();
        s4_22.setImgSeoul(R.drawable.img_seoul);
        s4_22.setTxtStationName("이촌");
        s4_22.setImgStationNum(R.drawable.img_station4);

        StationBean s4_23 = new StationBean();
        s4_23.setImgSeoul(R.drawable.img_seoul);
        s4_23.setTxtStationName("동작");
        s4_23.setImgStationNum(R.drawable.img_station4);
        s4_23.setImgStationNum2(R.drawable.img_station9);

        StationBean s4_24 = new StationBean();
        s4_24.setImgSeoul(R.drawable.img_seoul);
        s4_24.setTxtStationName("총신대입구(이수)");
        s4_24.setImgStationNum(R.drawable.img_station4);
        s4_24.setImgStationNum2(R.drawable.img_station7);

        StationBean s4_25 = new StationBean();
        s4_25.setImgSeoul(R.drawable.img_seoul);
        s4_25.setTxtStationName("사당");
        s4_25.setImgStationNum(R.drawable.img_station2);
        s4_25.setImgStationNum2(R.drawable.img_station4);

        StationBean s4_26 = new StationBean();
        s4_26.setImgSeoul(R.drawable.img_seoul);
        s4_26.setTxtStationName("남태령");
        s4_26.setImgStationNum(R.drawable.img_station4);

        //객체 정보(5호선)
        StationBean s5_1 = new StationBean();
        s5_1.setImgSeoul(R.drawable.img_seoul);
        s5_1.setTxtStationName("방화");
        s5_1.setImgStationNum(R.drawable.img_station5);

        StationBean s5_2 = new StationBean();
        s5_2.setImgSeoul(R.drawable.img_seoul);
        s5_2.setTxtStationName("개화산");
        s5_2.setImgStationNum(R.drawable.img_station5);

        StationBean s5_3 = new StationBean();
        s5_3.setImgSeoul(R.drawable.img_seoul);
        s5_3.setTxtStationName("김포공항");
        s5_3.setImgStationNum(R.drawable.img_station5);
        s5_3.setImgStationNum2(R.drawable.img_station9);

        StationBean s5_4 = new StationBean();
        s5_4.setImgSeoul(R.drawable.img_seoul);
        s5_4.setTxtStationName("송정");
        s5_4.setImgStationNum(R.drawable.img_station5);

        StationBean s5_5 = new StationBean();
        s5_5.setImgSeoul(R.drawable.img_seoul);
        s5_5.setTxtStationName("마곡");
        s5_5.setImgStationNum(R.drawable.img_station5);

        StationBean s5_6 = new StationBean();
        s5_6.setImgSeoul(R.drawable.img_seoul);
        s5_6.setTxtStationName("발산");
        s5_6.setImgStationNum(R.drawable.img_station5);

        StationBean s5_7 = new StationBean();
        s5_7.setImgSeoul(R.drawable.img_seoul);
        s5_7.setTxtStationName("우장산");
        s5_7.setImgStationNum(R.drawable.img_station5);

        StationBean s5_8 = new StationBean();
        s5_8.setImgSeoul(R.drawable.img_seoul);
        s5_8.setTxtStationName("화곡");
        s5_8.setImgStationNum(R.drawable.img_station5);

        StationBean s5_9 = new StationBean();
        s5_9.setImgSeoul(R.drawable.img_seoul);
        s5_9.setTxtStationName("까치산");
        s5_9.setImgStationNum(R.drawable.img_station2);
        s5_9.setImgStationNum2(R.drawable.img_station5);

        StationBean s5_10 = new StationBean();
        s5_10.setImgSeoul(R.drawable.img_seoul);
        s5_10.setTxtStationName("신정");
        s5_10.setImgStationNum(R.drawable.img_station5);

        StationBean s5_11 = new StationBean();
        s5_11.setImgSeoul(R.drawable.img_seoul);
        s5_11.setTxtStationName("목동");
        s5_11.setImgStationNum(R.drawable.img_station5);

        StationBean s5_12 = new StationBean();
        s5_12.setImgSeoul(R.drawable.img_seoul);
        s5_12.setTxtStationName("오목교");
        s5_12.setImgStationNum(R.drawable.img_station5);

        StationBean s5_13 = new StationBean();
        s5_13.setImgSeoul(R.drawable.img_seoul);
        s5_13.setTxtStationName("양평");
        s5_13.setImgStationNum(R.drawable.img_station5);

        StationBean s5_14 = new StationBean();
        s5_14.setImgSeoul(R.drawable.img_seoul);
        s5_14.setTxtStationName("영등포구청");
        s5_14.setImgStationNum(R.drawable.img_station2);
        s5_14.setImgStationNum2(R.drawable.img_station5);

        StationBean s5_15 = new StationBean();
        s5_15.setImgSeoul(R.drawable.img_seoul);
        s5_15.setTxtStationName("영등포시장");
        s5_15.setImgStationNum(R.drawable.img_station5);

        StationBean s5_16 = new StationBean();
        s5_16.setImgSeoul(R.drawable.img_seoul);
        s5_16.setTxtStationName("신길");
        s5_16.setImgStationNum(R.drawable.img_station1);
        s5_16.setImgStationNum2(R.drawable.img_station5);

        StationBean s5_17 = new StationBean();
        s5_17.setImgSeoul(R.drawable.img_seoul);
        s5_17.setTxtStationName("여의도");
        s5_17.setImgStationNum(R.drawable.img_station5);
        s5_17.setImgStationNum2(R.drawable.img_station9);

        StationBean s5_18 = new StationBean();
        s5_18.setImgSeoul(R.drawable.img_seoul);
        s5_18.setTxtStationName("여의나루");
        s5_18.setImgStationNum(R.drawable.img_station5);

        StationBean s5_19 = new StationBean();
        s5_19.setImgSeoul(R.drawable.img_seoul);
        s5_19.setTxtStationName("마포");
        s5_19.setImgStationNum(R.drawable.img_station5);

        StationBean s5_20 = new StationBean();
        s5_20.setImgSeoul(R.drawable.img_seoul);
        s5_20.setTxtStationName("공덕");
        s5_20.setImgStationNum(R.drawable.img_station5);
        s5_20.setImgStationNum2(R.drawable.img_station6);

        StationBean s5_21 = new StationBean();
        s5_21.setImgSeoul(R.drawable.img_seoul);
        s5_21.setTxtStationName("애오개");
        s5_21.setImgStationNum(R.drawable.img_station5);

        StationBean s5_22 = new StationBean();
        s5_22.setImgSeoul(R.drawable.img_seoul);
        s5_22.setTxtStationName("충정로");
        s5_22.setImgStationNum(R.drawable.img_station2);
        s5_22.setImgStationNum2(R.drawable.img_station5);

        StationBean s5_23 = new StationBean();
        s5_23.setImgSeoul(R.drawable.img_seoul);
        s5_23.setTxtStationName("서대문");
        s5_23.setImgStationNum(R.drawable.img_station5);

        StationBean s5_24 = new StationBean();
        s5_24.setImgSeoul(R.drawable.img_seoul);
        s5_24.setTxtStationName("광화문");
        s5_24.setImgStationNum(R.drawable.img_station5);

        StationBean s5_25 = new StationBean();
        s5_25.setImgSeoul(R.drawable.img_seoul);
        s5_25.setTxtStationName("종로3가");
        s5_25.setImgStationNum(R.drawable.img_station1);
        s5_25.setImgStationNum2(R.drawable.img_station3);
        s5_25.setImgStationNum3(R.drawable.img_station5);

        StationBean s5_26 = new StationBean();
        s5_26.setImgSeoul(R.drawable.img_seoul);
        s5_26.setTxtStationName("을지로4가");
        s5_26.setImgStationNum(R.drawable.img_station2);
        s5_26.setImgStationNum2(R.drawable.img_station5);

        StationBean s5_27 = new StationBean();
        s5_27.setImgSeoul(R.drawable.img_seoul);
        s5_27.setTxtStationName("동대문역사문화공원");
        s5_27.setImgStationNum(R.drawable.img_station2);
        s5_27.setImgStationNum2(R.drawable.img_station4);
        s5_27.setImgStationNum3(R.drawable.img_station5);

        StationBean s5_28 = new StationBean();
        s5_28.setImgSeoul(R.drawable.img_seoul);
        s5_28.setTxtStationName("청구");
        s5_28.setImgStationNum(R.drawable.img_station5);
        s5_28.setImgStationNum2(R.drawable.img_station6);

        StationBean s5_29 = new StationBean();
        s5_29.setImgSeoul(R.drawable.img_seoul);
        s5_29.setTxtStationName("신금호");
        s5_29.setImgStationNum(R.drawable.img_station5);

        StationBean s5_30 = new StationBean();
        s5_30.setImgSeoul(R.drawable.img_seoul);
        s5_30.setTxtStationName("행당");
        s5_30.setImgStationNum(R.drawable.img_station5);

        StationBean s5_31 = new StationBean();
        s5_31.setImgSeoul(R.drawable.img_seoul);
        s5_31.setTxtStationName("왕십리");
        s5_31.setImgStationNum(R.drawable.img_station2);
        s5_31.setImgStationNum2(R.drawable.img_station5);

        StationBean s5_32 = new StationBean();
        s5_32.setImgSeoul(R.drawable.img_seoul);
        s5_32.setTxtStationName("마장");
        s5_32.setImgStationNum(R.drawable.img_station5);

        StationBean s5_33 = new StationBean();
        s5_33.setImgSeoul(R.drawable.img_seoul);
        s5_33.setTxtStationName("답십리");
        s5_33.setImgStationNum(R.drawable.img_station5);

        StationBean s5_34 = new StationBean();
        s5_34.setImgSeoul(R.drawable.img_seoul);
        s5_34.setTxtStationName("장한평");
        s5_34.setImgStationNum(R.drawable.img_station5);

        StationBean s5_35 = new StationBean();
        s5_35.setImgSeoul(R.drawable.img_seoul);
        s5_35.setTxtStationName("군자");
        s5_35.setImgStationNum(R.drawable.img_station5);
        s5_35.setImgStationNum2(R.drawable.img_station7);

        StationBean s5_36 = new StationBean();
        s5_36.setImgSeoul(R.drawable.img_seoul);
        s5_36.setTxtStationName("아차산");
        s5_36.setImgStationNum(R.drawable.img_station5);

        StationBean s5_37 = new StationBean();
        s5_37.setImgSeoul(R.drawable.img_seoul);
        s5_37.setTxtStationName("광나루");
        s5_37.setImgStationNum(R.drawable.img_station5);

        StationBean s5_38 = new StationBean();
        s5_38.setImgSeoul(R.drawable.img_seoul);
        s5_38.setTxtStationName("천호");
        s5_38.setImgStationNum(R.drawable.img_station5);
        s5_38.setImgStationNum2(R.drawable.img_station8);

        StationBean s5_39 = new StationBean();
        s5_39.setImgSeoul(R.drawable.img_seoul);
        s5_39.setTxtStationName("강동");
        s5_39.setImgStationNum(R.drawable.img_station5);

        StationBean s5_40 = new StationBean();
        s5_40.setImgSeoul(R.drawable.img_seoul);
        s5_40.setTxtStationName("길동");
        s5_40.setImgStationNum(R.drawable.img_station5);

        StationBean s5_41 = new StationBean();
        s5_41.setImgSeoul(R.drawable.img_seoul);
        s5_41.setTxtStationName("굽은다리");
        s5_41.setImgStationNum(R.drawable.img_station5);

        StationBean s5_42 = new StationBean();
        s5_42.setImgSeoul(R.drawable.img_seoul);
        s5_42.setTxtStationName("명일");
        s5_42.setImgStationNum(R.drawable.img_station5);

        StationBean s5_43 = new StationBean();
        s5_43.setImgSeoul(R.drawable.img_seoul);
        s5_43.setTxtStationName("고덕");
        s5_43.setImgStationNum(R.drawable.img_station5);

        StationBean s5_44 = new StationBean();
        s5_44.setImgSeoul(R.drawable.img_seoul);
        s5_44.setTxtStationName("상일동");
        s5_44.setImgStationNum(R.drawable.img_station5);

        StationBean s5_45 = new StationBean();
        s5_45.setImgSeoul(R.drawable.img_seoul);
        s5_45.setTxtStationName("둔촌동");
        s5_45.setImgStationNum(R.drawable.img_station5);

        StationBean s5_46 = new StationBean();
        s5_46.setImgSeoul(R.drawable.img_seoul);
        s5_46.setTxtStationName("올림픽공원");
        s5_46.setImgStationNum(R.drawable.img_station5);
        s5_46.setImgStationNum2(R.drawable.img_station9);

        StationBean s5_47 = new StationBean();
        s5_47.setImgSeoul(R.drawable.img_seoul);
        s5_47.setTxtStationName("방이");
        s5_47.setImgStationNum(R.drawable.img_station5);

        StationBean s5_48 = new StationBean();
        s5_48.setImgSeoul(R.drawable.img_seoul);
        s5_48.setTxtStationName("오금");
        s5_48.setImgStationNum(R.drawable.img_station3);
        s5_48.setImgStationNum2(R.drawable.img_station5);

        StationBean s5_49 = new StationBean();
        s5_49.setImgSeoul(R.drawable.img_seoul);
        s5_49.setTxtStationName("개롱");
        s5_49.setImgStationNum(R.drawable.img_station5);

        StationBean s5_50 = new StationBean();
        s5_50.setImgSeoul(R.drawable.img_seoul);
        s5_50.setTxtStationName("거여");
        s5_50.setImgStationNum(R.drawable.img_station5);

        StationBean s5_51 = new StationBean();
        s5_51.setImgSeoul(R.drawable.img_seoul);
        s5_51.setTxtStationName("마천");
        s5_51.setImgStationNum(R.drawable.img_station5);

        //객체 정보(6호선)
        StationBean s6_1 = new StationBean();
        s6_1.setImgSeoul(R.drawable.img_seoul);
        s6_1.setTxtStationName("응암");
        s6_1.setImgStationNum(R.drawable.img_station6);

        StationBean s6_2 = new StationBean();
        s6_2.setImgSeoul(R.drawable.img_seoul);
        s6_2.setTxtStationName("역촌");
        s6_2.setImgStationNum(R.drawable.img_station6);

        StationBean s6_3 = new StationBean();
        s6_3.setImgSeoul(R.drawable.img_seoul);
        s6_3.setTxtStationName("불광");
        s6_3.setImgStationNum(R.drawable.img_station3);
        s6_3.setImgStationNum2(R.drawable.img_station6);

        StationBean s6_4 = new StationBean();
        s6_4.setImgSeoul(R.drawable.img_seoul);
        s6_4.setTxtStationName("독바위");
        s6_4.setImgStationNum(R.drawable.img_station6);

        StationBean s6_5 = new StationBean();
        s6_5.setImgSeoul(R.drawable.img_seoul);
        s6_5.setTxtStationName("연신내");
        s6_5.setImgStationNum(R.drawable.img_station3);
        s6_5.setImgStationNum2(R.drawable.img_station6);

        StationBean s6_6 = new StationBean();
        s6_6.setImgSeoul(R.drawable.img_seoul);
        s6_6.setTxtStationName("구산");
        s6_6.setImgStationNum(R.drawable.img_station6);

        StationBean s6_7 = new StationBean();
        s6_7.setImgSeoul(R.drawable.img_seoul);
        s6_7.setTxtStationName("새절");
        s6_7.setImgStationNum(R.drawable.img_station6);

        StationBean s6_8 = new StationBean();
        s6_8.setImgSeoul(R.drawable.img_seoul);
        s6_8.setTxtStationName("증산");
        s6_8.setImgStationNum(R.drawable.img_station6);

        StationBean s6_9 = new StationBean();
        s6_9.setImgSeoul(R.drawable.img_seoul);
        s6_9.setTxtStationName("디지털미디어시티");
        s6_9.setImgStationNum(R.drawable.img_station6);

        StationBean s6_10 = new StationBean();
        s6_10.setImgSeoul(R.drawable.img_seoul);
        s6_10.setTxtStationName("월드컵경기장");
        s6_10.setImgStationNum(R.drawable.img_station6);

        StationBean s6_11 = new StationBean();
        s6_11.setImgSeoul(R.drawable.img_seoul);
        s6_11.setTxtStationName("마포구청");
        s6_11.setImgStationNum(R.drawable.img_station6);

        StationBean s6_12 = new StationBean();
        s6_12.setImgSeoul(R.drawable.img_seoul);
        s6_12.setTxtStationName("망원");
        s6_12.setImgStationNum(R.drawable.img_station6);

        StationBean s6_13 = new StationBean();
        s6_13.setImgSeoul(R.drawable.img_seoul);
        s6_13.setTxtStationName("합정");
        s6_13.setImgStationNum(R.drawable.img_station2);
        s6_13.setImgStationNum2(R.drawable.img_station6);

        StationBean s6_14 = new StationBean();
        s6_14.setImgSeoul(R.drawable.img_seoul);
        s6_14.setTxtStationName("상수");
        s6_14.setImgStationNum(R.drawable.img_station6);

        StationBean s6_15 = new StationBean();
        s6_15.setImgSeoul(R.drawable.img_seoul);
        s6_15.setTxtStationName("광흥창");
        s6_15.setImgStationNum(R.drawable.img_station6);

        StationBean s6_16 = new StationBean();
        s6_16.setImgSeoul(R.drawable.img_seoul);
        s6_16.setTxtStationName("대흥");
        s6_16.setImgStationNum(R.drawable.img_station6);

        StationBean s6_17 = new StationBean();
        s6_17.setImgSeoul(R.drawable.img_seoul);
        s6_17.setTxtStationName("공덕");
        s6_17.setImgStationNum(R.drawable.img_station5);
        s6_17.setImgStationNum2(R.drawable.img_station6);

        StationBean s6_18 = new StationBean();
        s6_18.setImgSeoul(R.drawable.img_seoul);
        s6_18.setTxtStationName("효창공원앞");
        s6_18.setImgStationNum(R.drawable.img_station6);

        StationBean s6_19 = new StationBean();
        s6_19.setImgSeoul(R.drawable.img_seoul);
        s6_19.setTxtStationName("삼각지");
        s6_19.setImgStationNum(R.drawable.img_station4);
        s6_19.setImgStationNum2(R.drawable.img_station6);

        StationBean s6_20 = new StationBean();
        s6_20.setImgSeoul(R.drawable.img_seoul);
        s6_20.setTxtStationName("녹사평");
        s6_20.setImgStationNum(R.drawable.img_station6);

        StationBean s6_21 = new StationBean();
        s6_21.setImgSeoul(R.drawable.img_seoul);
        s6_21.setTxtStationName("이태원");
        s6_21.setImgStationNum(R.drawable.img_station6);

        StationBean s6_22 = new StationBean();
        s6_22.setImgSeoul(R.drawable.img_seoul);
        s6_22.setTxtStationName("한강진");
        s6_22.setImgStationNum(R.drawable.img_station6);

        StationBean s6_23 = new StationBean();
        s6_23.setImgSeoul(R.drawable.img_seoul);
        s6_23.setTxtStationName("버티고개");
        s6_23.setImgStationNum(R.drawable.img_station6);

        StationBean s6_24 = new StationBean();
        s6_24.setImgSeoul(R.drawable.img_seoul);
        s6_24.setTxtStationName("약수");
        s6_24.setImgStationNum(R.drawable.img_station3);
        s6_24.setImgStationNum2(R.drawable.img_station6);

        StationBean s6_25 = new StationBean();
        s6_25.setImgSeoul(R.drawable.img_seoul);
        s6_25.setTxtStationName("청구");
        s6_25.setImgStationNum(R.drawable.img_station5);
        s6_25.setImgStationNum2(R.drawable.img_station6);

        StationBean s6_26 = new StationBean();
        s6_26.setImgSeoul(R.drawable.img_seoul);
        s6_26.setTxtStationName("신당");
        s6_26.setImgStationNum(R.drawable.img_station2);
        s6_26.setImgStationNum2(R.drawable.img_station6);

        StationBean s6_27 = new StationBean();
        s6_27.setImgSeoul(R.drawable.img_seoul);
        s6_27.setTxtStationName("동묘앞");
        s6_27.setImgStationNum(R.drawable.img_station1);
        s6_27.setImgStationNum2(R.drawable.img_station6);

        StationBean s6_28 = new StationBean();
        s6_28.setImgSeoul(R.drawable.img_seoul);
        s6_28.setTxtStationName("창신");
        s6_28.setImgStationNum(R.drawable.img_station6);

        StationBean s6_29 = new StationBean();
        s6_29.setImgSeoul(R.drawable.img_seoul);
        s6_29.setTxtStationName("보문");
        s6_29.setImgStationNum(R.drawable.img_station6);

        StationBean s6_30 = new StationBean();
        s6_30.setImgSeoul(R.drawable.img_seoul);
        s6_30.setTxtStationName("안암");
        s6_30.setImgStationNum(R.drawable.img_station6);

        StationBean s6_31 = new StationBean();
        s6_31.setImgSeoul(R.drawable.img_seoul);
        s6_31.setTxtStationName("고려대");
        s6_31.setImgStationNum(R.drawable.img_station6);

        StationBean s6_32 = new StationBean();
        s6_32.setImgSeoul(R.drawable.img_seoul);
        s6_32.setTxtStationName("월곡");
        s6_32.setImgStationNum(R.drawable.img_station6);

        StationBean s6_33 = new StationBean();
        s6_33.setImgSeoul(R.drawable.img_seoul);
        s6_33.setTxtStationName("상월곡");
        s6_33.setImgStationNum(R.drawable.img_station6);

        StationBean s6_34 = new StationBean();
        s6_34.setImgSeoul(R.drawable.img_seoul);
        s6_34.setTxtStationName("돌곶이");
        s6_34.setImgStationNum(R.drawable.img_station6);

        StationBean s6_35 = new StationBean();
        s6_35.setImgSeoul(R.drawable.img_seoul);
        s6_35.setTxtStationName("석계");
        s6_35.setImgStationNum(R.drawable.img_station1);
        s6_35.setImgStationNum2(R.drawable.img_station6);

        StationBean s6_36 = new StationBean();
        s6_36.setImgSeoul(R.drawable.img_seoul);
        s6_36.setTxtStationName("태릉입구");
        s6_36.setImgStationNum(R.drawable.img_station6);
        s6_36.setImgStationNum2(R.drawable.img_station7);

        StationBean s6_37 = new StationBean();
        s6_37.setImgSeoul(R.drawable.img_seoul);
        s6_37.setTxtStationName("화랑대");
        s6_37.setImgStationNum(R.drawable.img_station6);

        StationBean s6_38 = new StationBean();
        s6_38.setImgSeoul(R.drawable.img_seoul);
        s6_38.setTxtStationName("봉화산");
        s6_38.setImgStationNum(R.drawable.img_station6);

        //객체 정보(7호선)
        StationBean s7_1 = new StationBean();
        s7_1.setImgSeoul(R.drawable.img_seoul);
        s7_1.setTxtStationName("장암");
        s7_1.setImgStationNum(R.drawable.img_station7);

        StationBean s7_2 = new StationBean();
        s7_2.setImgSeoul(R.drawable.img_seoul);
        s7_2.setTxtStationName("도봉산");
        s7_2.setImgStationNum(R.drawable.img_station1);
        s7_2.setImgStationNum2(R.drawable.img_station7);

        StationBean s7_3 = new StationBean();
        s7_3.setImgSeoul(R.drawable.img_seoul);
        s7_3.setTxtStationName("수락산");
        s7_3.setImgStationNum(R.drawable.img_station7);

        StationBean s7_4 = new StationBean();
        s7_4.setImgSeoul(R.drawable.img_seoul);
        s7_4.setTxtStationName("마들");
        s7_4.setImgStationNum(R.drawable.img_station7);

        StationBean s7_5 = new StationBean();
        s7_5.setImgSeoul(R.drawable.img_seoul);
        s7_5.setTxtStationName("노원");
        s7_5.setImgStationNum(R.drawable.img_station4);
        s7_5.setImgStationNum2(R.drawable.img_station7);

        StationBean s7_6 = new StationBean();
        s7_6.setImgSeoul(R.drawable.img_seoul);
        s7_6.setTxtStationName("중계");
        s7_6.setImgStationNum(R.drawable.img_station7);

        StationBean s7_7 = new StationBean();
        s7_7.setImgSeoul(R.drawable.img_seoul);
        s7_7.setTxtStationName("하계");
        s7_7.setImgStationNum(R.drawable.img_station7);

        StationBean s7_8 = new StationBean();
        s7_8.setImgSeoul(R.drawable.img_seoul);
        s7_8.setTxtStationName("공릉");
        s7_8.setImgStationNum(R.drawable.img_station7);

        StationBean s7_9 = new StationBean();
        s7_9.setImgSeoul(R.drawable.img_seoul);
        s7_9.setTxtStationName("태릉입구");
        s7_9.setImgStationNum(R.drawable.img_station6);
        s7_9.setImgStationNum2(R.drawable.img_station7);

        StationBean s7_10 = new StationBean();
        s7_10.setImgSeoul(R.drawable.img_seoul);
        s7_10.setTxtStationName("먹골");
        s7_10.setImgStationNum(R.drawable.img_station7);

        StationBean s7_11 = new StationBean();
        s7_11.setImgSeoul(R.drawable.img_seoul);
        s7_11.setTxtStationName("중화");
        s7_11.setImgStationNum(R.drawable.img_station7);

        StationBean s7_12 = new StationBean();
        s7_12.setImgSeoul(R.drawable.img_seoul);
        s7_12.setTxtStationName("상봉");
        s7_12.setImgStationNum(R.drawable.img_station7);

        StationBean s7_13 = new StationBean();
        s7_13.setImgSeoul(R.drawable.img_seoul);
        s7_13.setTxtStationName("면목");
        s7_13.setImgStationNum(R.drawable.img_station7);

        StationBean s7_14 = new StationBean();
        s7_14.setImgSeoul(R.drawable.img_seoul);
        s7_14.setTxtStationName("사가정");
        s7_14.setImgStationNum(R.drawable.img_station7);

        StationBean s7_15 = new StationBean();
        s7_15.setImgSeoul(R.drawable.img_seoul);
        s7_15.setTxtStationName("용마산");
        s7_15.setImgStationNum(R.drawable.img_station7);

        StationBean s7_16 = new StationBean();
        s7_16.setImgSeoul(R.drawable.img_seoul);
        s7_16.setTxtStationName("중곡");
        s7_16.setImgStationNum(R.drawable.img_station7);

        StationBean s7_17 = new StationBean();
        s7_17.setImgSeoul(R.drawable.img_seoul);
        s7_17.setTxtStationName("군자");
        s7_17.setImgStationNum(R.drawable.img_station5);
        s7_17.setImgStationNum2(R.drawable.img_station7);

        StationBean s7_18 = new StationBean();
        s7_18.setImgSeoul(R.drawable.img_seoul);
        s7_18.setTxtStationName("어린이대공원");
        s7_18.setImgStationNum(R.drawable.img_station7);

        StationBean s7_19 = new StationBean();
        s7_19.setImgSeoul(R.drawable.img_seoul);
        s7_19.setTxtStationName("건대입구");
        s7_19.setImgStationNum(R.drawable.img_station2);
        s7_19.setImgStationNum2(R.drawable.img_station7);

        StationBean s7_20 = new StationBean();
        s7_20.setImgSeoul(R.drawable.img_seoul);
        s7_20.setTxtStationName("뚝섬유원지");
        s7_20.setImgStationNum(R.drawable.img_station7);

        StationBean s7_21 = new StationBean();
        s7_21.setImgSeoul(R.drawable.img_seoul);
        s7_21.setTxtStationName("청담");
        s7_21.setImgStationNum(R.drawable.img_station7);

        StationBean s7_22 = new StationBean();
        s7_22.setImgSeoul(R.drawable.img_seoul);
        s7_22.setTxtStationName("강남구청");
        s7_22.setImgStationNum(R.drawable.img_station7);

        StationBean s7_23 = new StationBean();
        s7_23.setImgSeoul(R.drawable.img_seoul);
        s7_23.setTxtStationName("학동");
        s7_23.setImgStationNum(R.drawable.img_station7);

        StationBean s7_24 = new StationBean();
        s7_24.setImgSeoul(R.drawable.img_seoul);
        s7_24.setTxtStationName("논현");
        s7_24.setImgStationNum(R.drawable.img_station7);

        StationBean s7_25 = new StationBean();
        s7_25.setImgSeoul(R.drawable.img_seoul);
        s7_25.setTxtStationName("반포");
        s7_25.setImgStationNum(R.drawable.img_station7);

        StationBean s7_26 = new StationBean();
        s7_26.setImgSeoul(R.drawable.img_seoul);
        s7_26.setTxtStationName("고속터미널");
        s7_26.setImgStationNum(R.drawable.img_station3);
        s7_26.setImgStationNum2(R.drawable.img_station7);
        s7_26.setImgStationNum3(R.drawable.img_station9);

        StationBean s7_27 = new StationBean();
        s7_27.setImgSeoul(R.drawable.img_seoul);
        s7_27.setTxtStationName("내방");
        s7_27.setImgStationNum(R.drawable.img_station7);

        StationBean s7_28 = new StationBean();
        s7_28.setImgSeoul(R.drawable.img_seoul);
        s7_28.setTxtStationName("총신대입구(이수)");
        s7_28.setImgStationNum(R.drawable.img_station4);
        s7_28.setImgStationNum2(R.drawable.img_station7);

        StationBean s7_29 = new StationBean();
        s7_29.setImgSeoul(R.drawable.img_seoul);
        s7_29.setTxtStationName("남성");
        s7_29.setImgStationNum(R.drawable.img_station7);

        StationBean s7_30 = new StationBean();
        s7_30.setImgSeoul(R.drawable.img_seoul);
        s7_30.setTxtStationName("숭실대입구");
        s7_30.setImgStationNum(R.drawable.img_station7);

        StationBean s7_31 = new StationBean();
        s7_31.setImgSeoul(R.drawable.img_seoul);
        s7_31.setTxtStationName("상도");
        s7_31.setImgStationNum(R.drawable.img_station7);

        StationBean s7_32 = new StationBean();
        s7_32.setImgSeoul(R.drawable.img_seoul);
        s7_32.setTxtStationName("장승배기");
        s7_32.setImgStationNum(R.drawable.img_station7);

        StationBean s7_33 = new StationBean();
        s7_33.setImgSeoul(R.drawable.img_seoul);
        s7_33.setTxtStationName("신대방삼거리");
        s7_33.setImgStationNum(R.drawable.img_station7);

        StationBean s7_34 = new StationBean();
        s7_34.setImgSeoul(R.drawable.img_seoul);
        s7_34.setTxtStationName("보라매");
        s7_34.setImgStationNum(R.drawable.img_station7);

        StationBean s7_35 = new StationBean();
        s7_35.setImgSeoul(R.drawable.img_seoul);
        s7_35.setTxtStationName("신풍");
        s7_35.setImgStationNum(R.drawable.img_station7);

        StationBean s7_36 = new StationBean();
        s7_36.setImgSeoul(R.drawable.img_seoul);
        s7_36.setTxtStationName("대림");
        s7_36.setImgStationNum(R.drawable.img_station2);
        s7_36.setImgStationNum2(R.drawable.img_station7);

        StationBean s7_37 = new StationBean();
        s7_37.setImgSeoul(R.drawable.img_seoul);
        s7_37.setTxtStationName("남구로");
        s7_37.setImgStationNum(R.drawable.img_station7);

        StationBean s7_38 = new StationBean();
        s7_38.setImgSeoul(R.drawable.img_seoul);
        s7_38.setTxtStationName("가산디지털단지");
        s7_38.setImgStationNum(R.drawable.img_station1);
        s7_38.setImgStationNum2(R.drawable.img_station7);

        StationBean s7_39 = new StationBean();
        s7_39.setImgSeoul(R.drawable.img_seoul);
        s7_39.setTxtStationName("철산");
        s7_39.setImgStationNum(R.drawable.img_station7);

        StationBean s7_40 = new StationBean();
        s7_40.setImgSeoul(R.drawable.img_seoul);
        s7_40.setTxtStationName("광명사거리");
        s7_40.setImgStationNum(R.drawable.img_station7);

        StationBean s7_41 = new StationBean();
        s7_41.setImgSeoul(R.drawable.img_seoul);
        s7_41.setTxtStationName("천왕");
        s7_41.setImgStationNum(R.drawable.img_station7);

        StationBean s7_42 = new StationBean();
        s7_42.setImgSeoul(R.drawable.img_seoul);
        s7_42.setTxtStationName("온수");
        s7_42.setImgStationNum(R.drawable.img_station1);
        s7_42.setImgStationNum2(R.drawable.img_station7);

        StationBean s7_43 = new StationBean();
        s7_43.setImgSeoul(R.drawable.img_seoul);
        s7_43.setTxtStationName("까치울");
        s7_43.setImgStationNum(R.drawable.img_station7);

        StationBean s7_44 = new StationBean();
        s7_44.setImgSeoul(R.drawable.img_seoul);
        s7_44.setTxtStationName("부천종합운동장");
        s7_44.setImgStationNum(R.drawable.img_station7);

        StationBean s7_45 = new StationBean();
        s7_45.setImgSeoul(R.drawable.img_seoul);
        s7_45.setTxtStationName("춘의");
        s7_45.setImgStationNum(R.drawable.img_station7);

        StationBean s7_46 = new StationBean();
        s7_46.setImgSeoul(R.drawable.img_seoul);
        s7_46.setTxtStationName("신중동");
        s7_46.setImgStationNum(R.drawable.img_station7);

        StationBean s7_47 = new StationBean();
        s7_47.setImgSeoul(R.drawable.img_seoul);
        s7_47.setTxtStationName("부천시청");
        s7_47.setImgStationNum(R.drawable.img_station7);

        StationBean s7_48 = new StationBean();
        s7_48.setImgSeoul(R.drawable.img_seoul);
        s7_48.setTxtStationName("상동");
        s7_48.setImgStationNum(R.drawable.img_station7);

        StationBean s7_49 = new StationBean();
        s7_49.setImgSeoul(R.drawable.img_seoul);
        s7_49.setTxtStationName("삼산체육관");
        s7_49.setImgStationNum(R.drawable.img_station7);

        StationBean s7_50 = new StationBean();
        s7_50.setImgSeoul(R.drawable.img_seoul);
        s7_50.setTxtStationName("굴포천");
        s7_50.setImgStationNum(R.drawable.img_station7);

        StationBean s7_51 = new StationBean();
        s7_51.setImgSeoul(R.drawable.img_seoul);
        s7_51.setTxtStationName("부평구청");
        s7_51.setImgStationNum(R.drawable.img_station7);

        //객체 정보(8호선)
        StationBean s8_1 = new StationBean();
        s8_1.setImgSeoul(R.drawable.img_seoul);
        s8_1.setTxtStationName("암사");
        s8_1.setImgStationNum(R.drawable.img_station8);

        StationBean s8_2 = new StationBean();
        s8_2.setImgSeoul(R.drawable.img_seoul);
        s8_2.setTxtStationName("천호");
        s8_2.setImgStationNum(R.drawable.img_station5);
        s8_2.setImgStationNum2(R.drawable.img_station8);

        StationBean s8_3 = new StationBean();
        s8_3.setImgSeoul(R.drawable.img_seoul);
        s8_3.setTxtStationName("강동구청");
        s8_3.setImgStationNum(R.drawable.img_station8);

        StationBean s8_4 = new StationBean();
        s8_4.setImgSeoul(R.drawable.img_seoul);
        s8_4.setTxtStationName("몽촌토성");
        s8_4.setImgStationNum(R.drawable.img_station8);

        StationBean s8_5 = new StationBean();
        s8_5.setImgSeoul(R.drawable.img_seoul);
        s8_5.setTxtStationName("잠실");
        s8_5.setImgStationNum(R.drawable.img_station2);
        s8_5.setImgStationNum2(R.drawable.img_station8);

        StationBean s8_6 = new StationBean();
        s8_6.setImgSeoul(R.drawable.img_seoul);
        s8_6.setTxtStationName("석촌");
        s8_6.setImgStationNum(R.drawable.img_station8);
        s8_6.setImgStationNum2(R.drawable.img_station9);

        StationBean s8_7 = new StationBean();
        s8_7.setImgSeoul(R.drawable.img_seoul);
        s8_7.setTxtStationName("송파");
        s8_7.setImgStationNum(R.drawable.img_station8);

        StationBean s8_8 = new StationBean();
        s8_8.setImgSeoul(R.drawable.img_seoul);
        s8_8.setTxtStationName("가락시장");
        s8_8.setImgStationNum(R.drawable.img_station3);
        s8_8.setImgStationNum2(R.drawable.img_station8);

        StationBean s8_9 = new StationBean();
        s8_9.setImgSeoul(R.drawable.img_seoul);
        s8_9.setTxtStationName("문정");
        s8_9.setImgStationNum(R.drawable.img_station8);

        StationBean s8_10 = new StationBean();
        s8_10.setImgSeoul(R.drawable.img_seoul);
        s8_10.setTxtStationName("장지");
        s8_10.setImgStationNum(R.drawable.img_station8);

        StationBean s8_11 = new StationBean();
        s8_11.setImgSeoul(R.drawable.img_seoul);
        s8_11.setTxtStationName("복정");
        s8_11.setImgStationNum(R.drawable.img_station8);

        StationBean s8_12 = new StationBean();
        s8_12.setImgSeoul(R.drawable.img_seoul);
        s8_12.setTxtStationName("산성");
        s8_12.setImgStationNum(R.drawable.img_station8);

        StationBean s8_13 = new StationBean();
        s8_13.setImgSeoul(R.drawable.img_seoul);
        s8_13.setTxtStationName("남한산성입구");
        s8_13.setImgStationNum(R.drawable.img_station8);

        StationBean s8_14 = new StationBean();
        s8_14.setImgSeoul(R.drawable.img_seoul);
        s8_14.setTxtStationName("단대오거리");
        s8_14.setImgStationNum(R.drawable.img_station8);

        StationBean s8_15 = new StationBean();
        s8_15.setImgSeoul(R.drawable.img_seoul);
        s8_15.setTxtStationName("신흥");
        s8_15.setImgStationNum(R.drawable.img_station8);

        StationBean s8_16 = new StationBean();
        s8_16.setImgSeoul(R.drawable.img_seoul);
        s8_16.setTxtStationName("수진");
        s8_16.setImgStationNum(R.drawable.img_station8);

        StationBean s8_17 = new StationBean();
        s8_17.setImgSeoul(R.drawable.img_seoul);
        s8_17.setTxtStationName("모란");
        s8_17.setImgStationNum(R.drawable.img_station8);

        //객체 추가
        final List<StationBean> stationList1 = new ArrayList<>();
        stationList1.add(s1_1);
        stationList1.add(s1_2);
        stationList1.add(s1_3);
        stationList1.add(s1_4);
        stationList1.add(s1_5);
        stationList1.add(s1_6);
        stationList1.add(s1_7);
        stationList1.add(s1_8);
        stationList1.add(s1_9);
        stationList1.add(s1_10);

        final List<StationBean> stationList2 = new ArrayList<>();
        stationList2.add(s2_1);
        stationList2.add(s2_2);
        stationList2.add(s2_3);
        stationList2.add(s2_4);
        stationList2.add(s2_5);
        stationList2.add(s2_6);
        stationList2.add(s2_7);
        stationList2.add(s2_8);
        stationList2.add(s2_9);
        stationList2.add(s2_10);
        stationList2.add(s2_11);
        stationList2.add(s2_12);
        stationList2.add(s2_13);
        stationList2.add(s2_14);
        stationList2.add(s2_15);
        stationList2.add(s2_16);
        stationList2.add(s2_17);
        stationList2.add(s2_18);
        stationList2.add(s2_19);
        stationList2.add(s2_20);
        stationList2.add(s2_21);
        stationList2.add(s2_22);
        stationList2.add(s2_23);
        stationList2.add(s2_24);
        stationList2.add(s2_25);
        stationList2.add(s2_26);
        stationList2.add(s2_27);
        stationList2.add(s2_28);
        stationList2.add(s2_29);
        stationList2.add(s2_30);
        stationList2.add(s2_31);
        stationList2.add(s2_32);
        stationList2.add(s2_33);
        stationList2.add(s2_34);
        stationList2.add(s2_35);
        stationList2.add(s2_36);
        stationList2.add(s2_37);
        stationList2.add(s2_38);
        stationList2.add(s2_39);
        stationList2.add(s2_40);
        stationList2.add(s2_41);
        stationList2.add(s2_42);
        stationList2.add(s2_43);
        stationList2.add(s2_44);
        stationList2.add(s2_45);
        stationList2.add(s2_46);
        stationList2.add(s2_47);
        stationList2.add(s2_48);
        stationList2.add(s2_49);
        stationList2.add(s2_50);
        stationList2.add(s2_51);

        final List<StationBean> stationList3 = new ArrayList<>();
        stationList3.add(s3_1);
        stationList3.add(s3_2);
        stationList3.add(s3_3);
        stationList3.add(s3_4);
        stationList3.add(s3_5);
        stationList3.add(s3_6);
        stationList3.add(s3_7);
        stationList3.add(s3_8);
        stationList3.add(s3_9);
        stationList3.add(s3_10);
        stationList3.add(s3_11);
        stationList3.add(s3_12);
        stationList3.add(s3_13);
        stationList3.add(s3_14);
        stationList3.add(s3_15);
        stationList3.add(s3_16);
        stationList3.add(s3_17);
        stationList3.add(s3_18);
        stationList3.add(s3_19);
        stationList3.add(s3_20);
        stationList3.add(s3_21);
        stationList3.add(s3_22);
        stationList3.add(s3_23);
        stationList3.add(s3_24);
        stationList3.add(s3_25);
        stationList3.add(s3_26);
        stationList3.add(s3_27);
        stationList3.add(s3_28);
        stationList3.add(s3_29);
        stationList3.add(s3_30);
        stationList3.add(s3_31);
        stationList3.add(s3_32);
        stationList3.add(s3_33);
        stationList3.add(s3_34);

        final List<StationBean> stationList4 = new ArrayList<>();
        stationList4.add(s4_1);
        stationList4.add(s4_2);
        stationList4.add(s4_3);
        stationList4.add(s4_4);
        stationList4.add(s4_5);
        stationList4.add(s4_6);
        stationList4.add(s4_7);
        stationList4.add(s4_8);
        stationList4.add(s4_9);
        stationList4.add(s4_10);
        stationList4.add(s4_11);
        stationList4.add(s4_12);
        stationList4.add(s4_13);
        stationList4.add(s4_14);
        stationList4.add(s4_15);
        stationList4.add(s4_16);
        stationList4.add(s4_17);
        stationList4.add(s4_18);
        stationList4.add(s4_19);
        stationList4.add(s4_20);
        stationList4.add(s4_21);
        stationList4.add(s4_22);
        stationList4.add(s4_23);
        stationList4.add(s4_24);
        stationList4.add(s4_25);
        stationList4.add(s4_26);

        final List<StationBean> stationList5 = new ArrayList<>();
        stationList5.add(s5_1);
        stationList5.add(s5_2);
        stationList5.add(s5_3);
        stationList5.add(s5_4);
        stationList5.add(s5_5);
        stationList5.add(s5_6);
        stationList5.add(s5_7);
        stationList5.add(s5_8);
        stationList5.add(s5_9);
        stationList5.add(s5_10);
        stationList5.add(s5_11);
        stationList5.add(s5_12);
        stationList5.add(s5_13);
        stationList5.add(s5_14);
        stationList5.add(s5_15);
        stationList5.add(s5_16);
        stationList5.add(s5_17);
        stationList5.add(s5_18);
        stationList5.add(s5_19);
        stationList5.add(s5_20);
        stationList5.add(s5_21);
        stationList5.add(s5_22);
        stationList5.add(s5_23);
        stationList5.add(s5_24);
        stationList5.add(s5_25);
        stationList5.add(s5_26);
        stationList5.add(s5_27);
        stationList5.add(s5_28);
        stationList5.add(s5_29);
        stationList5.add(s5_30);
        stationList5.add(s5_31);
        stationList5.add(s5_32);
        stationList5.add(s5_33);
        stationList5.add(s5_34);
        stationList5.add(s5_35);
        stationList5.add(s5_36);
        stationList5.add(s5_37);
        stationList5.add(s5_38);
        stationList5.add(s5_39);
        stationList5.add(s5_40);
        stationList5.add(s5_41);
        stationList5.add(s5_42);
        stationList5.add(s5_43);
        stationList5.add(s5_44);
        stationList5.add(s5_45);
        stationList5.add(s5_46);
        stationList5.add(s5_47);
        stationList5.add(s5_48);
        stationList5.add(s5_49);
        stationList5.add(s5_50);
        stationList5.add(s5_51);

        final List<StationBean> stationList6 = new ArrayList<>();
        stationList6.add(s6_1);
        stationList6.add(s6_2);
        stationList6.add(s6_3);
        stationList6.add(s6_4);
        stationList6.add(s6_5);
        stationList6.add(s6_6);
        stationList6.add(s6_7);
        stationList6.add(s6_8);
        stationList6.add(s6_9);
        stationList6.add(s6_10);
        stationList6.add(s6_11);
        stationList6.add(s6_12);
        stationList6.add(s6_13);
        stationList6.add(s6_14);
        stationList6.add(s6_15);
        stationList6.add(s6_16);
        stationList6.add(s6_17);
        stationList6.add(s6_18);
        stationList6.add(s6_19);
        stationList6.add(s6_20);
        stationList6.add(s6_21);
        stationList6.add(s6_22);
        stationList6.add(s6_23);
        stationList6.add(s6_24);
        stationList6.add(s6_25);
        stationList6.add(s6_26);
        stationList6.add(s6_27);
        stationList6.add(s6_28);
        stationList6.add(s6_29);
        stationList6.add(s6_30);
        stationList6.add(s6_31);
        stationList6.add(s6_32);
        stationList6.add(s6_33);
        stationList6.add(s6_34);
        stationList6.add(s6_35);
        stationList6.add(s6_36);
        stationList6.add(s6_37);
        stationList6.add(s6_38);

        final List<StationBean> stationList7 = new ArrayList<>();
        stationList7.add(s7_1);
        stationList7.add(s7_2);
        stationList7.add(s7_3);
        stationList7.add(s7_4);
        stationList7.add(s7_5);
        stationList7.add(s7_6);
        stationList7.add(s7_7);
        stationList7.add(s7_8);
        stationList7.add(s7_9);
        stationList7.add(s7_10);
        stationList7.add(s7_11);
        stationList7.add(s7_12);
        stationList7.add(s7_13);
        stationList7.add(s7_14);
        stationList7.add(s7_15);
        stationList7.add(s7_16);
        stationList7.add(s7_17);
        stationList7.add(s7_18);
        stationList7.add(s7_19);
        stationList7.add(s7_20);
        stationList7.add(s7_21);
        stationList7.add(s7_22);
        stationList7.add(s7_23);
        stationList7.add(s7_24);
        stationList7.add(s7_25);
        stationList7.add(s7_26);
        stationList7.add(s7_27);
        stationList7.add(s7_28);
        stationList7.add(s7_29);
        stationList7.add(s7_30);
        stationList7.add(s7_31);
        stationList7.add(s7_32);
        stationList7.add(s7_33);
        stationList7.add(s7_34);
        stationList7.add(s7_35);
        stationList7.add(s7_36);
        stationList7.add(s7_37);
        stationList7.add(s7_38);
        stationList7.add(s7_39);
        stationList7.add(s7_40);
        stationList7.add(s7_41);
        stationList7.add(s7_42);
        stationList7.add(s7_43);
        stationList7.add(s7_44);
        stationList7.add(s7_45);
        stationList7.add(s7_46);
        stationList7.add(s7_47);
        stationList7.add(s7_48);
        stationList7.add(s7_49);
        stationList7.add(s7_50);
        stationList7.add(s7_51);

        final List<StationBean> stationList8 = new ArrayList<>();
        stationList8.add(s8_1);
        stationList8.add(s8_2);
        stationList8.add(s8_3);
        stationList8.add(s8_4);
        stationList8.add(s8_5);
        stationList8.add(s8_6);
        stationList8.add(s8_7);
        stationList8.add(s8_8);
        stationList8.add(s8_9);
        stationList8.add(s8_10);
        stationList8.add(s8_11);
        stationList8.add(s8_12);
        stationList8.add(s8_13);
        stationList8.add(s8_14);
        stationList8.add(s8_15);
        stationList8.add(s8_16);
        stationList8.add(s8_17);

        //디폴트로 1호선 리스트 나오게
        lstStation1.setVisibility(View.VISIBLE);
        btnS1.setBackgroundResource(R.drawable.list_line1_selected);
        StationAdapter stationAdapter = new StationAdapter(SelectActivity.this, stationList1);//Adapter를 생성한다.
        lstStation1.setAdapter(stationAdapter);//Adapter를 ListView에 부착시킨다.

        //버튼 이벤트
        btnS1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lstStation1.setVisibility(View.VISIBLE);
                lstStation2.setVisibility(View.INVISIBLE);
                lstStation3.setVisibility(View.INVISIBLE);
                lstStation4.setVisibility(View.INVISIBLE);
                lstStation5.setVisibility(View.INVISIBLE);
                lstStation6.setVisibility(View.INVISIBLE);
                lstStation7.setVisibility(View.INVISIBLE);
                lstStation8.setVisibility(View.INVISIBLE);

                btnS1.setBackgroundResource(R.drawable.list_line1_selected);
                btnS2.setBackgroundResource(R.drawable.list_line2);
                btnS3.setBackgroundResource(R.drawable.list_line3);
                btnS4.setBackgroundResource(R.drawable.list_line4);
                btnS5.setBackgroundResource(R.drawable.list_line5);
                btnS6.setBackgroundResource(R.drawable.list_line6);
                btnS7.setBackgroundResource(R.drawable.list_line7);
                btnS8.setBackgroundResource(R.drawable.list_line8);

                //Adapter를 생성한다.
                StationAdapter stationAdapter = new StationAdapter(SelectActivity.this, stationList1);

                //Adapter를 ListView에 부착시킨다.
                lstStation1.setAdapter(stationAdapter);
            }
        });

        btnS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lstStation1.setVisibility(View.INVISIBLE);
                lstStation2.setVisibility(View.VISIBLE);
                lstStation3.setVisibility(View.INVISIBLE);
                lstStation4.setVisibility(View.INVISIBLE);
                lstStation5.setVisibility(View.INVISIBLE);
                lstStation6.setVisibility(View.INVISIBLE);
                lstStation7.setVisibility(View.INVISIBLE);
                lstStation8.setVisibility(View.INVISIBLE);

                btnS1.setBackgroundResource(R.drawable.list_line1);
                btnS2.setBackgroundResource(R.drawable.list_line2_selected);
                btnS3.setBackgroundResource(R.drawable.list_line3);
                btnS4.setBackgroundResource(R.drawable.list_line4);
                btnS5.setBackgroundResource(R.drawable.list_line5);
                btnS6.setBackgroundResource(R.drawable.list_line6);
                btnS7.setBackgroundResource(R.drawable.list_line7);
                btnS8.setBackgroundResource(R.drawable.list_line8);

                //Adapter를 생성한다
                StationAdapter stationAdapter = new StationAdapter(SelectActivity.this, stationList2);

                //Adapter를 ListView에 부착시킨다
                lstStation2.setAdapter(stationAdapter);
            }
        });

        btnS3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lstStation1.setVisibility(View.INVISIBLE);
                lstStation2.setVisibility(View.INVISIBLE);
                lstStation3.setVisibility(View.VISIBLE);
                lstStation4.setVisibility(View.INVISIBLE);
                lstStation5.setVisibility(View.INVISIBLE);
                lstStation6.setVisibility(View.INVISIBLE);
                lstStation7.setVisibility(View.INVISIBLE);
                lstStation8.setVisibility(View.INVISIBLE);

                btnS1.setBackgroundResource(R.drawable.list_line1);
                btnS2.setBackgroundResource(R.drawable.list_line2);
                btnS3.setBackgroundResource(R.drawable.list_line3_selected);
                btnS4.setBackgroundResource(R.drawable.list_line4);
                btnS5.setBackgroundResource(R.drawable.list_line5);
                btnS6.setBackgroundResource(R.drawable.list_line6);
                btnS7.setBackgroundResource(R.drawable.list_line7);
                btnS8.setBackgroundResource(R.drawable.list_line8);

                //Adapter를 생성한다
                StationAdapter stationAdapter = new StationAdapter(SelectActivity.this, stationList3);

                //Adapter를 ListView에 부착시킨다
                lstStation3.setAdapter(stationAdapter);
            }
        });

        btnS4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lstStation1.setVisibility(View.INVISIBLE);
                lstStation2.setVisibility(View.INVISIBLE);
                lstStation3.setVisibility(View.INVISIBLE);
                lstStation4.setVisibility(View.VISIBLE);
                lstStation5.setVisibility(View.INVISIBLE);
                lstStation6.setVisibility(View.INVISIBLE);
                lstStation7.setVisibility(View.INVISIBLE);
                lstStation8.setVisibility(View.INVISIBLE);

                btnS1.setBackgroundResource(R.drawable.list_line1);
                btnS2.setBackgroundResource(R.drawable.list_line2);
                btnS3.setBackgroundResource(R.drawable.list_line3);
                btnS4.setBackgroundResource(R.drawable.list_line4_selected);
                btnS5.setBackgroundResource(R.drawable.list_line5);
                btnS6.setBackgroundResource(R.drawable.list_line6);
                btnS7.setBackgroundResource(R.drawable.list_line7);
                btnS8.setBackgroundResource(R.drawable.list_line8);

                //Adapter를 생성한다.
                StationAdapter stationAdapter = new StationAdapter(SelectActivity.this, stationList4);

                //Adapter를 ListView에 부착시킨다.
                lstStation4.setAdapter(stationAdapter);

            }
        });

        btnS5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lstStation1.setVisibility(View.INVISIBLE);
                lstStation2.setVisibility(View.INVISIBLE);
                lstStation3.setVisibility(View.INVISIBLE);
                lstStation4.setVisibility(View.INVISIBLE);
                lstStation5.setVisibility(View.VISIBLE);
                lstStation6.setVisibility(View.INVISIBLE);
                lstStation7.setVisibility(View.INVISIBLE);
                lstStation8.setVisibility(View.INVISIBLE);

                btnS1.setBackgroundResource(R.drawable.list_line1);
                btnS2.setBackgroundResource(R.drawable.list_line2);
                btnS3.setBackgroundResource(R.drawable.list_line3);
                btnS4.setBackgroundResource(R.drawable.list_line4);
                btnS5.setBackgroundResource(R.drawable.list_line5_selected);
                btnS6.setBackgroundResource(R.drawable.list_line6);
                btnS7.setBackgroundResource(R.drawable.list_line7);
                btnS8.setBackgroundResource(R.drawable.list_line8);

                //Adapter를 생성한다.
                StationAdapter stationAdapter = new StationAdapter(SelectActivity.this, stationList5);

                //Adapter를 ListView에 부착시킨다.
                lstStation5.setAdapter(stationAdapter);
            }
        });

        btnS6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lstStation1.setVisibility(View.INVISIBLE);
                lstStation2.setVisibility(View.INVISIBLE);
                lstStation3.setVisibility(View.INVISIBLE);
                lstStation4.setVisibility(View.INVISIBLE);
                lstStation5.setVisibility(View.INVISIBLE);
                lstStation6.setVisibility(View.VISIBLE);
                lstStation7.setVisibility(View.INVISIBLE);
                lstStation8.setVisibility(View.INVISIBLE);

                btnS1.setBackgroundResource(R.drawable.list_line1);
                btnS2.setBackgroundResource(R.drawable.list_line2);
                btnS3.setBackgroundResource(R.drawable.list_line3);
                btnS4.setBackgroundResource(R.drawable.list_line4);
                btnS5.setBackgroundResource(R.drawable.list_line5);
                btnS6.setBackgroundResource(R.drawable.list_line6_selected);
                btnS7.setBackgroundResource(R.drawable.list_line7);
                btnS8.setBackgroundResource(R.drawable.list_line8);

                //Adapter를 생성한다.
                StationAdapter stationAdapter = new StationAdapter(SelectActivity.this, stationList6);

                //Adapter를 ListView에 부착시킨다.
                lstStation6.setAdapter(stationAdapter);
            }
        });

        btnS7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lstStation1.setVisibility(View.INVISIBLE);
                lstStation2.setVisibility(View.INVISIBLE);
                lstStation3.setVisibility(View.INVISIBLE);
                lstStation4.setVisibility(View.INVISIBLE);
                lstStation5.setVisibility(View.INVISIBLE);
                lstStation6.setVisibility(View.INVISIBLE);
                lstStation7.setVisibility(View.VISIBLE);
                lstStation8.setVisibility(View.INVISIBLE);

                btnS1.setBackgroundResource(R.drawable.list_line1);
                btnS2.setBackgroundResource(R.drawable.list_line2);
                btnS3.setBackgroundResource(R.drawable.list_line3);
                btnS4.setBackgroundResource(R.drawable.list_line4);
                btnS5.setBackgroundResource(R.drawable.list_line5);
                btnS6.setBackgroundResource(R.drawable.list_line6);
                btnS7.setBackgroundResource(R.drawable.list_line7_selected);
                btnS8.setBackgroundResource(R.drawable.list_line8);

                //Adapter를 생성한다.
                StationAdapter stationAdapter = new StationAdapter(SelectActivity.this, stationList7);

                //Adapter를 ListView에 부착시킨다.
                lstStation7.setAdapter(stationAdapter);
            }
        });

        btnS8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lstStation1.setVisibility(View.INVISIBLE);
                lstStation2.setVisibility(View.INVISIBLE);
                lstStation3.setVisibility(View.INVISIBLE);
                lstStation4.setVisibility(View.INVISIBLE);
                lstStation5.setVisibility(View.INVISIBLE);
                lstStation6.setVisibility(View.INVISIBLE);
                lstStation7.setVisibility(View.INVISIBLE);
                lstStation8.setVisibility(View.VISIBLE);

                btnS1.setBackgroundResource(R.drawable.list_line1);
                btnS2.setBackgroundResource(R.drawable.list_line2);
                btnS3.setBackgroundResource(R.drawable.list_line3);
                btnS4.setBackgroundResource(R.drawable.list_line4);
                btnS5.setBackgroundResource(R.drawable.list_line5);
                btnS6.setBackgroundResource(R.drawable.list_line6);
                btnS7.setBackgroundResource(R.drawable.list_line7);
                btnS8.setBackgroundResource(R.drawable.list_line8_selected);

                //Adapter를 생성한다.
                StationAdapter stationAdapter = new StationAdapter(SelectActivity.this, stationList8);

                //Adapter를 ListView에 부착시킨다.
                lstStation8.setAdapter(stationAdapter);
            }
        });

        //예진 리스트 끝

        //현재 요일을 DB 검색하기 편하게 요일 형식 바꾸기
        if (formatDay == "토") {
            day = "sat";
        } else if (formatDay == "일") {
            day = "sun";
        } else {
            day = "week";
        }

        //현재시간으로 DB에 검색
        time  = "time" + formatHour ;

        database = FirebaseDatabase.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("데이터를 불러오고 있습니다");


        //도착역 선택 버튼 클릭했을 때
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                congestion = null;

                if (time.equals("time01") || time.equals("time02") || time.equals("time03") || time.equals("time04")) {
                    //1시, 2시, 3시는 어떤 경우에도 운행시간이 아니기 때문에 DB를 거칠 필요 없음
                    congestion = "운행시간아님";

                    final Intent intent = new Intent(getApplicationContext(), CongestionActivity.class);
                    intent.putExtra("FORMATTIME", formatTime);
                    intent.putExtra("DEPART", DepartArrive.get(0));
                    intent.putExtra("ARRIVE", DepartArrive.get(1));
                    intent.putExtra("CONGESTION", congestion);
                    startActivity(intent);
                } else {

                    if (DepartArrive.size() == 1) {
                        Toast.makeText(SelectActivity.this, "도착역을 설정하세요", Toast.LENGTH_SHORT).show();
                    } else {
                        //DepartArrive.add("동대문역사문화공원"); //임의로 넣음(추후 수정)
                        //DepartArrive.add("사당"); //임의로 넣음 (추후 수정)

                        //선택받은 출발역과 도착역 four 배열에서 찾아서 몇번째인지 변수에 저장하기
                        for (int i = 0; i < four.length; i++) {
                            if (four[i].equals(DepartArrive.get(0))) {
                                depart = i;
                            }
                            if (four[i].equals(DepartArrive.get(1))) {
                                arrive = i;
                            }
                        }

                        //상행 하행 판단
                        if (arrive - depart < 0) { //ex 동대문->사당 //상행 (DB에 상행이라 저장함)
                            direction = "up";
                        } else {  //나머지는 하행
                            direction = "down";
                        }

                        //DB 코드
                        datas.clear();
                        stations.clear();
                        dialog.show();
                        database.getReference().child("4" + day + direction).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                                        Data data = datasnapshot.getValue(Data.class);

                                        if (direction.equals("down")) { //하행일 때 (동대문 -> 사당)

                                            for (int i = depart; i <= arrive; i++) {
                                                if (data.station.equals(four[i])) {
                                                    Log.d(TAG, "four하행=====>" + four[i]);
                                                    stations.add(data.station);
                                                    for (Field field : data.getClass().getDeclaredFields()) {
                                                        try {
                                                            field.setAccessible(true);
                                                            if (field.getName().equals(time)) {
                                                                Log.d(TAG, "field===>" + field.getName());
                                                                String value = (String) field.get(data);
                                                                Log.d(TAG, "value===>" + value);
                                                                datas.add(value);
                                                                if (data.station.equals(four[depart])) {
                                                                    congestion = value;
                                                                    //intent.putExtra("CONGESTION",congestion);
                                                                }
                                                            }

                                                        } catch (Exception e) {
                                                            Log.d(TAG, "ㅜㅜㅜㅜㅜㅜㅜ");
                                                        }

                                                    }
                                                    //datas.add(data.time13);
                                                    break;
                                                }
                                            }

                                        } else { //상행일 때 (사당 -> 동대문)
                                            for (int i = depart; i >= arrive; i--) {
                                                if (data.station.equals(four[i])) {
                                                    Log.d(TAG, "four상행=====>" + four[i]);
                                                    stations.add(data.station);
                                                    for (Field field : data.getClass().getDeclaredFields()) {
                                                        try {
                                                            field.setAccessible(true);
                                                            if (field.getName().equals(time)) {
                                                                Log.d(TAG, "field===>" + field.getName());
                                                                String value = (String) field.get(data);
                                                                Log.d(TAG, "value===>" + value);
                                                                datas.add(value);
                                                                if (data.station.equals(four[depart])) {
                                                                    congestion = value;
                                                                    //intent.putExtra("CONGESTION",congestion);
                                                                }
                                                            }
                                                        } catch (Exception e) {
                                                            Log.d(TAG, "ㅜㅜㅜㅜㅜㅜㅜ");
                                                        }

                                                    }
                                                    //datas.add(data.time13);
                                                    break;
                                                }
                                            }

                                        }
                                    }
                                    Log.d(TAG, datas.toString());

                                    if (direction.equals("up")) { //상행일 때 (사당 -> 동대문)
                                        Collections.reverse(stations);
                                        Collections.reverse(datas);
                                        //하은 소요시간
                                        timeResult = 0;
                                        for (int i = arrive; i < depart; i++) {
                                            timeResult += totalTime[i];
                                        }
                                    } else {
                                        //하은 소요시간
                                        timeResult = 0;
                                        for (int i = depart; i < arrive; i++) {
                                            timeResult += totalTime[i];
                                        }

                                    }


                                    final Intent intent = new Intent(getApplicationContext(), CongestionActivity.class);
                                    intent.putExtra("FORMATTIME", formatTime);
                                    intent.putExtra("DEPART", four[depart]);
                                    intent.putExtra("ARRIVE", four[arrive]);

                                    intent.putExtra("CONGESTION", congestion);
                                    intent.putStringArrayListExtra("DATA", datas);
                                    intent.putStringArrayListExtra("STATION", stations);
                                    dialog.dismiss();


                                    intent.putExtra("timeResult", timeResult);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        });

        //////////////////////////////////////// 태영 시간요일바꾸는버튼
        txtvTime = findViewById(R.id.txtvTime);
        txtvTime.setText(day+" "+formatTime);

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                ModifyFragment dialogFragment = new ModifyFragment();
                dialogFragment.show(fm, "fragment_modify");
            }
        });

        // 예진 플로팅버튼
        btnfabS = findViewById(R.id.btnfabS);

        btnfabS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StationMapAActivity.class);
                intent.putStringArrayListExtra("DepartArrive", DepartArrive);
                //i.putExtra("DEPART", four[depart]);
                startActivity(intent);
            }
        });

    }// end OnCreate();

    //@Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnfabS:
                break;
        }

    } // end onClick();
}