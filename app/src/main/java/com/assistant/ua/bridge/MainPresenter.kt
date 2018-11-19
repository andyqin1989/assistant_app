package com.assistant.ua.bridge

import android.util.Log
import com.assistant.ua.framework.base.BasePresenter

/** Created by qinbaoyuan on 2018/11/11
 */
open class MainPresenter : BasePresenter {
    override fun onCreate() {
        Log.e("andy", "Splash Presenter onCreate")
    }

    override fun onStart() {
        Log.e("andy", "Splash Presenter onStart")
    }

    override fun onResume() {
        Log.e("andy", "Splash Presenter onResume")
    }

    override fun onPause() {
        Log.e("andy", "Splash Presenter onPause")
    }

    override fun onStop() {
        Log.e("andy", "Splash Presenter onStop")
    }

    override fun onDestroy() {
        Log.e("andy", "Splash Presenter onDestroy")
    }
}