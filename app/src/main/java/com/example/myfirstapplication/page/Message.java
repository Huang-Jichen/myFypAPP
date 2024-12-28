package com.example.myfirstapplication.page;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapplication.R;
import com.example.myfirstapplication.db.ImagePickerHelper;

public class Message extends AppCompatActivity {
    TextView tvHome;
    TextView tvPersonalCenter;
    ImageView postNewNote;
    private ImagePickerHelper imagePickerHelper;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);

        tvHome = findViewById(R.id.textViewHome);
        tvPersonalCenter = findViewById(R.id.textViewPersonalCenter);
        imagePickerHelper = new ImagePickerHelper(this);
        postNewNote = findViewById(R.id.postNewNote);
        postNewNote.setOnClickListener(v -> imagePickerHelper.showChooseImageDialog());

        tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Message.this,MainActivity.class);
                startActivity(intent);
            }
        });

        tvPersonalCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Message.this,PersonalCenter.class);
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