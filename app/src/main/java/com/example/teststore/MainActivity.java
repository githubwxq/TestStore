package com.example.teststore;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    TextView tv_create_file;
    TextView tv_find_file;
    TextView tv_find_pic;
    TextView tv_insert_pic;
    ImageView iv_pic;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_create_file = findViewById(R.id.tv_create_file);
        tv_find_file = findViewById(R.id.tv_find_file);
        tv_find_pic = findViewById(R.id.tv_find_pic);
        tv_insert_pic = findViewById(R.id.tv_insert_pic);
        iv_pic = findViewById(R.id.iv_pic);

        checkPermission(this);

        tv_create_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertFile();
            }
        });

        tv_find_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findFile();
            }
        });
        tv_insert_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertImage();
            }
        });
        tv_find_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchImage();
            }
        });
    }

    /**
     * 查找文件文件路径
     */
    private void findFile() {

//        Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

    }









    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void insertOwnerFile() {

//在自身目录下创建apk文件夹
        File apkFile = getExternalFilesDir("apk");

        String apkFilePath = getExternalFilesDir("apk").getAbsolutePath();
        File newFile = new File(apkFilePath + File.separator + "temp.text");
        OutputStream os = null;
        try {
            os = new FileOutputStream(newFile);
            if (os != null) {
                os.write("file is created111111111111111111111111".getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            Toast.makeText(MainActivity.this, "創建陳工" + newFile.getAbsolutePath(), Toast.LENGTH_LONG).show();

        } catch (IOException e) {
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e1) {

            }
        }


    }

    private void searchImage() {

        Uri extnerl = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String selection = MediaStore.Images.Media.DISPLAY_NAME + "=?";

        String[] args = new String[]{"111.jpg"};
        String[] projections = new String[]{MediaStore.Images.Media._ID};


        Cursor cursor = getContentResolver().query(extnerl, projections, selection, args, null);

        if (cursor.moveToFirst()) {
            Uri queryUir = ContentUris.withAppendedId(extnerl, cursor.getLong(0));
            Toast.makeText(MainActivity.this, "查询success" + queryUir, Toast.LENGTH_LONG).show();
            cursor.close();
            // 给图片设置bitmap
            try {
                    ParcelFileDescriptor fd = getContentResolver().openFileDescriptor(queryUir, "r");
                    if (fd != null) {
                        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fd.getFileDescriptor());
                        fd.close();
                        iv_pic.setImageBitmap(bitmap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }



        }


    }

    private void insertFile() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = MediaStore.Files.getContentUri("external");

        ContentValues values = new ContentValues();
        String path = Environment.DIRECTORY_DOWNLOADS + "/bug";
        values.put(MediaStore.Downloads.RELATIVE_PATH, path);

        values.put(MediaStore.Downloads.DISPLAY_NAME, "first_bug.text");
        values.put(MediaStore.Downloads.TITLE, path);
        Uri resultUri = contentResolver.insert(uri, values);

        if (resultUri != null) {
            Toast.makeText(MainActivity.this, "创建成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "创建失败", Toast.LENGTH_LONG).show();
        }


        try {
            OutputStream outputStream = contentResolver.openOutputStream(resultUri);

            //将字符串转成字节
            byte[] contentInBytes = "first_bugfirst_bugfafasfaaaaaaaaaaaaaaafirst_bugaaa".toString().getBytes();

            outputStream.write(contentInBytes);
            outputStream.flush();

            outputStream.close();
            Toast.makeText(MainActivity.this, "写入陈宫", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


        File file = new File(path);
        if (file.exists()) {
            if (file.isDirectory()) {
                Toast.makeText(MainActivity.this, "写入陈宫", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "写入陈宫", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "写入陈宫", Toast.LENGTH_LONG).show();
        }


    }

    private void insertImage() {
        String disPlayName = "111.jpg";
        ContentResolver contentResolver1 = getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, disPlayName);
        contentValues.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/jpeg");
        contentValues.put(MediaStore.Images.ImageColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/wxq/");
        Uri insert = contentResolver1.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        if (insert != null) {
            Bitmap bitmap = getBitmap(this, R.mipmap.ic_launcher);
            try {
                OutputStream outputStream = contentResolver1.openOutputStream(insert);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.close();
                Toast.makeText(MainActivity.this, "插入图片成功", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    private static Bitmap getBitmap(Context context, int vectorDrawableId) {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermission(MainActivity mainActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mainActivity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        File file = new File(getFilesDir().getPath() + File.separator, "AAAAAAAAAAA.txt");
        File file2 = new File(Environment.getExternalStorageState() + File.separator, "ccccccc.txt");
        try {
            file.createNewFile();
//            file2.createNewFile();
            Toast.makeText(this, "创建成功", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "创建失败", Toast.LENGTH_LONG).show();
        }

    }
}