package com.assistant.ua.business.ui.blog;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.assistant.ua.R;
import com.assistant.ua.databinding.ActivityBlogDetailBinding;
import com.assistant.ua.framework.base.ui.BaseActivity;
import com.zzhoujay.markdown.MarkDown;

/**
 * Created by qinbaoyuan on 2018/11/20
 */
public class BlogDetailActivity extends BaseActivity {
    private ActivityBlogDetailBinding blogDetailBinding;
    private String blogContent;
    private String titleName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        blogDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_blog_detail);

        setSupportActionBar(blogDetailBinding.toolbarBlogContent);
        blogContent = getIntent().getStringExtra("blog_content");
        titleName = getIntent().getStringExtra("title_name");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Spanned spanned = MarkDown.fromMarkdown(blogContent, new Html.ImageGetter() {
                @Override
                public Drawable getDrawable(String source) {
                    Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
                    drawable.setBounds(0, 0, 400, 400);
                    return drawable;
                }
            }, blogDetailBinding.txtBlogContent);
            blogDetailBinding.txtBlogContent.setText(spanned);
            blogDetailBinding.toolbarBlogContent.setTitle(titleName);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
