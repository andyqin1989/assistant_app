package com.assistant.ua.business.ui.blog.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import com.assistant.ua.R;
import com.assistant.ua.framework.util.AsUiOps;

/**
 * Created by qinbaoyuan on 2018/12/3
 */
public class FollowFragTopView extends FrameLayout {
    private float totalMoveLenght = AsUiOps.dp2px(51);

    private View rootView;

    private ImageView portraitImg;
    private AppCompatTextView nameTxt;
    private AppCompatTextView timeTxt;

    public FollowFragTopView(@NonNull Context context) {
        this(context, null);
    }

    public FollowFragTopView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FollowFragTopView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        View.inflate(getContext(), R.layout.view_follow_frag_top, this);
        rootView = findViewById(R.id.id_view);
        portraitImg = findViewById(R.id.img_user_portrait);
        nameTxt = findViewById(R.id.txt_user_name);
        timeTxt = findViewById(R.id.txt_add_time);
    }

    public void update() {

    }

    public void onScrollY(float scrollY) {
        if (scrollY < totalMoveLenght) {
            this.setTranslationY(-scrollY);
            rootView.setAlpha(scrollY / totalMoveLenght);
        }
        portraitScrollY(scrollY);
        nameScrollY(scrollY);
        timeScrollY(scrollY);
    }

    private void portraitScrollY(float scrollY) {
        float moveLength = AsUiOps.dp2px(27);
        if (scrollY <= totalMoveLenght) {
            Log.e("andy", "scrollY = " + scrollY);
            float rate = scrollY / totalMoveLenght;
            float imgScale = 1 - ((17f * rate) / 48f);
            Log.e("andy", "imgScale = " + imgScale);
            portraitImg.setScaleX(imgScale);
            portraitImg.setScaleY(imgScale);
            portraitImg.setTranslationY(moveLength * rate);
        }
    }

    private void nameScrollY(float scrollY) {
        float moveLength = AsUiOps.dp2px(36);
        if (scrollY <= totalMoveLenght) {
            Log.e("andy", "scrollY = " + scrollY);
            float rate = scrollY / totalMoveLenght;
            float imgScale = 1 - ((2f * rate) / 48f);
            Log.e("andy", "imgScale = " + imgScale);
            nameTxt.setScaleX(imgScale);
            nameTxt.setScaleY(imgScale);
            nameTxt.setTranslationY(moveLength * rate);
        }
    }

    private void timeScrollY(float scrollY) {
        if (scrollY < totalMoveLenght) {
            float rate = Math.min(scrollY / totalMoveLenght + 0.5f, 1);
            float alpha = 1 - rate;
            timeTxt.setAlpha(alpha);
        }
    }
}
