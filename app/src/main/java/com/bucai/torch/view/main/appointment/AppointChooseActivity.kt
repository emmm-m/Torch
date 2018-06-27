package com.bucai.torch.view.main.appointment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import com.bucai.torch.R
import com.bucai.torch.bean.FreeTime
import com.bucai.torch.bean.Teacher
import com.bucai.torch.util.LogUtil
import com.bucai.torch.util.uiutils.ClassifyGroup
import com.bucai.torch.view.base.BaseActivity
import com.zia.toastex.ToastEx
import kotlinx.android.synthetic.main.activity_appoint_choose.*
import java.util.*

class AppointChooseActivity : BaseActivity() {

    override val contentViewId: Int = R.layout.activity_appoint_choose

    private val FULL = "已满"
    private var way = "面对面咨询"
    private var time = ""
    private var dayTime = ""
    private lateinit var teacher: Teacher
    private var white: Int = 0
    private var orange: Int = 0
    private lateinit var bg: Drawable
    private lateinit var sbg: Drawable
    private lateinit var weekSelectBg: Drawable
    private var lastSelectedView: TextView? = null

    override fun initData() {
        teacher = intent.getSerializableExtra("teacher") as Teacher
        initColor()
        setWayLayout()//咨询方式
        setTimeChooseLayout()//时间选择
        appoint_choose_submit.setOnClickListener {
            if (time.isNotEmpty() && dayTime.isNotEmpty()) {
                val intent = Intent(this@AppointChooseActivity, AppointCompleteActivity::class.java)
                intent.putExtra("way", way)
                intent.putExtra("time", time)
                intent.putExtra("teacher", teacher)
                intent.putExtra("dayTime", dayTime)
                startActivity(intent)
            } else {
                if (time.isEmpty()) ToastEx.warning(this@AppointChooseActivity, "请选择具体时间").show()
                if (dayTime.isEmpty()) ToastEx.warning(this@AppointChooseActivity, "请选择日期").show()
            }
        }
    }

    private fun initColor() {
        white = resources.getColor(R.color.white)
        orange = resources.getColor(R.color.orange)
        bg = resources.getDrawable(R.drawable.appoint_choose_way_bg)
        sbg = resources.getDrawable(R.drawable.appoint_choose_way_bg_select)
        weekSelectBg = resources.getDrawable(R.drawable.appoint_choose_week_bg_select)
    }

    private fun setTimeChooseLayout() {
        initTime()
    }

    private fun initTime() {//脑壳痛，为了方便修改服务器数据，app来7遍吧
        //设置日期
        val calendar = Calendar.getInstance()
        val toWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1//今天星期几
        val toDay = calendar.get(Calendar.DAY_OF_MONTH)//今天的日期
        val toMonth = calendar.get(Calendar.MONTH)
        //设置空闲时间
        val freeTime = teacher.freeTime
        val weeks = arrayListOf<List<FreeTime.Time>>(freeTime.monday, freeTime.tuesday, freeTime.wednesday
                , freeTime.thursday, freeTime.friday, freeTime.saturday, freeTime.sunday)
        val texts = arrayListOf<TextView>(appoint_choose_mondayText, appoint_choose_TuesdayText,
                appoint_choose_WedText, appoint_choose_ThurText, appoint_choose_FridayText,
                appoint_choose_SaturdayText, appoint_choose_sundayText)
        val layouts = arrayListOf<View>(appoint_choose_mondayLayout, appoint_choose_TuesdayLayout,
                appoint_choose_WedLayout, appoint_choose_ThurLayout, appoint_choose_FridayLayout,
                appoint_choose_SaturdayLayout, appoint_choose_sundayLayout)
        val emptys = arrayListOf<TextView>(appoint_choose_mondayEmpty, appoint_choose_TuesdayEmpty,
                appoint_choose_WedEmpty, appoint_choose_ThurEmpty, appoint_choose_FridayEmpty,
                appoint_choose_SaturdayEmpty, appoint_choose_sundayEmpty)
        //设置已满提醒
        layouts.forEach {
            it.setOnClickListener {
                ToastEx.warning(this@AppointChooseActivity, "人数已满，请选择其它时间").show()
            }
        }
        //设置星期几的选择
        for (i in 0..6) {
            val w = weeks[i].filter { it.isEmpty }//去掉已满
            if (w.isNotEmpty()) {
                setInvisible(emptys[i])
                texts[i].setTextColor(orange)
                layouts[i].setOnClickListener {
                    setWeekSelect(texts[i])
                    setClassifyGroup(w.map { "${it.start}-${it.end}" })//设置选择具体时间
                    dayTime = "${toMonth}月${toDay}日 ${getWeekString(i + 1)}"
                    freshTv()
                }
            }
        }
        LogUtil.e("week:$toWeek,day:$toDay")
        for (i in 0..6) {
            texts[(i + toWeek - 1) % 7].text = calendar.get(Calendar.DAY_OF_MONTH).toString()
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        //选择今天
        setWeekSelect(texts[toWeek - 1])
        val w = weeks[toWeek - 1].filter { it.isEmpty }
        setClassifyGroup(w.map { "${it.start}-${it.end}" })//设置选择具体时间
        dayTime = "${toMonth}月${toDay}日 ${getWeekString(toWeek)}"
    }

    private fun setWeekSelect(view: TextView) {
        lastSelectedView?.background = null
        lastSelectedView?.setTextColor(orange)
        lastSelectedView = view
        view.background = weekSelectBg
        view.setTextColor(white)
    }

    private fun setInvisible(view: View) {
        view.visibility = View.INVISIBLE
    }

    private fun getWeekString(day: Int): String {
        when (day) {
            1 -> {
                return "星期一"
            }
            2 -> {
                return "星期二"
            }
            3 -> {
                return "星期三"
            }
            4 -> {
                return "星期四"
            }
            5 -> {
                return "星期五"
            }
            6 -> {
                return "星期六"
            }
            7 -> {
                return "星期日"
            }
        }
        return ""
    }

    private fun setTimeClick() {

    }

    private var timeSelected: TextView? = null

    private fun setClassifyGroup(strings: List<String>) {
        LogUtil.e(strings.toString())
        appoint_choose_timeGroup.addStrings(strings, 3, R.drawable.appoint_choose_way_bg, 18f, orange)
        appoint_choose_timeGroup.setItemClickListener(object :ClassifyGroup.ItemClickListener {
            override fun onClick(index: Int, view: View) {
                timeSelected?.setTextColor(orange)
                timeSelected?.background = bg
                timeSelected = view as TextView?
                timeSelected?.background = sbg
                timeSelected?.setTextColor(white)
                time = timeSelected?.text.toString()
                freshTv()
            }

        })
    }

    private fun setWayLayout() {
        appoint_choose_face.setOnClickListener {
            way = "面对面咨询"
            appoint_choose_face.setTextColor(white)
            appoint_choose_face.background = sbg
            appoint_choose_video.setTextColor(orange)
            appoint_choose_video.background = bg
            freshTv()
        }
        appoint_choose_video.setOnClickListener {
            way = "视频咨询"
            appoint_choose_face.setTextColor(orange)
            appoint_choose_face.background = bg
            appoint_choose_video.setTextColor(white)
            appoint_choose_video.background = sbg
            freshTv()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun freshTv() {
        runOnUiThread { appoint_choose_tv.text = "$dayTime  $time\n$way" }
    }
}
