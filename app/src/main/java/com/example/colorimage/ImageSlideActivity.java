package com.example.colorimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

public class ImageSlideActivity extends AppCompatActivity {

    public ArrayList<Image> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slide);

        arrayList = fn_imagespath();


        ViewPager imageSlider = findViewById(R.id.imageSlider);
        ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(arrayList);
        imageSlider.setAdapter(imageSliderAdapter);
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
