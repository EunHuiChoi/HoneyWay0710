package com.example.user.swu.likelion;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

    // 위도경도 DB관련
    final String DATABASE_NAME = "gpsLocation.db";
    final String PACKAGE_DIRECTORY = "/data/data/com.example.user.swu.likelion/";
    Context mContext = this;
    myDBHelper myHelper;
    SQLiteDatabase locationDB;

    int[] arrayStationNo;
    String[] arrayStationName;
    int[] arrayLineNum;
    double[] arrayLatitude;
    double[] arrayLongtitude ;
    int rowSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean bResult = isCheckDatabase();    // DB가 있는지?
        Log.d("Working", "DataBase Check=" + bResult);
        if (!bResult) {  // DB가 없으면 복사
            copyDataBase();
        }

        myHelper = new myDBHelper(this);
        locationDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = locationDB.rawQuery("SELECT * FROM gps", null);

        rowSize = cursor.getCount();
        arrayStationNo = new int[rowSize];
        arrayStationName = new String[rowSize];
        arrayLineNum = new int[rowSize];
        arrayLatitude = new double[rowSize];
        arrayLongtitude = new double[rowSize];

        int i = 0;
        while(cursor.moveToNext()){
            arrayStationNo[i] = cursor.getInt(0);
            arrayStationName[i] = cursor.getString(1);
            arrayLineNum[i] = cursor.getInt(2);
            arrayLatitude[i] = cursor.getDouble(3);
            arrayLongtitude[i] = cursor.getDouble(4);
            i++;
        }

        cursor.close();
        locationDB.close();

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

            Location locationA = new Location("point");
            //현재 위도, 경도
            locationA.setLatitude(latitude);
            locationA.setLongitude(longitude);

            Location[] stationLoc = new Location[rowSize];
            for(int i = 0; i < rowSize; i++){
                stationLoc[i] = new Location("point" + arrayStationNo[i]);
                stationLoc[i].setLatitude(arrayLatitude[i]);
                stationLoc[i].setLongitude(arrayLongtitude[i]);
            }

            double[] distance = new double[rowSize];
            for(int i = 0; i < rowSize; i++){
                distance[i] = locationA.distanceTo(stationLoc[i]);
            }

            String closest = "", closest2 = "";
            double closest_distance = distance[0];

            // distance배열중 가장 작은 걸 찾아서 closest distance에 넣는다.
            for(int i = 0; i < rowSize; i++){
                if (closest_distance > distance[i]){
                    closest_distance = distance[i];
                }
            }


//            double[] closest_stn = new double[rowSize];
//            for(int i = 0; i < rowSize; i++){
//                closest_stn[i] = distance[i];
//            }
//
//            Arrays.sort(distance);
//            closest_distance = distance[0];

            for(int i = 0; i < rowSize; i++){
                if(closest_distance == distance[i]){
                    closest = arrayStationName[i];
                    closest2 = "StationEnglishName";
                    btn_1.setVisibility(View.GONE);
                    btn_2.setVisibility(View.GONE);
                    btn_3.setVisibility(View.GONE);
                    btn_4.setVisibility(View.VISIBLE);
                    btn_5.setVisibility(View.GONE);
                    btn_6.setVisibility(View.GONE);
                    btn_7.setVisibility(View.GONE);
                    btn_9.setVisibility(View.GONE);
                }
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

    // DB관련 코드
    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context){
            super(context, "gpsLocation.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public boolean isCheckDatabase() {
        String filePath = PACKAGE_DIRECTORY + "/databases/" + DATABASE_NAME;
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    public void copyDataBase(){
        Log.d("Working", "copyDatabase");
        AssetManager manager = mContext.getAssets();
        String folderPath = PACKAGE_DIRECTORY + "/databases";
        String filePath = PACKAGE_DIRECTORY + "/databases/" + DATABASE_NAME;
        File folder = new File(folderPath);
        File file = new File(filePath);
        FileOutputStream fileOut = null;
        BufferedOutputStream bufferOut = null;
        try {
            InputStream inputStr = manager.open(DATABASE_NAME);
            BufferedInputStream bufferStr = new BufferedInputStream(inputStr);
            if (folder.exists()) {

            }else{
                folder.mkdirs();
            }
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }

            fileOut = new FileOutputStream(file);
            bufferOut = new BufferedOutputStream(fileOut);
            int read = -1;
            byte[] buffer = new byte[1024];
            while ((read = bufferStr.read(buffer, 0, 1024)) != -1) {
                bufferOut.write(buffer, 0, read);
            }

            bufferOut.flush();
            bufferOut.close();
            fileOut.close();
            bufferStr.close();
            inputStr.close();
        } catch (IOException e) {
            Log.e("Error : ", e.getMessage());
        }
    }
}
