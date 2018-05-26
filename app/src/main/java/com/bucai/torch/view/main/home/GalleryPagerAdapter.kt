package com.bucai.torch.view.main.home

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

/**
 * Created by zia on 2018/5/26.
 */
class GalleryPagerAdapter : PagerAdapter() {

    private var list = ArrayList<View>()

    public fun setList(list: ArrayList<View>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(list[position])
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(list[position])
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }
}