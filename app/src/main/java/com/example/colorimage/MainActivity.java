package com.example.colorimage;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS = 100;
    RecyclerView recyclerView;
    ImageAdapter adapter;
    public ArrayList<Image> arrayList = new ArrayList<>();

    Button btnDelte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnDelte = findViewById(R.id.delete_btn);
        btnDelte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                File fdelete = new File("file:///storage/emulated/0/Pictures/download.jpg");
                File fdelete = new File("file:///storage/emulated/0/Pictures/Screenshots/Screenshot_2019-12-21-00-43-26.png");

                if (fdelete.delete()) {
                    Log.d("001", "onCreate: aaa");
                } else {
                    Log.d("001", "onCreate: bbb");
                }
            }
        });



//
//        ContentResolver contentResolver = getContentResolver();
//        contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                MediaStore.Images.ImageColumns.DATA + "=?", new String[]{"file:///storage/emulated/0/Download/images"});

        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
            Log.e("Else", "Else");
            fn_imagespath();
        }
        arrayList = fn_imagespath();

        adapter = new ImageAdapter(arrayList, MainActivity.this);

        recyclerView = findViewById(R.id.list);

        recyclerView.setHasFixedSize(true);

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);


        recyclerView.setAdapter(adapter);

    }

    public ArrayList<Image> fn_imagespath() {
        ArrayList<Image> images = new ArrayList<>();
        images.clear();

        Uri uri;
        Cursor cursor;

        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            Log.e("Column", absolutePathOfImage);
            Image image = new Image(absolutePathOfImage);
            images.add(image);
        }
        return images;
    }


}
