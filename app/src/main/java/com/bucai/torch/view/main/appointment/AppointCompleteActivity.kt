package com.bucai.torch.view.main.appointment

import android.content.Intent
import com.bucai.torch.R
import com.bucai.torch.bean.Teacher
import com.bucai.torch.view.base.BaseActivity
import com.zia.toastex.ToastEx
import kotlinx.android.synthetic.main.activity_appoint_complete.*

class AppointCompleteActivity : BaseActivity() {
    override val contentViewId = R.layout.activity_appoint_complete

    override fun initData() {
        val teacher: Teacher = intent.getSerializableExtra("teacher") as Teacher
        val way = intent.getStringExtra("way")
        val time = intent.getStringExtra("time")
        val dayTime = intent.getStringExtra("dayTime")
        appoint_complete_submit.setOnClickListener {
            val name = appoint_complete_name.text.toString()
            val phone = appoint_complete_phone.text.toString()
            val sex = appoint_complete_sex.text.toString()
            val age = appoint_complete_age.text.toString()
            val status = appoint_complete_status.text.toString()
            val nameTwo = appoint_complete_name_two.text.toString()
            val phoneTwo = appoint_complete_phone_two.text.toString()
            val beizhu = appoint_complete_beizhu.text.toString()
            if (name.isEmpty() || phone.isEmpty() || sex.isEmpty() || age.isEmpty() || status.isEmpty()) {
                ToastEx.warning(this@AppointCompleteActivity, "基本信息不能为空").show()
            } else {
                val intent = Intent()
            }
        }

    }
}
