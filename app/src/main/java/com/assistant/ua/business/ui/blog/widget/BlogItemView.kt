package com.assistant.ua.business.ui.blog.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.assistant.ua.R
import com.assistant.ua.datasource.entity.BlogEntity
import com.assistant.ua.framework.recycler.IViewData
import com.assistant.ua.framework.util.AsUiOps

/** Created by qinbaoyuan on 2018/11/19
 */
class BlogItemView : FrameLayout, IViewData<BlogEntity> {
    private lateinit var nameTxt: TextView
    private lateinit var timeTxt: TextView

    constructor (context: Context) : this(context, null)

    constructor(context: Context, attribute: AttributeSet?) : this(context, attribute, 0)

    constructor(context: Context, attribute: AttributeSet?, defStyleAttr: Int) :
            super(context, attribute, defStyleAttr) {
        val view = View.inflate(context, R.layout.view_blog_item, this)
        nameTxt = view.findViewById(R.id.txt_task_name)
        timeTxt = view.findViewById(R.id.txt_task_time)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (layoutParams != null) {
            layoutParams.width = LayoutParams.MATCH_PARENT
            layoutParams.height = AsUiOps.dp2px(80)
        }
    }

    override fun update(data: BlogEntity?) {
        nameTxt.text = data?.titleName
        timeTxt.text = data?.getTime()
    }
}