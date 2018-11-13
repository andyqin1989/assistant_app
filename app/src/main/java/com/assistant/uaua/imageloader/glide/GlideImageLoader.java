package com.assistant.uaua.imageloader.glide;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

/**
 * Created by qinbaoyuan on 2018/11/13
 */
public class GlideImageLoader {
    public static void loadImage(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .into(view);
    }
}
