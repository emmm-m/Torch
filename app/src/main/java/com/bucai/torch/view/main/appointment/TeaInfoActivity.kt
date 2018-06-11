package com.bucai.torch.view.main.appointment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bucai.torch.R
import kotlinx.android.synthetic.main.activity_tea_info.*

class TeaInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tea_info)
        val strings = arrayListOf("青少年咨询", "家庭咨询", "亲子咨询", "儿童咨询", "伴侣咨询", "留学生咨询", "成人咨询")
        tea_info_classifyGroup.addStrings(strings, 4, R.drawable.tea_info_bg, 18f,Color.parseColor("#676666"))
//        val l = ArrayList<TextView>()
//        for (i in 0..7) {
//            val itemView = TextView(this@TeaInfoActivity)
//            itemView.text = "NO.$i"
//            itemView.background = resources.getDrawable(R.drawable.tea_info_bg)
////            itemView.setTextColor(Color.BLACK)
//            l.add(itemView)
//        }
//        tea_info_classifyGroup.addItems(l, 4)
    }
}
