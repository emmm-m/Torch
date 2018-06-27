package com.bucai.torch.view.main.searchresult

import android.support.v7.widget.LinearLayoutManager
import com.avos.avoscloud.AVException
import com.bucai.torch.R
import com.bucai.torch.bean.Lecturer
import com.bucai.torch.util.leancloud.GetDataModel
import com.bucai.torch.util.leancloud.IGetDataModel
import com.bucai.torch.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_search_result.*

class SearchResultActivity : BaseActivity() {
    val model: IGetDataModel = GetDataModel()

    override val contentViewId = R.layout.activity_search_result

    override fun initData() {
        val str = intent.extras["content"].toString()
        model.getTeachersList(str, object : GetDataModel.GetDataListener<Lecturer>{
            override fun onStart() {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onError(e: AVException?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onFinish(list: MutableList<Lecturer>?) {
                runOnUiThread {
                    if (list!!.isEmpty()) {
                        toast("没有搜索结果")
                    }
                    log(list.size.toString())
                    rv_result_search.layoutManager = LinearLayoutManager(this@SearchResultActivity)
                    rv_result_search.adapter = SearchResultRvAdapter(list!!)
                }
            }

        })
    }
}
