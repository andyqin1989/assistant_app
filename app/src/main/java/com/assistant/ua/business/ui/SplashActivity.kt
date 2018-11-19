package com.assistant.ua.business.ui

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.assistant.ua.BaseObserver
import com.assistant.ua.R
import com.assistant.ua.framework.base.ui.BaseActivity
import com.assistant.ua.bridge.SplashPresenter
import com.assistant.ua.databinding.ActivitySplashBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/** Created by qinbaoyuan on 2018/11/10
 */
class SplashActivity : BaseActivity() {
    private lateinit var splashBinding: ActivitySplashBinding
    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        splashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        lifecycle.addObserver(SplashPresenter())
        showCountDown()
        splashBinding.txtSplashNum.setOnClickListener { startMainActivity() }
    }

    private fun showCountDown() {
        val observable = Observable.interval(0, 1, TimeUnit.SECONDS)
            .take(3)
            .observeOn(AndroidSchedulers.mainThread())
            .map { t ->
                (2 - t).toString() + "s"
            }

        observable.subscribe(
            object : BaseObserver<String>() {
                override fun onSubscribe(d: Disposable) {
                    super.onSubscribe(d)
                    disposable = d
                }

                override fun onNext(t: String) {
                    super.onNext(t)
                    splashBinding.txtSplashNum.text = t
                }

                override fun onComplete() {
                    super.onComplete()
                    startMainActivity()
                }
            }
        )
    }

    private fun startMainActivity() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}