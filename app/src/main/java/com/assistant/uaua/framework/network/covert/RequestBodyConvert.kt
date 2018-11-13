package com.assistant.uaua.framework.network.covert


import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.assistant.uaua.framework.network.NetWorkLog
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.Converter

import java.io.IOException
import java.io.OutputStreamWriter
import java.nio.charset.Charset

/**
 * Created by qinbaoyuan on 2018/11/12
 */
class RequestBodyConvert<T>(private val gson: Gson, private val adapter: TypeAdapter<T>) : Converter<T, RequestBody> {

    @Throws(IOException::class)
    override fun convert(value: T): RequestBody {
        val buffer = Buffer()
        val writer = OutputStreamWriter(buffer.outputStream(), UTF_8)
        val jsonWriter = gson.newJsonWriter(writer)
        adapter.write(jsonWriter, value)
        jsonWriter.close()

        val byteString = buffer.readByteString()
        NetWorkLog.showRequest("", "Request", byteString.utf8())

        return RequestBody.create(MEDIA_TYPE, byteString)
    }

    companion object {
        private val MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8")
        private val UTF_8 = Charset.forName("UTF-8")
    }
}
