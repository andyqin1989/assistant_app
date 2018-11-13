package com.assistant.uaua.business.ui.login

/** Created by qinbaoyuan on 2018/11/13
 */
class LoginCheckUtil {
    companion object {

        private fun isEmpty(src: String?): Boolean {
            return src == null || src.isEmpty()
        }


        fun isNotPhoneNum(phone: String): Boolean {
            var result = false
            if (LoginCheckUtil.isEmpty(phone)) {
                result = true
            }

            if (phone.length != 11) {
                result = true
            }

            for (i in 0 until phone.length) {
                if (phone[i] > '9' || phone[i] < '0') {
                    result = true
                }
            }
            return result
        }

        fun getPhoneNumError(phone: String): String {
            var result = ""
            if (LoginCheckUtil.isEmpty(phone)) {
                result = "电话号码不能为空"
            }

            if (phone.length != 11) {
                result = "电话号码格式不正确"
            }

            for (i in 0 until phone.length) {
                if (phone[i] > '9' || phone[i] < '0') {
                    result = "电话号码格式不正确"
                }
            }
            return result
        }

        fun isNotPassWord(passWord: String): Boolean {
            return passWord.isEmpty() || passWord.length < 6 || passWord.length > 8
        }

        fun getPassWordError(passWord: String): String {
            var result = ""
            if (passWord.isEmpty()) {
                result = "密码不能为空"
            }

            if (passWord.length < 6 || passWord.length > 8) {
                result = "密码长度为6至8位"
            }
            return result
        }


        fun getNameError(name: String): String {
            if (name.isEmpty()) {
                return "昵称不能为空"
            }
            return ""
        }
    }
}