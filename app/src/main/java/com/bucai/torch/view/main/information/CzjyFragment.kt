package com.bucai.torch.view.main.information

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVObject
import com.bucai.torch.R
import com.bucai.torch.util.model.GetDataModel
import com.bucai.torch.util.model.IGetDataModel
import com.bucai.torch.view.main.home.log
import kotlinx.android.synthetic.main.fragment_czjy.*


class CzjyFragment : Fragment() {
    val model: IGetDataModel = GetDataModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_czjy, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setList()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setList()
    }

    private fun setList() {
        model.getData("InfoCzjy", object :GetDataModel.GetDataListener<AVObject>{
            override fun onStart() {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onError(e: AVException?) {
                log(e.toString())
            }

            override fun onFinish(list: MutableList<AVObject>?) {
                rv_czjy.layoutManager = LinearLayoutManager(activity)
                rv_czjy.adapter = InfoRvAdapter(list!!, activity!!)
            }

        })
    }


}