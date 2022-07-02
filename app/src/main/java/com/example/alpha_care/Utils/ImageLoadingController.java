package com.example.alpha_care.Utils;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageLoadingController {

    public ImageLoadingController(){}
    public static void loadImageByUrlToImageView(Activity activity, ImageView imageView, String ImageUrl){
        Glide
                .with(activity)
                .load(ImageUrl)
                .centerCrop()
                .into(imageView);
    }
}
