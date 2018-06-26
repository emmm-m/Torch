package com.bucai.torch.view.teacher

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.bucai.torch.R
import com.bucai.torch.bean.Lecturer
import com.bucai.torch.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_teacher_price.*

class TeacherPriceActivity : BaseActivity() {

    private lateinit var lecturer: Lecturer

    override val contentViewId: Int = R.layout.activity_teacher_price

    override fun initData() {
        teacher_price_root.setOnClickListener { finish() }
        lecturer = intent.getSerializableExtra("lecturer") as Lecturer
        val grade = arrayListOf("三年级", "四年级", "五年级", "六年级", "初二", "初三")
        val way = arrayListOf("老师上门￥${lecturer.price + 5}", "学生上门￥${lecturer.price}")
        teacher_price_gradeClassify.addStrings(grade, 4
                , R.drawable.tea_info_bg, 18f
                , Color.parseColor("#9C9C9C"))
        teacher_price_gradeClassify.setItemClickListener(View.OnClickListener {

        })
        teacher_price_wayClassify.addStrings(way, 2
                , R.drawable.tea_info_bg, 18f
                , Color.parseColor("#9C9C9C"))
        teacher_price_wayClassify.setItemClickListener(View.OnClickListener {

        })
    }
}
