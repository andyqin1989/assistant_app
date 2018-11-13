package com.assistant.uaua.business.ui.login

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.assistant.uaua.R
import com.assistant.uaua.databinding.ActivityLoginBinding
import com.assistant.uaua.framework.base.ui.BaseActivity

/** Created by qinbaoyuan on 2018/11/11
 */
class LoginActivity : BaseActivity(), View.OnClickListener {
    private lateinit var loginBinding: ActivityLoginBinding
    private val loginModel = LoginModel()
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        initViews()
        loginBinding.loginModel = loginModel
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        listenViewModel()
    }

    private fun initViews() {
        setSupportActionBar(loginBinding.toolBarLogin)
        loginBinding.toolBarLogin.setNavigationOnClickListener { onBackPressed() }
        loginBinding.txtLoginSwitch.setOnClickListener(this)
        loginBinding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.txt_login_switch -> {
                loginModel.setAccount(!loginModel.isHasAccount)
                if (!loginModel.isHasAccount) {
                    setViewFocus(loginBinding.editNameLogin)
                } else {
                    setViewFocus(loginBinding.editPhoneLogin)
                }
            }

            R.id.btn_login -> {
                startLoginAndRegister()
            }
        }
    }

    /**
     * 设置EditView焦点
     */
    private fun setViewFocus(view: View) {
        Handler().postDelayed({
            view.isFocusable = true
            view.isFocusableInTouchMode = true
            view.requestFocus()
            view.requestFocusFromTouch()
        }, 50)

    }

    private fun startLoginAndRegister() {
        if (loginModel.isHasAccount) {
            loginViewModel.startLogin(
                loginBinding.editPhoneLogin.text.toString(),
                loginBinding.editPasswordLogin.text.toString()
            )
        } else {
            loginViewModel.startRegister(
                loginBinding.editNameLogin.text.toString(),
                loginBinding.editPhoneLogin.text.toString(),
                loginBinding.editPasswordLogin.text.toString()
            )
        }
    }

    private fun listenViewModel() {
        val errorObserver = Observer<String> { error ->
            run {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        }


        val succeedObserver = Observer<Boolean> { succeed ->
            run {
                if (succeed) {
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }
        }

        loginViewModel.errorLiveData.observe(this, errorObserver)
        loginViewModel.succeedLiveData.observe(this, succeedObserver)

    }
}