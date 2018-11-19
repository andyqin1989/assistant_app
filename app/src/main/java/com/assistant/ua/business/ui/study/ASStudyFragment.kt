package com.assistant.ua.business.ui.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.assistant.ua.R
import com.assistant.ua.databinding.FragmentStudyBinding
import com.assistant.ua.framework.base.ui.ServiceFragment

/** Created by qinbaoyuan on 2018/11/12
 */
class ASStudyFragment : ServiceFragment() {
    private lateinit var lifeBinding: FragmentStudyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        lifeBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_study, null, false)
        initViews(lifeBinding.root)
        return lifeBinding.root
    }

    private fun initViews(view: View) {

    }
}