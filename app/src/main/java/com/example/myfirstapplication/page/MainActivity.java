package com.example.myfirstapplication.page;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapplication.adapter.NoteListAdapter;
import com.example.myfirstapplication.R;
import com.example.myfirstapplication.db.ImagePickerHelper;
import com.example.myfirstapplication.db.NoteDbHelper;
import com.example.myfirstapplication.db.UserDbHelper;
import com.example.myfirstapplication.pojo.NoteInfo;
import com.example.myfirstapplication.pojo.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tvMessage;
    private TextView tvPersonalCenter;
    private RecyclerView myRecyclerView;
    private NoteListAdapter myNoteListAdapter;
    //创建用户集合用于遍历所有用户笔记
    List<UserInfo> UserInfoList = new ArrayList<>();
    //创建集合用于存储数据
    List<NoteInfo> myNoteInfoList = new ArrayList<>();
    private SharedPreferences mySharedPreferences;
    //发表笔记
    ImageView postNewNote;
    private ImagePickerHelper imagePickerHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        //初始化控件
        tvMessage = findViewById(R.id.textViewMessage);
        tvPersonalCenter = findViewById(R.id.textViewPersonalCenter);

        imagePickerHelper = new ImagePickerHelper(this);
        postNewNote = findViewById(R.id.postNewNote);
        postNewNote.setOnClickListener(v -> imagePickerHelper.showChooseImageDialog());

        myRecyclerView = findViewById(R.id.RecyclerView);

        //获取SharedPreferences对象,从中取出mySharedPreferences存储的用户名
        mySharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        String username = mySharedPreferences.getString("username", null);
        UserInfo login = UserDbHelper.getInstance(MainActivity.this).login(username);
        //保存用户信息
        UserInfo.setUserInfos(login);

        //初始化适配器
        myNoteListAdapter = new NoteListAdapter(myNoteInfoList);

        //设置适配器
        myRecyclerView.setAdapter(myNoteListAdapter);

        // 初始化一个总的列表，用于存储所有用户的笔记信息
        List<NoteInfo> allNoteInfos = new ArrayList<>();

        // 遍历所有用户信息
        List<UserInfo> userInfos = UserDbHelper.getInstance(MainActivity.this).queryUserListData();
        for (UserInfo userInfo : userInfos) {
            // 获取每个用户的笔记数据
            List<NoteInfo> noteInfos = NoteDbHelper.getInstance(MainActivity.this).queryNoteListData(userInfo.getUsername());
            // 将每个用户的笔记数据添加到总列表中
            allNoteInfos.addAll(noteInfos);
        }

        // 设置适配器数据为所有用户的笔记信息
        myNoteListAdapter.setNoteListAdapter(allNoteInfos);

        //recyclerView点击事件
        myNoteListAdapter.setMyOnItemClickListener(new NoteListAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(NoteInfo NoteInfo) {
                //跳转到详情页
                Intent intent = new Intent(MainActivity.this, NoteDetailActivity.class);
                //跳转传递值，直接传递对象
                //传递的对象要实现Serializable接口
                intent.putExtra("NoteInfo",NoteInfo);

                startActivity(intent);
            }
        });

        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Message.class);
                startActivity(intent);
            }
        });

        tvPersonalCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PersonalCenter.class);
                startActivity(intent);
            }
        });

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