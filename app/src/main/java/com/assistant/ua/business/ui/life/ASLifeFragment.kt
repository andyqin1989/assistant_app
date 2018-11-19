package com.assistant.ua.business.ui.life

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.assistant.ua.R
import com.assistant.ua.databinding.FragmentLifeBinding
import com.assistant.ua.framework.base.ui.ServiceFragment

/** Created by qinbaoyuan on 2018/11/12
 */
class ASLifeFragment : ServiceFragment() {
    private lateinit var lifeBinding: FragmentLifeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        lifeBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_life, null, false)
        initViews(lifeBinding.root)
        return lifeBinding.root
    }

    private fun initViews(view: View) {

    }
}