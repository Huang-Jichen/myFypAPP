package com.example.myfirstapplication.page;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapplication.adapter.NoteListAdapter;
import com.example.myfirstapplication.R;
import com.example.myfirstapplication.db.ImagePickerHelper;
import com.example.myfirstapplication.db.NoteDbHelper;
import com.example.myfirstapplication.pojo.NoteInfo;
import com.example.myfirstapplication.pojo.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class PersonalCenter extends AppCompatActivity {
    TextView tvHome;
    TextView tvMessage;
    RecyclerView myRecyclerView;
    NoteListAdapter myNoteListAdapter;
    //创建集合用于存储数据
    List<NoteInfo> myNoteInfoList = new ArrayList<>();
    private ImageView headshot;

    //发表笔记
    ImageView postNewNote;
    private ImagePickerHelper imagePickerHelper;

    //获取用户信息
    UserInfo userInfos = UserInfo.getUserInfos();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalcenter);

        tvHome = findViewById(R.id.textViewHome);
        tvMessage = findViewById(R.id.textViewMessage);
        myRecyclerView = findViewById(R.id.recyclerView1);
        headshot = findViewById(R.id.imageView);
        imagePickerHelper = new ImagePickerHelper(this);
        postNewNote = findViewById(R.id.postNewNote);
        postNewNote.setOnClickListener(v -> imagePickerHelper.showChooseImageDialog());

        //初始化适配器
        myNoteListAdapter = new NoteListAdapter(myNoteInfoList);

        //设置适配器
        myRecyclerView.setAdapter(myNoteListAdapter);

        //获取数据
        List<NoteInfo> noteInfos = NoteDbHelper.getInstance(PersonalCenter.this).queryNoteListData(userInfos.getUsername());
        //设置数据
        myNoteListAdapter.setNoteListAdapter(noteInfos);

        //recyclerView点击事件
        myNoteListAdapter.setMyOnItemClickListener(new NoteListAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(NoteInfo NoteInfo) {
                //跳转到详情页
                Intent intent = new Intent(PersonalCenter.this, NoteDetailActivity.class);
                //跳转传递值，直接传递对象
                //传递的对象要实现Serializable接口
                intent.putExtra("NoteInfo", NoteInfo);
                startActivity(intent);
            }
        });

        //切换到主页界面
        tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalCenter.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //切换到消息界面

        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalCenter.this, Message.class);
                startActivity(intent);
            }
        });

        //选择头像
    }

    // 启动NewNoteActivity并处理返回结果
    public void addNewNote(View view) {
        Intent intent = new Intent(this, NewNote.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ImagePickerHelper.REQUEST_PICK_IMAGE && data != null) {
                Uri selectedImage = data.getData();
                // 跳转到新页面，并传递图片Uri
                Intent intent = new Intent(this, NewNote.class);
                intent.putExtra("IMAGE_URI", selectedImage.toString());
                startActivity(intent);
            }
        }
    }

}


