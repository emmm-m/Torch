package com.bucai.torch.view.teacher

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import com.bucai.torch.R
import com.bucai.torch.util.leancloud.IUpLoadModel
import com.bucai.torch.util.leancloud.UpLoadModel
import com.bucai.torch.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_teacher_buy.*


class TeacherBuyActivity : BaseActivity() {
    private val model: IUpLoadModel = UpLoadModel()

    override val contentViewId = R.layout.activity_teacher_buy
    private var totalPrice = 0
    private var classCount = 0
    private var teacherId = ""

    override fun initData() {
        totalPrice = intent.getIntExtra("totalPrice", 0)
        classCount = intent.getIntExtra("classCount", 0)
        teacherId = intent.getStringExtra("objectId")

        model.uploadOrderInfo(teacherId, totalPrice, classCount, object : UpLoadModel.UpLoadListener {
            override fun onStart() {
                tx_buy.text = "正在生成订单。。。"
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                tx_buy.text = "订单课时：$classCount 节\n " +
                        "订单总价：$totalPrice 元\n" +
                        "请点击下方拨打电话\n" +
                        "联系工作人员进行后续流程"
            }

        })
        btn_call_buy.setOnClickListener { call("15533538378") }
    }

    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    private fun call(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

}
