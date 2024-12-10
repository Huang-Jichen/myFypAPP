package com.example.myfirstapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapplication.db.UserDbHelper;
import com.example.myfirstapplication.page.MainActivity;
import com.example.myfirstapplication.pojo.UserInfo;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView notRegisteredText;
    private SharedPreferences mySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        notRegisteredText = findViewById(R.id.not_registered_text);

        //获取SharedPreferences对象
        mySharedPreferences = getSharedPreferences("user",MODE_PRIVATE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });

        //跳转到注册界面
        notRegisteredText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到注册界面
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void performLogin() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        //用SharedPreferences存储当前登录的用户信息
        SharedPreferences.Editor edit = mySharedPreferences.edit();
        edit.putString("username",username);
        //一定要提交
        edit.commit() ;
        // 检查用户名和密码是否为空
        if (username.isEmpty() || password.isEmpty()) {
            // 如果为空，显示Toast提示
            Toast.makeText(LoginActivity.this, "Please put the username and password", Toast.LENGTH_SHORT).show();
        }else{
            // 这里添加登录逻辑，比如验证用户名和密码
            UserInfo login = UserDbHelper.getInstance(LoginActivity.this).login(username);
            //判断用户名是否存在
            if(login != null){
                if (username.equals(login.getUsername()) && password.equals(login.getPassword())) {
                    //保存用户信息
                    UserInfo.setUserInfos(login);
                    //登录成功
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(LoginActivity.this, "Account not registered", Toast.LENGTH_SHORT).show();
            }

        }
    }
}