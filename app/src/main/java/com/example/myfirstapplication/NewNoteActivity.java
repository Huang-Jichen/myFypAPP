package com.example.myfirstapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewNoteActivity extends AppCompatActivity {

        private static final int REQUEST_IMAGE_CAPTURE = 1;
        private static final int REQUEST_PICK_IMAGE = 2;
        private EditText etNote;
        private ImageView ivNoteImage;
        private Button btnChooseImage;
        private Button btnPost;
        private Uri imageUri;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_new_note);

            etNote = findViewById(R.id.etNote);
            ivNoteImage = findViewById(R.id.ivNoteImage);
            btnChooseImage = findViewById(R.id.btnChooseImage);
            btnPost = findViewById(R.id.btnPost);

            btnChooseImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showChooseImageDialog();
                }
            });

            btnPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String noteContent = etNote.getText().toString().trim();
                    if (!noteContent.isEmpty()) {
                        // 模拟发表笔记，实际开发中应保存到数据库或服务器
                        postNoteAndReturn(noteContent);
                    }

                }
            });


        }

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

        private void dispatchTakePictureIntent() {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                imageUri = getOutputMediaFileUri(REQUEST_IMAGE_CAPTURE);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

        private void pickImageFromGallery() {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_PICK_IMAGE);
        }

    private Uri getOutputMediaFileUri(int type) {
        // 检查外部存储是否可用
        if (!isExternalStorageAvailable()) {
            // 如果外部存储不可用，返回null或抛出异常
            return null;
        }

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "YourAppName");

        // 确保目录存在或创建它
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            return null;
        }

        // 创建一个唯一的文件名
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else {
            // 如果type不是1，你可以添加其他类型的处理逻辑
            return null;
        }

        // 返回文件的Uri
        return Uri.fromFile(mediaFile);
    }

    private boolean isExternalStorageAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bitmap image = BitmapFactory.decodeFile(imageUri.getPath());
                ivNoteImage.setImageBitmap(image);
                ivNoteImage.setVisibility(View.VISIBLE);
            } else if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                Bitmap image = getBitmapFromUri(selectedImage);
                ivNoteImage.setImageBitmap(image);
                ivNoteImage.setVisibility(View.VISIBLE);
            }
        }

        private Bitmap getBitmapFromUri(Uri uri) {
            // 检查uri是否为空
            if (uri == null) return null;



            // 检查读权限（如果需要）
            /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 请求权限的代码（如果需要）
                return null;
            }*/

            // 尝试从Uri获取Bitmap
            try {
                InputStream inputStream;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    // 对于Android 9（Pie）及以上版本，使用此方法打开InputStream
                    inputStream = getContentResolver().openInputStream(uri);
                } else {
                    // 对于旧版本，使用此方法打开InputStream
                    inputStream = getContentResolver().openInputStream(uri);
                }

                // 从InputStream中获取Bitmap
                return BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private void postNote() {
            String note = etNote.getText().toString().trim();
            if (note.isEmpty()) {
                Toast.makeText(this, "笔记内容不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            // 模拟发表笔记
            simulatePostNote(note, imageUri);

            // 清空编辑框和图片预览
            etNote.setText("");
            ivNoteImage.setVisibility(View.GONE);
        }

        private void simulatePostNote(String noteText, Uri imageUri) {
            // 实现方法，模拟发表笔记
            // ...
        }
    private void postNoteAndReturn(String noteContent) {
        // 模拟发表笔记成功后，返回个人中心并传递笔记内容
        Intent intent = new Intent();
        intent.putExtra("NEW_NOTE_CONTENT", noteContent);
        NoteManager.getInstance().addLatestNote(noteContent); // 添加笔记到列表,保存为全局变量
        setResult(RESULT_OK, intent);
        finish();
    }



    }

