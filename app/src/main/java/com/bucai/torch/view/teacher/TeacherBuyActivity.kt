package com.bucai.torch.view.teacher

import com.bucai.torch.R
import com.bucai.torch.view.base.BaseActivity
import com.zia.toastex.ToastEx

class TeacherBuyActivity : BaseActivity() {

    override val contentViewId = R.layout.activity_teacher_buy
    private var totalPrice = 0

    override fun initData() {
        totalPrice = intent.getIntExtra("totalPrice", 0)
        ToastEx.info(this@TeacherBuyActivity, "一共需要支付${totalPrice}元").show()
    }
}
