package com.example.user.swu.likelion;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FiveRoadActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager pager;


    // 혼잡도 DB 불러오기
    final String DATABASE_NAME = "congestion.db";
    final String PACKAGE_DIRECTORY = "/data/data/com.example.user.swu.likelion/";
    Context mContext = this;
    myDBHelper myHelper;
    SQLiteDatabase congestionDB;

    // 배열선언
    // int[] arrayStationNo;

    // 배열 사이즈
    int rowSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_road);

        tabLayout = findViewById(R.id.tabLayout);
        pager = findViewById(R.id.pager);

        boolean bResult = isCheckDatabase();    // DB가 있는지?
        Log.d("Working", "DataBase Check=" + bResult);
        if (!bResult) {  // DB가 없으면 복사
            copyDataBase();
        }

        myHelper = new FiveRoadActivity.myDBHelper(this);
        congestionDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = congestionDB.rawQuery("SELECT time0530 FROM satUp where stationName= '서울역'", null);

        rowSize = cursor.getCount();

        String arrayStationNo[];
        // 배열 할당
        arrayStationNo = new String[rowSize];

        int i = 0;
        while(cursor.moveToNext()){
            // 배열 삽입
            arrayStationNo[i] = cursor.getString(0);
            i++;
        }

        Log.d("dbCheck", arrayStationNo[0]);
        cursor.close();
        congestionDB.close();
    }// endOnCreate

    // DB관련 코드
    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context){
            super(context, "congestion.db", null, 1);
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
