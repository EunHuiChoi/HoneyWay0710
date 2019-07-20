package com.example.user.swu.likelion;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
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
import java.io.Serializable;
import java.sql.Struct;

public class FiveRoadActivity extends AppCompatActivity implements Serializable {

    private TabLayout tabLayout;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_road);

        tabLayout = findViewById(R.id.tabLayout);
        pager = findViewById(R.id.pager);

        PagerAdapter adapter = new com.example.user.swu.likelion.adapter.PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        structInfo[] infos;

        Intent intent = getIntent();
        Object[] objects = (Object[])intent.getSerializableExtra("INFOS");

        //infos = new structInfo[objects.length];
       // for(int i=0;i<objects.length;i++){
           // infos[i] = (structInfo)objects[(objects.length-1-i)];
       // }
       // for(int i=0;i<infos.length;i++){
        //    infos[i].print();
       // }
    }// endOnCreate
}