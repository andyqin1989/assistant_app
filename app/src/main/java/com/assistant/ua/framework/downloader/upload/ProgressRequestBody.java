package com.assistant.ua.framework.downloader.upload;

import android.os.Handler;
import android.os.Message;
import com.assistant.ua.framework.downloader.data.Consts;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.*;

import java.io.IOException;

public class ProgressRequestBody extends RequestBody {
    private RequestBody requestBody;
    private Handler handler;
    private CountingSink countingSink;

    public ProgressRequestBody(RequestBody requestBody, Handler handler) {
        this.requestBody = requestBody;
        this.handler = handler;
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        try {
            countingSink = new CountingSink(sink);
            BufferedSink bufferedSink = Okio.buffer(countingSink);
            requestBody.writeTo(bufferedSink);
            bufferedSink.flush();
        } catch (Exception e) {
            Message message = Message.obtain();
            message.what = Consts.ERROR;
            message.obj = e.toString();
            handler.sendMessage(message);
        }
    }

    protected class CountingSink extends ForwardingSink {

        private long currentLength = 0;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);

            currentLength += byteCount;
            Message message = Message.obtain();
            message.what = Consts.PROGRESS;
            message.arg1 = (int) currentLength;
            message.arg2 = (int) contentLength();
            handler.sendMessage(message);
        }
    }
}
