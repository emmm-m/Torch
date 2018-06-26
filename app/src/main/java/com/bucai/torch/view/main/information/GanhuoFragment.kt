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
import com.bucai.torch.util.leancloud.GetDataModel
import com.bucai.torch.util.leancloud.IGetDataModel
import com.bucai.torch.view.main.home.log
import kotlinx.android.synthetic.main.fragment_ganhuo.*

// TODO: Rename parameter arguments, choose names that match

class GanhuoFragment : Fragment() {

    val model: IGetDataModel = GetDataModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ganhuo, container, false)
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
        model.getData("InfoGanhuo", object : GetDataModel.GetDataListener<AVObject>{
            override fun onStart() {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onError(e: AVException?) {
                log(e.toString())
            }

            override fun onFinish(list: MutableList<AVObject>?) {
                rv_ganhuo.layoutManager = LinearLayoutManager(activity)
                rv_ganhuo.adapter = InfoRvAdapter(list!!, activity!!)
            }

        })
    }

}
