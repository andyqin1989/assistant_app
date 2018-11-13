package com.assistant.uaua.imageloader.glide

import android.content.Context
import android.widget.ImageView
import com.assistant.uaua.imageloader.ILoader
import com.bumptech.glide.Glide

/** Created by qinbaoyuan on 2018/11/13
 */
class GlideLoader : ILoader {
    override fun init(context: Context) {
    }

    companion object {
        fun loadImage(context: Context, url: String?, imageView: ImageView) {
            Glide.with(context)
                .load(url)
                .into(imageView)
        }
    }
}