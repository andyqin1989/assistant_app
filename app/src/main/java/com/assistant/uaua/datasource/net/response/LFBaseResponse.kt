package com.assistant.uaua.datasource.net.response

open class LFBaseResponse {
    var status: Int = 0
    var message: String? = null

    public fun succeed(): Boolean {
        return status == 1
    }

}
