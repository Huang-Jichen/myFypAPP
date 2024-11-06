package com.example.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView tvMessage;
    TextView tvPersonalCenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.home);
        tvMessage = findViewById(R.id.textViewMessage);
        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Message.class);
                startActivity(intent);
            }
        });
        tvPersonalCenter = findViewById(R.id.textViewPersonalCenter);
        tvPersonalCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PersonalCenter.class);
                startActivity(intent);
            }
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
/*        //绑定监听
        //创建按钮对象
        Button btnLogin = findViewById(R.id.button2);
        //绑定监听事件
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etUserName = findViewById(R.id.editTextText);
                String name = etUserName.getText().toString();
                EditText etUserPassword = findViewById(R.id.editTextTextPassword);
                String password = etUserPassword.getText().toString();
                Log.i("TouchEvent", "UserName:" + name);
                Log.i("TouchEvent", "UserPassword:" + password);
            }*/;
    });
    }
}