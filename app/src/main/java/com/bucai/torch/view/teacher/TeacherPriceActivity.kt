package com.bucai.torch.view.teacher

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.bucai.torch.R
import com.bucai.torch.bean.Lecturer
import com.bucai.torch.util.uiutils.ClassifyGroup
import com.bucai.torch.view.base.BaseActivity
import com.zia.toastex.ToastEx
import kotlinx.android.synthetic.main.activity_teacher_price.*

class TeacherPriceActivity : BaseActivity() {

    private lateinit var lecturer: Lecturer

    override val contentViewId: Int = R.layout.activity_teacher_price

    private var lastGrade: TextView? = null
    private var lastWay: TextView? = null
    private var lastDiscount: TextView? = null
    private var grade = ""
    private var price = 0
    private var classCount = 0
    private var discount = 0.0f
    private var totalPrice = 0
    private val textColor = Color.parseColor("#9C9C9C")

    override fun initData() {
        teacher_price_root.setOnClickListener { finish() }
        lecturer = intent.getSerializableExtra("lecturer") as Lecturer
        val grade = lecturer.grade
        val way = arrayListOf("老师上门￥${lecturer.price + 5}", "学生上门￥${lecturer.price}")
        val discount = arrayListOf("买56课时送4课时", "买96课时送8课时", "买136课时送12课时", "有钱任性，不用优惠")
        teacher_price_gradeClassify.addStrings(grade, 4
                , R.drawable.tea_info_bg, 18f
                , Color.parseColor("#9C9C9C"))
        teacher_price_gradeClassify.setItemClickListener(object : ClassifyGroup.ItemClickListener {
            override fun onClick(index: Int, view: View) {
                change(lastGrade, view as TextView)
                lastGrade = view
                this@TeacherPriceActivity.grade = view.text.toString()
            }
        })
        teacher_price_wayClassify.addStrings(way, 2
                , R.drawable.tea_info_bg, 18f
                , Color.parseColor("#9C9C9C"))
        teacher_price_wayClassify.setItemClickListener(object : ClassifyGroup.ItemClickListener {
            override fun onClick(index: Int, view: View) {
                price = when (index) {
                    0 -> lecturer.price + 5
                    1 -> lecturer.price
                    else -> 0
                }
                change(lastWay, view as TextView)
                lastWay = view
            }
        })
        teacher_price_discount.addStrings(discount, 1
                , R.drawable.tea_info_bg, 18f
                , Color.parseColor("#9C9C9C"))
        teacher_price_discount.setItemClickListener(object : ClassifyGroup.ItemClickListener {
            override fun onClick(index: Int, view: View) {
                when (index) {
                    0 -> {
                        this@TeacherPriceActivity.discount = 1 - 4 / 56f
                        totalPrice = price * 52
                        classCount = 52
                    }
                    1 -> {
                        this@TeacherPriceActivity.discount = 1 - 8 / 96f
                        totalPrice = price * 88
                        classCount = 88
                    }
                    2 -> {
                        this@TeacherPriceActivity.discount = 1 - 12 / 136f
                        totalPrice = price * 124
                        classCount = 124
                    }
                    else -> {
                        this@TeacherPriceActivity.discount = 1f
                        totalPrice = price
                        classCount = 1
                    }
                }
                change(lastDiscount, view as TextView)
                lastDiscount = view
            }
        })
        teacher_price_buy.setOnClickListener {
            if (totalPrice == 0 || this@TeacherPriceActivity.discount == 0.0f || price == 0) {
                ToastEx.warning(this@TeacherPriceActivity, "请选择...").show()
                return@setOnClickListener
            }
            val intent = Intent(this@TeacherPriceActivity, TeacherBuyActivity::class.java)
            intent.putExtra("totalPrice", totalPrice)
            intent.putExtra("classCount", classCount)
            intent.putExtra("objectId", lecturer.objectId)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun change(lastView: TextView?, currentView: TextView) {
        lastView?.background = resources.getDrawable(R.drawable.tea_info_bg)
        lastView?.setTextColor(textColor)
        currentView.background = resources.getDrawable(R.drawable.bg_tea_good_count)
        currentView.setTextColor(resources.getColor(R.color.orange))
        teacher_price_value.text = (price * discount).toInt().toString() + "元/小时"
    }
}
