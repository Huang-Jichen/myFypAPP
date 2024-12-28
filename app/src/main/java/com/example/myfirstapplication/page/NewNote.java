package com.example.myfirstapplication.page;

import static com.example.myfirstapplication.Utils.ImageUtils.getBitmapFromImageView;
import static com.example.myfirstapplication.Utils.ImageUtils.saveImageToInternalStorage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapplication.R;
import com.example.myfirstapplication.db.NoteDbHelper;
import com.example.myfirstapplication.pojo.NoteInfo;
import com.example.myfirstapplication.pojo.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NewNote extends AppCompatActivity {

    private TextView Title;
    private TextView MainBody;
    private Button btnPost;
    private ImageView ivNoteImage;
    private static final String EXTRA_IMAGE_URI = "IMAGE_URI";
    NoteInfo myNoteInfo = new NoteInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        //初始化控件
        Title = findViewById(R.id.etNote_title);
        MainBody = findViewById(R.id.etNote);
        ivNoteImage = findViewById(R.id.ivNoteImage);
        btnPost = findViewById(R.id.btnPost);

        //给按钮添加监听
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取用户信息
                UserInfo userInfos = UserInfo.getUserInfos();

                // 获取Title TextView中的文本内容，并设置到myNoteInfo对象的对应属性中
                String titleText = Title.getText().toString().trim();
                myNoteInfo.setTitle(titleText);

                // 获取MainBody TextView中的文本内容，并设置到myNoteInfo对象的对应属性中
                String mainBodyText = MainBody.getText().toString().trim();
                myNoteInfo.setMainBody(mainBodyText);

                if(null != userInfos){
                    myNoteInfo.setUserName(userInfos.getUsername());
                }

                String uuid = UUID.randomUUID().toString();
                // 保存笔记图片
                String imagePath = saveImageToInternalStorage(NewNote.this, getBitmapFromImageView(ivNoteImage), uuid  +".png");
                if (imagePath == null) {
                    Toast.makeText(NewNote.this, "Please add a picture~", Toast.LENGTH_SHORT).show();
                    return;
                }
                myNoteInfo.setImg(imagePath);

                // 检查笔记标题内容是否为空
                if (titleText.isEmpty() || mainBodyText.isEmpty()) {
                    // 如果为空，显示Toast提示
                    Toast.makeText(NewNote.this, "Please set the title and mainbody", Toast.LENGTH_SHORT).show();
                } else {

                    // 如果不为空，执行把笔记添加到数据库逻辑
                    int row = NoteDbHelper.getInstance(NewNote.this).addNote(userInfos.getUsername(),titleText, mainBodyText,imagePath);
                    if (row > 0) {
                        Toast.makeText(NewNote.this, "Post successfully", Toast.LENGTH_SHORT).show();
                    }
                }
                Intent intent = new Intent(NewNote.this, PersonalCenter.class);
                startActivity(intent);
            }
        });

        // 获取传递过来的图片Uri
        String imageUriString = getIntent().getStringExtra(EXTRA_IMAGE_URI);
        Uri imageUri = Uri.parse(imageUriString);
        // 使用图片Uri，例如设置到ImageView
        ivNoteImage.setImageURI(imageUri);
    }
}
