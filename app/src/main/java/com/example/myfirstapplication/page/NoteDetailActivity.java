package com.example.myfirstapplication.page;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myfirstapplication.R;
import com.example.myfirstapplication.db.NoteDbHelper;
import com.example.myfirstapplication.pojo.NoteInfo;
import com.example.myfirstapplication.pojo.UserInfo;

public class NoteDetailActivity extends AppCompatActivity {

    ImageView image;
    TextView Title;
    TextView MainBody;
    TextView UpdateTime;
    NoteInfo mynoteInfo;
    private Toolbar toolbar;
    private int noteId;
    //获取用户信息
    UserInfo userInfos = UserInfo.getUserInfos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notedetail);

        //获取数据
        mynoteInfo = (NoteInfo) getIntent().getSerializableExtra("NoteInfo");
        noteId = mynoteInfo.getId();

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

        UpdateTime.setText(mynoteInfo.getUpdatedAt());

        toolbar.setTitle(mynoteInfo.getUserName());

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (mynoteInfo.getUserName().equals(userInfos.getUsername())) {
            // 加载菜单资源文件
            getMenuInflater().inflate(R.menu.baseline_menu_24, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            //删除笔记
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure to delete this note?");
            builder.setPositiveButton("no", null);
            builder.setNegativeButton("yes", (dialog, which) -> deleteNote());
            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteNote() {
        NoteDbHelper.getInstance(NoteDetailActivity.this).deleteNote(noteId);
        Toast.makeText(NoteDetailActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(NoteDetailActivity.this,PersonalCenter.class);
        startActivity(intent);
    }

}
