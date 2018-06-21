package com.bucai.torch.view.main.mine

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avos.avoscloud.*
import com.bucai.torch.R
import com.bucai.torch.bean.Comment
import com.bucai.torch.bean.Lecturer
import com.bucai.torch.util.LogUtil
import com.bucai.torch.util.model.GetDataModel
import com.bucai.torch.view.login.LoginActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_mine.*
import java.util.*
import kotlin.collections.ArrayList

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
            val simpleComment = Comment()
            simpleComment.comment = "评论啦啦啦啦"
            simpleComment.effectRatio = 100
            simpleComment.serveRatio = 100
            simpleComment.good = 10
            simpleComment.totalComment = 20
            simpleComment.time = Date().time.toString()
            simpleComment.userObjectId = "5b29e75aac502e0031a66166"
            val completeComment = ArrayList<Comment>()
            completeComment.add(simpleComment)
            completeComment.add(simpleComment)
            completeComment.add(simpleComment)
            val gson = Gson()
            val avObject = AVObject("Lecturer")
            avObject.put("description", arrayOf("帅气迷人", "文武双全", "金枪不倒", "善解人衣"))
            avObject.put("commentGroup", arrayOf("讲课认真19", "耐心16", "思维敏捷12"))
            avObject.put("studentCount", 5)
            avObject.put("simpleIntroduce", "教龄8年 | 已授536课")
            avObject.put("successCase", arrayOf("朱展萱同学 中考成绩750\n朱展萱特别优秀","朱展萱同学 中考成绩750\n朱展萱特别优秀"))
            avObject.put("experience", "教学100余年，老字号")
            avObject.put("star", 5)
            avObject.put("teaName", "Mr.Li")
            avObject.put("completeIntroduce", "这是一条很长很长的完整介绍")
            avObject.put("simpleComment", gson.toJson(simpleComment))
            avObject.put("goodCommentCount", 20)
            avObject.put("location", AVGeoPoint(1.0, 1.0))
            avObject.put("price", 100)
            avObject.put("completeComment", gson.toJson(completeComment))
            avObject.saveInBackground(object : SaveCallback() {
                override fun done(p0: AVException?) {
                    p0?.printStackTrace()
                    if (p0 != null)
                        LogUtil.e("push success")
                }
            })
        }

        mine_get.setOnClickListener {
            val model = GetDataModel()
            model.getLecturerList(object : GetDataModel.GetDataListener<Lecturer>{
                override fun onStart() {

                }

                override fun onError(e: AVException?) {
                    e?.printStackTrace()
                }

                override fun onFinish(list: MutableList<Lecturer>?) {
                    list?.forEach {
                        var lecturer = model.getLectureDetail(it)
                        lecturer = model.getLecturerComment(lecturer)
                        LogUtil.e(lecturer.toString())
                    }
                }

            })
        }
        btn_logout.setOnClickListener {
            AVUser.logOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity!!.finish()
        }
    }
}
