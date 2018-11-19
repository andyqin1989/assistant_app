package com.assistant.ua.framework.network

import android.util.Log

/** Created by qinbaoyuan on 2018/11/12
 */
class NetWorkLog {
    private val TAG = NetWorkLog::class.java.simpleName

    companion object {
        private fun e(message: String) {
            Log.e("network", "message = $message")
        }

        fun showRequest(url: String, tag: String, message: String) {
            if (!url.isEmpty()) {
                e("url = $url")
            }

            if (!message.isEmpty()) {
                e("@@@@@- Begin $tag Body -@@@@@")
                e(message)
                e("@@@@@- End $tag Body -@@@@@\r\n")
            }
        }

        fun showResponse(tag: String, message: String) {
            if (!message.isEmpty()) {
                e("@@@@@- Begin $tag Body -@@@@@")
                e(message)
                e("@@@@@- End $tag Body -@@@@@\r\n")
            }
        }
    }

}