package com.bucai.torch.view.main.appointment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bucai.torch.R
import com.bucai.torch.bean.Teacher
import com.bucai.torch.util.ThreadPool
import com.bucai.torch.util.leancloud.GetDataModel
import com.bumptech.glide.Glide
import com.zia.toastex.ToastEx
import kotlinx.android.synthetic.main.activity_tea_info.*

class TeaInfoActivity : AppCompatActivity() {

    private lateinit var teacher: Teacher
    private lateinit var progressDialog: ProgressDialog
    private val dark = Color.parseColor("#808080")
    private val red = Color.parseColor("#DD7049")

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tea_info)
        progressDialog = ProgressDialog.show(this@TeaInfoActivity, "正在加载", "请等待")
        teacher = intent.getSerializableExtra("teacher") as Teacher
        tea_info_name.text = teacher.name
        tea_info_price.text = "" + teacher.price
        Glide.with(this@TeaInfoActivity).load(teacher.head).into(tea_info_circleImage)
        showIntroduce()
        tea_info_introduceLayout.setOnClickListener {
            showIntroduce()
        }
        tea_info_appointLayout.setOnClickListener {
            ToastEx.info(this@TeaInfoActivity, "正在加载教师资源，请等待").show()
        }
        tea_info_learnMore.setOnClickListener {
            ToastEx.info(this@TeaInfoActivity, "正在加载教师资源，请等待").show()
        }
        ThreadPool.instance.cachedThreadPool.execute {
            teacher = GetDataModel().getTeacherDetail(teacher)
            progressDialog.dismiss()
            tea_info_appointLayout.setOnClickListener {
                runOnUiThread { showAppointment() }
            }
            tea_info_learnMore.setOnClickListener {
                val intent = Intent(this@TeaInfoActivity, AppointChooseActivity::class.java)
                intent.putExtra("teacher", teacher)
                startActivity(intent)
            }
            runOnUiThread { tea_info_complete_introduce.text = teacher.completeIntroduce }
        }
    }

    private fun showIntroduce() {
        tea_info_appointBottom.visibility = View.GONE
        tea_info_appointText.setTextColor(dark)
        tea_info_introduceText.setTextColor(red)
        tea_info_introduce_bottom.visibility = View.VISIBLE
        tea_info_classifyGroup.addStrings(teacher.description, 4, R.drawable.tea_info_bg, 18f, Color.parseColor("#676666"))
    }

    private fun showAppointment() {
        tea_info_appointBottom.visibility = View.VISIBLE
        tea_info_appointText.setTextColor(red)
        tea_info_introduceText.setTextColor(dark)
        tea_info_introduce_bottom.visibility = View.GONE
        tea_info_classifyGroup.addStrings(getTimeList(), 3, R.drawable.tea_info_bg, 18f, Color.parseColor("#676666"))
    }

    private fun getTimeList(): List<String> {
        val freeTime = teacher.freeTime
        val f = ArrayList<String>()
        freeTime.monday.forEach {
            val t = "${it.start} - ${it.end}"
            f.add(t)
        }
        freeTime.tuesday.forEach {
            val t = "${it.start} - ${it.end}"
            f.add(t)
        }
        freeTime.wednesday.forEach {
            val t = "${it.start} - ${it.end}"
            f.add(t)
        }
        freeTime.thursday.forEach {
            val t = "${it.start} - ${it.end}"
            f.add(t)
        }
        freeTime.friday.forEach {
            val t = "${it.start} - ${it.end}"
            f.add(t)
        }
        freeTime.saturday.forEach {
            val t = "${it.start} - ${it.end}"
            f.add(t)
        }
        freeTime.sunday.forEach {
            val t = "${it.start} - ${it.end}"
            f.add(t)
        }
        return f.distinct()
    }

    override fun onDestroy() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
        super.onDestroy()
    }
}
