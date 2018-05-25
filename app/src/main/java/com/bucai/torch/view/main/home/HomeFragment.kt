package com.bucai.torch.view.main.home


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bucai.torch.R
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * 主页
 */
class HomeFragment : Fragment() {

    private lateinit var adapter: HomeRvAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = HomeRvAdapter()
        home_rv.adapter = adapter
        home_rv.layoutManager = LinearLayoutManager(context)
    }

}
