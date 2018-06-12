package com.bucai.torch.view.main.appointment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bucai.torch.R
import com.bucai.torch.bean.Teacher
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_tea_info.*

class TeaInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tea_info)
        val teacher = intent.getSerializableExtra("teacher") as Teacher
        tea_info_classifyGroup.addStrings(teacher.description, 4, R.drawable.tea_info_bg, 18f, Color.parseColor("#676666"))
        tea_info_name.text = teacher.name
        tea_info_price.text = teacher.price
        Glide.with(this@TeaInfoActivity).load(teacher.head).into(tea_info_circleImage)
    }
}
