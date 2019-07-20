package com.example.user.swu.likelion;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FiveRoadActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_road);

        tabLayout = findViewById(R.id.tabLayout);
        pager = findViewById(R.id.pager);
    }
}
