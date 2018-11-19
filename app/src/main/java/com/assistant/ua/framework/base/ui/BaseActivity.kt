package com.assistant.ua.framework.base.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.assistant.ua.framework.base.BasePresenter

/** Created by qinbaoyuan on 2018/11/10
 */
abstract class BaseActivity : AppCompatActivity() {
    private var presenter: BasePresenter? = null

    fun initPresenter() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
    }
}