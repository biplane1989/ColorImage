package com.example.colorimage;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.ArrayList;

public class ImageSliderAdapter extends PagerAdapter {

    //    private String[] urls;
    private ArrayList<Image> urls;

    public ImageSliderAdapter(ArrayList<Image> urls) {
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container,  int position) {
        // Tạo ImageView, container chính là ViewPager của chúng ta
        final Context context = container.getContext();
        final AppCompatImageView imageView = new AppCompatImageView(context);
        container.addView(imageView);

//        final AppCompatButton deleteBtn = new AppCompatButton(context);
//        container.addView(deleteBtn);

        // Load ảnh vào ImageView bằng Glide
        //  Glide.with(context).load(urls.get(position)).into(imageView);

        Glide.with(context).load("file://" + urls.get(position).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);
        Log.d("001", "file://" + urls.get(position).getUrl());
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Log.d("001", "file://" + urls.get(0).getUrl());

                return true;
            }
        });
        // Return
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // container chính là ViewPager, còn Object chính là return của instantiateItem ứng với position
        container.removeView((View) object);
    }


}
