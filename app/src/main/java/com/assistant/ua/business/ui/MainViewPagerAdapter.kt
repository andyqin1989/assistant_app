package com.assistant.ua.business.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/** Created by qinbaoyuan on 2018/11/12
 */
open class MainViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    private val fragments = ArrayList<Fragment>()
    private val titles = ArrayList<String>()

    public fun addFragments(fragment: Fragment, title: String) {
        fragments.add(fragment)
        titles.add(title)
    }


    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}