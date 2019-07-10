package com.example.user.swu.likelion;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    // 현재시간을 msec 으로 구한다.
    long now = System.currentTimeMillis();
    // 현재시간을 date 변수에 저장한다.
    Date date = new Date(now);
    // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
    SimpleDateFormat sdfNow = new SimpleDateFormat("E,HH:mm");
    // nowDate 변수에 값을 저장한다.
    String formatDate = sdfNow.format(date);

    TextView time;
    //Button Refresh;
    Button btn_0, btn_1,btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_9;
    ImageButton btn_next;
    ImageButton btn_StartStation;
    ImageView imageview = null;
    TextView textview4;
    TextView textView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = (TextView) findViewById(R.id.time);
        time.setText(formatDate);    // TextView 에 현재 시간 문자열 할당
//        Refresh = (Button) findViewById(R.id.Refresh);
//        Refresh.setOnClickListener(this);

        btn_0 = (Button) findViewById(R.id.btn_0); // 위치를 못 찾아서 호선 자리가 비어있을 때, 투명으로 자리 지키는 버튼 (위치를 찾아서 호선 버튼이 출력되면 위치가 달라지기 때문에 필요)
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_6 = (Button) findViewById(R.id.btn_6);
        btn_7 = (Button) findViewById(R.id.btn_7);
        btn_9 = (Button) findViewById(R.id.btn_9);
        btn_next = (ImageButton) findViewById(R.id.btn_next);
        btn_StartStation = (ImageButton) findViewById(R.id.btn_StartStation);
        imageview = (ImageView)findViewById(R.id.imageView);

        textview4 = (TextView)findViewById(R.id.textView4);
        textView5 = (TextView)findViewById(R.id.textView5);

        //1,2,3,4,5,6,7,9 시작
        btn_0.setVisibility(View.VISIBLE);
        btn_1.setVisibility(View.GONE);
        btn_2.setVisibility(View.GONE);
        btn_3.setVisibility(View.GONE);
        btn_4.setVisibility(View.GONE);
        btn_5.setVisibility(View.GONE);
        btn_6.setVisibility(View.GONE);
        btn_7.setVisibility(View.GONE);
        btn_9.setVisibility(View.GONE);

        //1,2,3,4,5,6,7,9 시작

        btn_1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                imageview.setImageResource(R.drawable.simbol_1st);
            };
        });

        btn_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                imageview.setImageResource(R.drawable.simbol_2nd);
            };
        });

        btn_3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                imageview.setImageResource(R.drawable.simbol_3nd);
            };
        });

        btn_4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                imageview.setImageResource(R.drawable.simbol);
            };
        });

        btn_5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                imageview.setImageResource(R.drawable.simbol_5th);
            };
        });

        btn_6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                imageview.setImageResource(R.drawable.simbol_6nd);
            };
        });

        btn_7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                imageview.setImageResource(R.drawable.simbol_7nd);
            };
        });

        btn_9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                imageview.setImageResource(R.drawable.simbol_9th);
            };
        });

        btn_next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                TextView textView4= (TextView)textview4.findViewById(R.id.textView4);

                if (textView4.getText().equals("찾는 중이에요")){
                    Toast.makeText(MainActivity.this, "위치정보를 찾지 못 하였습니다. 출발역을 먼저 설정해주세요", Toast.LENGTH_SHORT).show();
                }else {
                    //Toast.makeText(MainActivity.this, textView4.getText(), Toast.LENGTH_SHORT).show();

                    final ArrayList<String> DepartArrive = new ArrayList<>();
                    DepartArrive.add(textView4.getText().toString());

                    Intent intent = new Intent(getApplicationContext(), SelectActivity.class);
                    intent.putStringArrayListExtra("DepartArrive", DepartArrive);
                    startActivity(intent);
                }
            };
        });

        btn_StartStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Select2Activity.class);
                startActivity(intent);
            }
        });

        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( MainActivity.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );
        }
        else{
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    1000,
                    1,
                    gpsLocationListener);
        }

        //GPS관련 코드

        /* refresh 버튼 삭제함
        Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdfNow = new SimpleDateFormat("E,HH:mm");
                String formatDate = sdfNow.format(date);
                time.setText(formatDate);
            }
        });*/
    }

    //GPS 관련코드
    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            btn_0.setVisibility(View.GONE); //위치 찾으면 투명이였던 버튼 없어지게

            Location locationA = new Location("point 0");
            //현재 위도, 경도
            locationA.setLatitude(latitude);
            locationA.setLongitude(longitude);

            Location location409 = new Location("point 409");
            location409.setLatitude(37.670268);
            location409.setLongitude(127.079051);

            Location location410 = new Location("point 410");
            location410.setLatitude(37.660892);
            location410.setLongitude(127.073622);

            Location location411 = new Location("point 411");
            location411.setLatitude(37.656373);
            location411.setLongitude(127.063386);

            Location location412 = new Location("point 412");
            location412.setLatitude(37.653128);
            location412.setLongitude(127.047744);

            Location location413 = new Location("point 413");
            location413.setLatitude(37.648626);
            location413.setLongitude(127.03474);

            Location location414 = new Location("point 414");
            location414.setLatitude(37.637973);
            location414.setLongitude(127.02575);

            Location location415 = new Location("point 415");
            location415.setLatitude(37.626622);
            location415.setLongitude(127.026029);

            Location location416 = new Location("point 416");
            location416.setLatitude(37.613229);
            location416.setLongitude(127.030127);

            Location location417 = new Location("point 417");
            location417.setLatitude(37.603369);
            location417.setLongitude(127.025063);

            Location location418 = new Location("point 418");
            location418.setLatitude(37.592557);
            location418.setLongitude(127.016394);

            Location location419 = new Location("point 419");
            location419.setLatitude(37.588493);
            location419.setLongitude(127.006266);

            Location location420 = new Location("point 420");
            location420.setLatitude(37.582286);
            location420.setLongitude(127.001846);

            Location location421 = new Location("point 421");
            location421.setLatitude(37.570892);
            location421.setLongitude(127.00927);

            Location location422 = new Location("point 422");
            location422.setLatitude(37.56511);
            location422.setLongitude(127.007779);

            Location location423 = new Location("point 423");
            location423.setLatitude(37.561297);
            location423.setLongitude(126.99451);

            Location location424 = new Location("point 424");
            location424.setLatitude(37.560917);
            location424.setLongitude(126.986412);

            Location location425 = new Location("point 425");
            location425.setLatitude(37.558476);
            location425.setLongitude(126.978328);

            Location location426 = new Location("point 426");
            location426.setLatitude(37.553139);
            location426.setLongitude(126.972653);

            Location location427 = new Location("point 427");
            location427.setLatitude(37.544577);
            location427.setLongitude(126.972106);

            Location location428 = new Location("point 428");
            location428.setLatitude(37.533926);
            location428.setLongitude(126.972513);

            Location location429 = new Location("point 429");
            location429.setLatitude(37.529128);
            location429.setLongitude(126.967986);

            Location location430 = new Location("point 430");
            location430.setLatitude(37.522257);
            location430.setLongitude(126.974713);

            Location location431 = new Location("point 431");
            location431.setLatitude(37.502874);
            location431.setLongitude(126.980356);

            Location location432 = new Location("point 432");
            //총신대입구역 위도, 경도
            location432.setLatitude(37.487322);
            location432.setLongitude(126.982255);

            Location location433 = new Location("point 433");
            //사당역 위도, 경도
            location433.setLatitude(37.477055);
            location433.setLongitude(126.98174);

            Location location434 = new Location("point 434");
            //남태령역 위도, 경도
            location434.setLatitude(37.463907);
            location434.setLongitude(126.989143);

            double distance409, distance410,
                    distance411, distance412, distance413, distance414, distance415, distance416, distance417, distance418, distance419, distance420,
                    distance421, distance422, distance423, distance424, distance425, distance426, distance427, distance428, distance429, distance430,
                    distance431, distance432, distance433, distance434;
            //String meter432, meter433, meter434;


            distance409 = locationA.distanceTo(location409);
            distance410 = locationA.distanceTo(location410);
            distance411 = locationA.distanceTo(location411);
            distance412 = locationA.distanceTo(location412);
            distance413 = locationA.distanceTo(location413);
            distance414 = locationA.distanceTo(location414);
            distance415 = locationA.distanceTo(location415);
            distance416 = locationA.distanceTo(location416);
            distance417 = locationA.distanceTo(location417);
            distance418 = locationA.distanceTo(location418);
            distance419 = locationA.distanceTo(location419);
            distance420 = locationA.distanceTo(location420);
            distance421 = locationA.distanceTo(location421);
            distance422 = locationA.distanceTo(location422);
            distance423 = locationA.distanceTo(location423);
            distance424 = locationA.distanceTo(location424);
            distance425 = locationA.distanceTo(location425);
            distance426 = locationA.distanceTo(location426);
            distance427 = locationA.distanceTo(location427);
            distance428 = locationA.distanceTo(location428);
            distance429 = locationA.distanceTo(location429);
            distance430 = locationA.distanceTo(location430);
            distance431 = locationA.distanceTo(location431);

            distance432 = locationA.distanceTo(location432);
            //meter432 = Double.toString(distance432);

            distance433 = locationA.distanceTo(location433);
            //meter433 = Double.toString(distance433);

            distance434 = locationA.distanceTo(location434);
            //meter434 = Double.toString(distance434);

            String closest, closest2;
            Double closest_distance;

            ArrayList<Double> closest_stn = new ArrayList<>();

            closest_stn.add(distance409);
            closest_stn.add(distance410);
            closest_stn.add(distance411);
            closest_stn.add(distance412);
            closest_stn.add(distance413);
            closest_stn.add(distance414);
            closest_stn.add(distance415);
            closest_stn.add(distance416);
            closest_stn.add(distance417);
            closest_stn.add(distance418);
            closest_stn.add(distance419);
            closest_stn.add(distance420);
            closest_stn.add(distance421);
            closest_stn.add(distance422);
            closest_stn.add(distance423);
            closest_stn.add(distance424);
            closest_stn.add(distance425);
            closest_stn.add(distance426);
            closest_stn.add(distance427);
            closest_stn.add(distance428);
            closest_stn.add(distance429);
            closest_stn.add(distance430);
            closest_stn.add(distance431);
            closest_stn.add(distance432);
            closest_stn.add(distance433);
            closest_stn.add(distance434);
            Collections.sort(closest_stn);
            closest_distance = closest_stn.get(0);

            if(closest_distance == distance409){
                closest = "당고개";
                closest2 = "Danggogae";
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            } else if(closest_distance == distance410){
                closest = "상계";
                closest2 = "Sanggye";
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            } else if(closest_distance == distance411){
                closest = "노원";
                closest2 = "Nowon";
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.VISIBLE);
                btn_9.setVisibility(View.GONE);
            }else if(closest_distance == distance412){
                closest = "창동";
                closest2 = "Changdong";
                btn_1.setVisibility(View.VISIBLE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            }else if(closest_distance == distance413){
                closest = "쌍문";
                closest2 = "Ssangmun";
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            } else if(closest_distance == distance414){
                closest = "수유";
                closest2 = "Suyu";
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            }else if(closest_distance == distance415){
                closest = "미아";
                closest2 = "Mia";
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            } else if(closest_distance == distance416){
                closest = "미아사거리";
                closest2 = "Miasageori";
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            }else if(closest_distance == distance417){
                closest = "길음";
                closest2 = "Gireum";
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            } else if(closest_distance == distance418){
                closest = "성신여대입구";
                closest2 = "Sungshin Women's Univ.";
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            }else if(closest_distance == distance419){
                closest = "한성대입구";
                closest2 = "Hansung Univ.";
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            } else if(closest_distance == distance420){
                closest = "혜화";
                closest2 = "Hyehwa";
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            }else if(closest_distance == distance421){
                closest = "동대문";
                closest2 = "Dongdaemun";
                btn_1.setVisibility(View.VISIBLE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            }else if(closest_distance == distance422){
                closest = "동대문역사문화공원";
                closest2 = "Dongdaemun History & Culture Park";
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.VISIBLE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.VISIBLE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            }else if(closest_distance == distance423){
                closest = "충무로";
                closest2 = "Chungmuro";
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.VISIBLE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            } else if(closest_distance == distance424){
                closest = "명동";
                closest2 = "Myeongdong";
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            }else if(closest_distance == distance425){
                closest = "회현";
                closest2 = "Hoehyeon";
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            } else if(closest_distance == distance426){
                closest = "서울역";
                closest2 = "Seoul Station";
                btn_1.setVisibility(View.VISIBLE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            }else if(closest_distance == distance427){
                closest = "숙대입구";
                closest2 = "Sookmyung Women's Univ.";
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            } else if(closest_distance == distance428){
                closest = "삼각지";
                closest2 = "Samgakji";
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.VISIBLE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            }else if(closest_distance == distance429){
                closest = "신용산";
                closest2 = "Sinyongsan";
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            } else if(closest_distance == distance430){
                closest = "이촌";
                closest2 = "Ichon";
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            }else if(closest_distance == distance431){
                closest = "동작";
                closest2 = "Dongjak";
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.VISIBLE);
            }else if(closest_distance == distance432){
                closest = "총신대입구(이수)";
                closest2 = "Chongshin Univ.";
                btn_4.setVisibility(View.VISIBLE);
                btn_7.setVisibility(View.VISIBLE);
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.VISIBLE);
                btn_9.setVisibility(View.GONE);
            }else if(closest_distance == distance433){
                closest = "사당";
                closest2 = "Sadang";
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.VISIBLE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            } else if(closest_distance == distance434){
                closest = "남태령";
                closest2 = "Namtaeryeong";
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
                btn_3.setVisibility(View.GONE);
                btn_4.setVisibility(View.VISIBLE);
                btn_5.setVisibility(View.GONE);
                btn_6.setVisibility(View.GONE);
                btn_7.setVisibility(View.GONE);
                btn_9.setVisibility(View.GONE);
            } else {
                closest = "초기값";
                closest2 = "오류입니다";
            }

            textview4.setText(closest);
            textView5.setText(closest2);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };
}
