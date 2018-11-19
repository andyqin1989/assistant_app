package com.assistant.ua.business.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.assistant.ua.LFUserInfo
import com.assistant.ua.datasource.net.UserRepository
import com.assistant.ua.datasource.net.request.LoginRequest
import com.assistant.ua.datasource.net.request.RegisterRequest
import com.assistant.ua.datasource.net.response.LoginResponse
import com.assistant.ua.datasource.room.DbRepository
import com.assistant.ua.framework.observer.ServiceObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/** Created by qinbaoyuan on 2018/11/13
 */
class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var disposable: Disposable
    var errorLiveData: MutableLiveData<String> = MutableLiveData()
    var succeedLiveData: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * 用户登录observer
     */
    private var loginObserver = object : ServiceObserver<LoginResponse>() {

        override fun onSubscribe(d: Disposable) {
            super.onSubscribe(d)
            disposable = d
        }

        override fun onNext(response: LoginResponse) {
            super.onNext(response)
            if (response.succeed()) {
                LFUserInfo.instance.setUserInfo(response.data)
                DbRepository.instance.saveLoginInfo(response.data)
                succeedLiveData.postValue(true)

            } else {
                errorLiveData.postValue(response.message)
            }
        }

        override fun onError(e: Throwable) {
            super.onError(e)
            errorLiveData.postValue(e.message)
        }
    }

    /**
     * 开始注册
     */
    fun startRegister(name: String, phoneNum: String, passWord: String) {
        if (checkInput(name, phoneNum, passWord)) {
            val request = RegisterRequest(name, phoneNum, passWord)
            UserRepository.getInstance().register(request)
                .subscribeOn(Schedulers.io())
                .doOnNext { response ->
                    if (!response.succeed()) {
                        errorLiveData.postValue(response.message)
                        disposable.dispose()
                    }
                }.doOnError { t ->
                    errorLiveData.postValue(t.message)
                    disposable.dispose()
                }.flatMap {
                    val loginRequest = LoginRequest(phoneNum, passWord)
                    UserRepository.getInstance().login(loginRequest)
                }.observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginObserver)
        }
    }

    /**
     * 开始登录
     */
    fun startLogin(phoneNum: String, passWord: String) {
        if (checkInput(phoneNum, passWord)) {
            UserRepository.getInstance().login(LoginRequest(phoneNum, passWord))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginObserver)
        }
    }

    /**
     * 登录时检查输入的参数
     */
    private fun checkInput(phoneNum: String, passWord: String): Boolean {
        return checkInput("default", phoneNum, passWord)
    }

    /**
     * 注册时检查输入的参数
     */
    private fun checkInput(name: String, phoneNum: String, passWord: String): Boolean {
        if (LoginCheckUtil.getNameError(name).isNotEmpty()) {
            errorLiveData.postValue(LoginCheckUtil.getNameError(name))
            return false
        }

        if (LoginCheckUtil.getPhoneNumError(phoneNum).isNotEmpty()) {
            errorLiveData.postValue(LoginCheckUtil.getPhoneNumError(phoneNum))
            return false
        }

        if (LoginCheckUtil.getPassWordError(passWord).isNotEmpty()) {
            errorLiveData.postValue(LoginCheckUtil.getPassWordError(passWord))
            return false
        }
        return true
    }
}