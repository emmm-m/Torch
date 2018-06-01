package com.bucai.torch.view.main.home


import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.bucai.torch.R
import com.bucai.torch.util.model.GetDataModel
import com.bucai.torch.util.model.IGetDataModel
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * 主页
 */
class HomeFragment : Fragment() {

    private lateinit var adapter: HomeRvAdapter
    private val model: IGetDataModel = GetDataModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = HomeRvAdapter()
        home_rv.adapter = adapter
        home_rv.layoutManager = LinearLayoutManager(context)
        loadGalleryImages()
    }

    private fun loadGalleryImages(){
        val list = ArrayList<View>()
        val imageView1 = ImageView(context)
        imageView1.setBackgroundColor(Color.RED)
        list.add(imageView1)
        val imageView2 = ImageView(context)
        imageView2.setBackgroundColor(Color.GREEN)
        list.add(imageView2)
        val imageView3 = ImageView(context)
        imageView3.setBackgroundColor(Color.BLACK)
        list.add(imageView3)
        adapter.setGalleryViews(list)
    }

}
