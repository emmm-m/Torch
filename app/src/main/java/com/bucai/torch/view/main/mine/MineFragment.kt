package com.bucai.torch.view.main.mine

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.SaveCallback
import com.bucai.torch.R
import com.bucai.torch.bean.FreeTime
import com.bucai.torch.view.login.LoginActivity
import com.google.gson.Gson
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
            val free = FreeTime()
            val m1 = FreeTime.Time()
            m1.start = "10:00"
            m1.end = "12:00"
            val m2 = FreeTime.Time()
            m2.start = "10:00"
            m2.end = "24:00"
            val list = ArrayList<FreeTime.Time>(7)
            list.add(m1)
            list.add(m2)
            free.monday = list
            free.tuesday = list
            free.wednesday = list
            free.thursday = list
            free.friday = list
            free.saturday = list
            free.sunday = list
            val tea = AVObject("Teacher")
            tea.put("description", description)
            tea.put("sex", "男")
            tea.put("age", 20)
            tea.put("phone", "17772727272")
            tea.put("name", "zzzia")
            tea.put("star", 30)
            tea.put("year", 10)
            tea.put("certification", 3)
            tea.put("price", "109w+")
            tea.put("freeTime", Gson().toJson(free, FreeTime::class.java))
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
