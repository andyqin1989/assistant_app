package com.assistant.ua.business.webview.test_webview_demo;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.LinearLayout;
import com.assistant.ua.R;
import com.assistant.ua.framework.base.ui.BaseActivity;
import com.tencent.smtt.sdk.TbsReaderView;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * Created by qinbaoyuan on 2018/12/4
 */
public class PdfActivity extends BaseActivity {
    private TbsReaderView readerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        readerView = new TbsReaderView(this, new TbsReaderView.ReaderCallback() {
            @Override
            public void onCallBackAction(Integer integer, Object o, Object o1) {
                Log.e("andyqin", "integer = " + integer);
                Log.e("andyqin", "o = " + o);
                Log.e("andyqin", "o1 = " + o1);
            }
        });

        LinearLayout rootView = findViewById(R.id.llayout_pdf_root_view);
        rootView.addView(readerView, new LinearLayout.LayoutParams(-1, -1));

        displayFile();
    }

    private void displayFile() {
        String path = getExternalCacheDir().getPath()+ File.separator + "11.pdf";
        Bundle bundle = new Bundle();
        bundle.putString("filePath", path);
        bundle.putString("tempPath", Environment.getExternalStorageDirectory().getPath());
        boolean result = readerView.preOpen(parseFormat("11.pdf"), false);
        if (result) {
            readerView.openFile(bundle);
        }
    }

    private String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Override
    protected void onDestroy() {
        readerView.onStop();
        super.onDestroy();
    }
}
