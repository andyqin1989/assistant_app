package com.assistant.ua.datasource.net.response

open class LFBaseResponse {
    var code: Int = 0
    var message: String? = null

    public fun succeed(): Boolean {
        return code == 0
    }

}
