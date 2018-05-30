package com.bucai.torch.view.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.bucai.torch.R
import com.bucai.torch.util.SharedPreferencesUtils
import com.bucai.torch.view.EvaluatePrepareActivity
import com.bucai.torch.view.main.appointment.AppointFragment
import com.bucai.torch.view.main.home.HomeFragment
import com.bucai.torch.view.main.information.InformationFragment
import com.bucai.torch.view.main.measurement.MeasureFragment
import com.bucai.torch.view.main.mine.MineFragment
import kotlinx.android.synthetic.main.activity_main.*
import q.rorbin.badgeview.Badge
import q.rorbin.badgeview.QBadgeView
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object {
        var USER : String? = null
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val count = intent!!.getIntExtra("count", 0)
        main_viewPager.currentItem = count
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = MyPagerAdapter(supportFragmentManager)
        main_viewPager.adapter = adapter
        setBottomNavigate()
        setEvaluate()

    }

    private fun setEvaluate() {
        if(SharedPreferencesUtils.getParam(this, "hasEvaluated", false) == false)
            startActivity(Intent(this@MainActivity, EvaluatePrepareActivity::class.java))
    }

    private fun setBottomNavigate() {
        bnve.enableAnimation(false)
        bnve.enableShiftingMode(false)
        bnve.enableItemShiftingMode(false)
        addBadgeAt(2, 1)
        bnve.setupWithViewPager(main_viewPager)
    }

    //添加小红点
    private fun addBadgeAt(position: Int, number: Int): Badge {
        return QBadgeView(this)
                .setBadgeNumber(number)
                .setGravityOffset(12f, 2f, true)
                .bindTarget(bnve.getBottomNavigationItemView(position))
                .setOnDragStateChangedListener { dragState, _, _ ->
                    if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState) {
                        Toast.makeText(this@MainActivity, "drag", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    internal inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val titles = arrayOf(getString(R.string.home)
                , getString(R.string.information), getString(R.string.measurement)
                , getString(R.string.appointment), getString(R.string.mine))
        private val fragments = ArrayList<Fragment>()

        init {
            fragments.add(HomeFragment())
            fragments.add(InformationFragment())
            fragments.add(MeasureFragment())
            fragments.add(AppointFragment())
            fragments.add(MineFragment())
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }
}
