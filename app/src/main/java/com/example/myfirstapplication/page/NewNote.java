package com.example.myfirstapplication.page;

import static com.example.myfirstapplication.Utils.ImageUtils.getBitmapFromImageView;
import static com.example.myfirstapplication.Utils.ImageUtils.saveImageToInternalStorage;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.myfirstapplication.R;
import com.example.myfirstapplication.RegisterActivity;
import com.example.myfirstapplication.db.NoteDbHelper;
import com.example.myfirstapplication.db.UserDbHelper;
import com.example.myfirstapplication.pojo.NoteInfo;
import com.example.myfirstapplication.pojo.UserInfo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class NewNote extends AppCompatActivity {

    private TextView Title;
    private TextView MainBody;
    private ImageView photo;
    private Button btnPost;
    private Button btnChooseImage;
    private ImageView ivNoteImage;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PICK_IMAGE = 2;
    private Uri imageUri;
    //创建集合用于存储数据
    List<NoteInfo> myNoteInfoList = new ArrayList<>();
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
        btnChooseImage = findViewById(R.id.btnChooseImage);

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

                // 检查用户名和密码是否为空
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
                finish();
            }
        });

        //为选择照片按钮绑定监听
        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseImageDialog();
            }
        });

    }

    //照片按钮监听事件
    private void showChooseImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择图片来源");
        builder.setMessage("选择图片来源");
        builder.setPositiveButton("拍照", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dispatchTakePictureIntent();
            }
        });
        builder.setNegativeButton("相册", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pickImageFromGallery();
            }
        });
        builder.setNeutralButton("取消", null);
        builder.show();
    }

    //从相册中选择照片
    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_PICK_IMAGE);
    }

    //处理图片选择返回的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ivNoteImage.setImageBitmap(imageBitmap);
            } else if (requestCode == REQUEST_PICK_IMAGE && data != null) {
                Uri selectedImage = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    ivNoteImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager())!= null) {
            File photoFile = null;
            try {
                photoFile = createImageFile(); // 自定义方法用于创建保存图片的文件，同时返回对应的File对象
            } catch (IOException ex) {
                // 处理文件创建异常
                ex.printStackTrace();
            }
            if (photoFile!= null) {
                imageUri = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // 为照片创建一个唯一的文件名，格式可以根据需求调整，这里采用了当前时间戳的方式来尽量保证唯一性
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        // 如果外部存储的Pictures目录不存在，则尝试创建它
        if (storageDir!= null &&!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                Log.e("CreateImageFile", "Failed to create directory");
                return null;
            }
        }
        // 创建对应的文件对象
        File imageFile = File.createTempFile(
                imageFileName,  /* 文件名前缀 */
                ".jpg",         /* 文件名后缀 */
                storageDir      /* 文件所在目录 */
        );
        return imageFile;
    }

}
