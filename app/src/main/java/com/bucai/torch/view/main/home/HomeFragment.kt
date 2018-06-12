package com.bucai.torch.view.main.home


import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.avos.avoscloud.AVException

import com.bucai.torch.R
import com.bucai.torch.bean.Teacher
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
        GetDataModel().getTeachersList(object  : GetDataModel.GetDataListener<Teacher>{
            override fun onStart() {

            }

            override fun onError(e: AVException?) {
                e?.printStackTrace()
            }

            override fun onFinish(list: MutableList<Teacher>?) {
                Log.e("zia",list?.toString())
                this@HomeFragment.activity?.runOnUiThread {
                    adapter.freshTeacher(list as ArrayList<Teacher>)
                }
            }
        })
    }
}

fun Fragment.log(str: String) {
    Log.d(activity.toString() + "Test", str)
}

fun Fragment.toast(str: String) {
    Toast.makeText(activity, str, Toast.LENGTH_SHORT).show()
}