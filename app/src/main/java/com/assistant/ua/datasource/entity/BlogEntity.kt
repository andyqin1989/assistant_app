package com.assistant.ua.datasource.entity

import java.text.SimpleDateFormat
import java.util.*

/** Created by qinbaoyuan on 2018/11/19
 */
class BlogEntity {
    public var id: Int = 0
    public var titleName: String = ""
    public var blogContent: String = ""
    public var createTime: Date? = null
    public var contentType = 0

    fun getTime(): String {
        val formatter = SimpleDateFormat.getDateTimeInstance()
        if (createTime != null) {
            return formatter.format(createTime)
        }
        return ""
    }
}