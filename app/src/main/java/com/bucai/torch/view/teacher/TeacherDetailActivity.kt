package com.bucai.torch.view.teacher

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bucai.torch.R
import com.bucai.torch.bean.Lecturer
import com.bucai.torch.util.ThreadPool
import com.bucai.torch.util.getStar
import com.bucai.torch.util.leancloud.GetDataModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_teacher_detail.*

class TeacherDetailActivity : AppCompatActivity() {

    private lateinit var lecturer: Lecturer

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_detail)
        lecturer = intent.getSerializableExtra("lecturer") as Lecturer
        Glide.with(this@TeacherDetailActivity).load(lecturer.head).into(teacher_detail_head)
        val progressDialog = ProgressDialog.show(this@TeacherDetailActivity, "正在加载", "请耐心等待")
        ThreadPool.instance.cachedThreadPool.execute {
            lecturer = GetDataModel().getLectureDetail(lecturer)
            runOnUiThread {
                progressDialog.dismiss()
                teacher_detail_successCase.text = lecturer.successCase[0]
                teacher_detail_star.text = getStar(lecturer.star)
                teacher_detail_simpleIntroduce.text = lecturer.simpleIntroduce
                teacher_detail_price.text = "￥${lecturer.price}起"
                teacher_detail_goodCount.text = "${lecturer.goodCommentCount}好评"
                teacher_detail_completeIntroduce.text = lecturer.completeIntroduce
                teacher_detail_name.text = lecturer.teaName
                teacher_detail_studentCount.text = "已在火炬教过${lecturer.studentCount}个学生"
                teacher_detail_experience.text = lecturer.experience
                teacher_detail_classify.addStrings(lecturer.description, 4
                        , R.drawable.tea_info_bg, 18f
                        , Color.parseColor("#9C9C9C"))
            }
        }
    }
}
