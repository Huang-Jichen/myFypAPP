package com.example.myfirstapplication.page;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapplication.adapter.NoteListAdapter;
import com.example.myfirstapplication.R;
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
    private Button btnPost;
    NoteInfo mynoteInfo;
    //获取用户信息
    UserInfo userInfos = UserInfo.getUserInfos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalcenter);

        tvHome = findViewById(R.id.textViewHome);
        tvMessage = findViewById(R.id.textViewMessage);
        myRecyclerView = findViewById(R.id.recyclerView1);
        headshot = findViewById(R.id.imageView);
        btnPost = findViewById(R.id.btnPost);

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

        //发表笔记按钮绑定监听
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewNote(view);
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
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // 从data中获取传递回来的noteInfo对象并添加到列表等操作
            NoteInfo newNoteInfo = (NoteInfo) data.getSerializableExtra("NoteInfo");
            if (newNoteInfo != null) {
                myNoteInfoList.add(newNoteInfo);
                myNoteListAdapter.notifyDataSetChanged(); // 通知适配器数据改变，刷新列表显示新笔记
            }
        }
    }

}


