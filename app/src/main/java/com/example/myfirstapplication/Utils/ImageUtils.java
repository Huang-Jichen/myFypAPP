package com.example.myfirstapplication.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    Context context;
    // REQUIRED_WIDTH 和 REQUIRED_HEIGHT 的值
    int REQUIRED_WIDTH = dpToPx(100);
    int REQUIRED_HEIGHT = dpToPx(100);

    public ImageUtils(Context context) {
        this.context = context;
    }

    //将图片转化为字节
    public byte[] getPhotoBytes(String photoPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, options);
        options.inSampleSize = calculateInSampleSize(options, REQUIRED_WIDTH, REQUIRED_HEIGHT);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }

    /**
     * Calculate an inSampleSize for use in a {@link BitmapFactory.Options} object when decoding
     * bitmaps using the decode* methods taking a bitmap factory options object.
     *
     * @param options   The options to modify in place to set the inSampleSize.
     * @param reqWidth  The requested width of the resulting bitmap.
     * @param reqHeight The requested height of the resulting bitmap.
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    // 将dp单位转换为像素
    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,context.getResources().getDisplayMetrics());
    }

    public static String saveImageToInternalStorage(Context context, Bitmap bitmap, String fileName) {
        File imagesDir = new File(context.getFilesDir(), "images");
        if (!imagesDir.exists()) {
            imagesDir.mkdirs();
        }
        File imageFile = new File(imagesDir, fileName);
        try {
            FileOutputStream out = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile.getAbsolutePath();
    }

    public static Bitmap getBitmapFromImageView(ImageView imageView) {
        // 获取Drawable
        Drawable drawable = imageView.getDrawable();

        // 检查Drawable是否是BitmapDrawable的实例
        if (drawable instanceof BitmapDrawable) {
            // 从BitmapDrawable中获取Bitmap
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            // 如果Drawable不是BitmapDrawable的实例，返回null或者抛出异常
            return null;
        }
    }

}
