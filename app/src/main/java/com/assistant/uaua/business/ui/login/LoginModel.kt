package com.assistant.uaua.business.ui.login

import androidx.databinding.BaseObservable

/**
 * Created by qinbaoyuan on 2018/11/12
 */
class LoginModel : BaseObservable() {
    var isHasAccount: Boolean = true

    fun setAccount(hasAccount: Boolean) {
        isHasAccount = hasAccount
        notifyChange()
    }

    fun loginOrRegister(): String {
        return if (isHasAccount) {
            LOGIN
        } else {
            REGISTER
        }
    }

    fun registerOrHasAccount(): String {
        return if (isHasAccount) {
            REGISTER
        } else {
            LOGIN_WITH_ACCOUNT
        }
    }

    companion object {
        const val LOGIN = "登录"
        const val REGISTER = "注册"
        const val LOGIN_WITH_ACCOUNT = "已有账户登录"
    }
}
