package com.assistant.ua.business.ui.blog

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by qinbaoyuan on 2018/11/19
 */
class Test internal constructor(private val context: Context) {

    internal var recyclerView = RecyclerView(context)

    internal fun sam() {
        for (i in 0 until recyclerView.childCount) {

        }
    }
}
