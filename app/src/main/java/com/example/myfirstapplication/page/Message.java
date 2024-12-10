package com.example.myfirstapplication.page;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapplication.R;

public class Message extends AppCompatActivity {
    TextView tvHome;
    TextView tvPersonalCenter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        tvHome = findViewById(R.id.textViewHome);
        tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Message.this,MainActivity.class);
                startActivity(intent);
            }
        });
        tvPersonalCenter = findViewById(R.id.textViewPersonalCenter);
        tvPersonalCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Message.this,PersonalCenter.class);
                startActivity(intent);
            }
        });


    }
}