package com.zc.demo.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.View

/**
 * @className FragmentViewPagerAdapter
 * @author wenchao
 * @date 2019/8/5
 * @version 1.0.1
 * @description
 */
class FragmentViewPagerAdapter(fm:FragmentManager,list: MutableList<Fragment>) : FragmentPagerAdapter(fm){
    var data:MutableList<Fragment> = list
    override fun getItem(position: Int): Fragment {
        return data.get(position)
    }

    override fun getCount(): Int {
        return data.size
    }
}