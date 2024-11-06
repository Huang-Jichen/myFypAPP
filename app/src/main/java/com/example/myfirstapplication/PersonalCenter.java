package com.example.myfirstapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PersonalCenter extends AppCompatActivity {
    TextView tvHome;
    TextView tvMessage;
    TextView tvLatestNote;

    //发表笔记
    private Button btnPost;

    private RecyclerView recyclerView;
    private NotesAdapter notesAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalcenter);

        tvHome = findViewById(R.id.textViewHome);
        tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalCenter.this,MainActivity.class);
                startActivity(intent);
            }
        });

        tvMessage = findViewById(R.id.textViewMessage);
        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalCenter.this,Message.class);
                startActivity(intent);
            }
        });

        btnPost = findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewNote(view);
                /*Intent intent = new Intent(PersonalCenter.this,NewNoteActivity.class);
                startActivity(intent);*/
            }
        });

        tvLatestNote = findViewById(R.id.tvLatestNote);
        displayNotes();

        recyclerView = findViewById(R.id.recyclerView1);
        notesAdapter = new NotesAdapter(NoteManager.getInstance().getNotes());
        recyclerView.setAdapter(notesAdapter);
    }

    // 启动NewNoteActivity并处理返回结果
    public void addNewNote(View view) {
        Intent intent = new Intent(this, NewNoteActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String newNoteContent = data.getStringExtra("NEW_NOTE_CONTENT");
            if (newNoteContent != null) {
                tvLatestNote.setText(newNoteContent);
                tvLatestNote.setVisibility(View.VISIBLE);
            }
        }
    }

    private void displayNotes() {
        List<String> notes = NoteManager.getInstance().getNotes();
        // 如果您使用 TextView 显示所有笔记
        StringBuilder sb = new StringBuilder();
        for (String note : notes) {
            sb.append(note).append("\n\n");
        }
        tvLatestNote.setText(sb.toString());
        tvLatestNote.setVisibility(View.VISIBLE);
    }



}

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add(1,1,1,"发现好友");
        menu.add(1,2,2,"发表动态");
        menu.add(1,3,3,"设置");
        menu.setGroupCheckable(1,true,false);
        menu.setGroupVisible(1,true);
        return super.onCreateOptionsMenu(menu);
    }*/


