package com.example.myfirstapplication.page;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myfirstapplication.R;
import com.example.myfirstapplication.pojo.NoteInfo;
import com.example.myfirstapplication.pojo.UserInfo;

public class NoteDetailActivity extends AppCompatActivity {

    ImageView image;
    TextView Title;
    TextView MainBody;
    TextView UpdateTime;
    NoteInfo mynoteInfo;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notedetail);

        //获取数据
        mynoteInfo = (NoteInfo) getIntent().getSerializableExtra("NoteInfo");

        //初始化控件
        image = findViewById(R.id.image);
        Title = findViewById(R.id.Title);
        MainBody = findViewById(R.id.MainBody);
        UpdateTime = findViewById(R.id.EditTime);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //设置数据
        Title.setText(mynoteInfo.getTitle());
        MainBody.setText(mynoteInfo.getMainBody());

        String noteImg_path = mynoteInfo.getImg();
        Bitmap bitmap = BitmapFactory.decodeFile(noteImg_path);
        image.setImageBitmap(bitmap);

        //获取用户信息
        UserInfo userInfos = UserInfo.getUserInfos();
        toolbar.setTitle(userInfos.getUsername());

    }


}
