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

        //切换到主页界面
        tvHome = findViewById(R.id.textViewHome);
        tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalCenter.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //切换到消息界面
        tvMessage = findViewById(R.id.textViewMessage);
        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalCenter.this,Message.class);
                startActivity(intent);
            }
        });

        //发表笔记按钮绑定监听
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

        recyclerView = findViewById(R.id.recyclerView1);
        notesAdapter = new NotesAdapter(NoteManager.getInstance().getNotes());
        recyclerView.setAdapter(notesAdapter);

        displayNotes();
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
                if (!NoteManager.getInstance().containsNote(newNoteContent)) {
                    NoteManager.getInstance().addLatestNote(newNoteContent);
                    notesAdapter.updateNotes(NoteManager.getInstance().getNotes());
                }
                // 更新tvLatestNote显示所有笔记
                displayNotes();
            }
        }
    }

    //更新UI

    @Override
    protected void onResume() {
        super.onResume();
    }

    //展示笔记函数，在tv框内
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


