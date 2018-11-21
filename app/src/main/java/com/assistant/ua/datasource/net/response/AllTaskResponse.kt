package com.assistant.ua.datasource.net.response

import com.assistant.ua.datasource.entity.BlogEntity

/** Created by qinbaoyuan on 2018/11/19
 */
class AllTaskResponse : LFBaseResponse() {
    public var data: List<BlogEntity>? = null
}