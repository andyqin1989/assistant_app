package com.assistant.ua.framework.recycler;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.assistant.ua.framework.util.AsUiOps;

/**
 * Created by qinbaoyuan on 2018/11/19
 */
public class RecyclerItemDecoration extends RecyclerView.ItemDecoration {
    private Paint paint;

    public RecyclerItemDecoration() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#f0f0f0"));
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = AsUiOps.dp2px(1);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            c.drawRect(new Rect(child.getLeft(), child.getBottom(),
                    child.getRight(), child.getBottom() + AsUiOps.dp2px(1)), paint);
        }
    }
}
