package com.assistant.ua.imageloader.glide

import android.content.Context
import android.widget.ImageView
import com.assistant.ua.R
import com.assistant.ua.imageloader.ILoader
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/** Created by qinbaoyuan on 2018/11/13
 */
class GlideLoader : ILoader {
    override fun init(context: Context) {
    }

    companion object {
        fun loadPortraitImage(context: Context, url: String?, imageView: ImageView) {
            Glide.with(context)
                .applyDefaultRequestOptions(glidePortraitOptions())
                .load(url)
                .into(imageView)

        }


        private fun glidePortraitOptions(): RequestOptions {
            return RequestOptions()
                .placeholder(R.drawable.icon_portrait_default)
                .error(R.drawable.icon_portrait_default)
                .fallback(R.drawable.icon_portrait_default)
        }
    }
}