package com.bucai.torch.view.main.mine

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.FindCallback
import com.avos.avoscloud.SaveCallback
import com.bucai.torch.R
import com.bucai.torch.bean.FreeTime
import com.bucai.torch.bean.Teacher
import com.bucai.torch.util.model.GetDataModel
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * 我的
 *
 */
class MineFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mine_send.setOnClickListener {
            val description = arrayListOf("青少年咨询", "家庭咨询", "亲子咨询", "儿童咨询", "伴侣咨询", "留学生咨询", "成人咨询")
            val freeTime = ArrayList<FreeTime>()
            val free = FreeTime()
            free.start = "10:00"
            free.end = "24:00"
            freeTime.add(free)
            val tea = AVObject("Teacher")
            tea.put("description", description)
            tea.put("sex", "男")
            tea.put("age", 20)
            tea.put("phone", "17772727272")
            tea.put("name", "zia")
            tea.put("star", 30)
            tea.put("year", 10)
            tea.put("certification", 3)
            tea.put("price", "10w+")
            tea.put("freeTime", freeTime)
            tea.saveInBackground(object : SaveCallback() {
                override fun done(p0: AVException?) {
                    p0?.printStackTrace()
                }
            })
        }

        mine_get.setOnClickListener {

        }
        btn_logout.setOnClickListener {
            AVUser.logOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity!!.finish()
        }
    }
}
