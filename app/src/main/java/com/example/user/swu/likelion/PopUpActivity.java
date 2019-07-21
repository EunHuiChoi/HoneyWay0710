package com.example.user.swu.likelion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class PopUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        Intent intent = getIntent();
        String depart = intent.getStringExtra("DEPART");
        String time = intent.getStringExtra("TIME");

        TextView txt = findViewById(R.id.txt);
        txt.setText(depart+" "+time);

        RadioButton radio1 = findViewById(R.id.radio1);
        RadioButton radio2 = findViewById(R.id.radio2);
        RadioButton radio3 = findViewById(R.id.radio3);
        RadioButton radio4 = findViewById(R.id.radio4);
        RadioButton radio5 = findViewById(R.id.radio5);

        Button submit = findViewById(R.id.submit);
        Button close = findViewById(R.id.close);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PopUpActivity.this, "피드백 제출 완료", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
