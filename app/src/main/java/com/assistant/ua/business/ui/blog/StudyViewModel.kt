package com.assistant.ua.business.ui.blog

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.assistant.ua.BaseObserver
import com.assistant.ua.LFUserInfo
import com.assistant.ua.datasource.entity.BlogEntity
import com.assistant.ua.datasource.net.UserRepository
import com.assistant.ua.datasource.net.request.GetAllBlogRequest
import retrofit2.HttpException

/** Created by qinbaoyuan on 2018/11/19
 */
class StudyViewModel(application: Application) : AndroidViewModel(application) {
    var allBlogLiveData: MutableLiveData<List<BlogEntity>> = MutableLiveData()
    var errorLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        if (LFUserInfo.instance.isLogin()) {
            getAllTask()
        }
    }

    fun getAllTask() {
        UserRepository.getInstance().getAllBlog(GetAllBlogRequest(LFUserInfo.instance.phoneNum))

            .subscribe(object : BaseObserver<List<BlogEntity>>() {
                override fun onNext(t: List<BlogEntity>) {
                    super.onNext(t)
                    allBlogLiveData.postValue(t)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    if (e is HttpException) {
                        Log.e("andy", "httpException = " + e.code())
                    }
                    errorLiveData.postValue(e.message)
                }
            })
    }
}