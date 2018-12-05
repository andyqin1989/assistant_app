package com.assistant.ua.business.ui.blog;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import androidx.core.widget.NestedScrollView;
import com.assistant.ua.R;
import com.assistant.ua.business.ui.blog.widget.FollowFragTopView;
import com.assistant.ua.business.webview.test_webview_demo.FullScreenActivity;
import com.assistant.ua.business.webview.test_webview_demo.PdfActivity;
import com.assistant.ua.framework.base.ui.BaseActivity;
import org.jetbrains.annotations.Nullable;

/**
 * Created by qinbaoyuan on 2018/11/23
 */
public class TestActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_test);

        NestedScrollView scrollView = findViewById(R.id.id_scroll_view);
        final FollowFragTopView topView = findViewById(R.id.id_follow_top_view);

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                topView.onScrollY(scrollY);
            }
        });

        Log.e("andyqin", "onCreate");

        topView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestActivity.this, PdfActivity.class));
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("andyqin", "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("andyqin", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("andyqin", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("andyqin", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("andyqin", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("andyqin", "onDestroy");
    }

    @Override
    public void onSaveInstanceState(Bundle outStat) {
        super.onSaveInstanceState(outStat);
        Log.e("andyqin", "onSaveInstanceState");
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("andyqin", "onRestoreInstanceState");
    }
}
