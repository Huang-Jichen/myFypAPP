package com.example.myfirstapplication.page;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myfirstapplication.R;
import com.example.myfirstapplication.db.UserDbHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText username, password;
    private Button registerButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        registerButton = findViewById(R.id.register_button);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 返回登录界面
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 注册逻辑
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();
                // 检查用户名和密码是否为空
                if (usernameText.isEmpty() || passwordText.isEmpty()) {
                    // 如果为空，显示Toast提示
                    Toast.makeText(RegisterActivity.this, "Please set the username and password", Toast.LENGTH_SHORT).show();
                } else {
                    // 检查用户名是否已存在
                    boolean isUsernameExists = UserDbHelper.getInstance(RegisterActivity.this).isUsernameExists(usernameText);
                    if (isUsernameExists) {
                        // 如果用户名已存在，显示Toast提示
                        Toast.makeText(RegisterActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                    }else{
                        // 如果不为空，执行注册逻辑
                        int row = UserDbHelper.getInstance(RegisterActivity.this).register(usernameText,passwordText);
                        if (row > 0){
                            Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                }

            }
        });
    }
}
