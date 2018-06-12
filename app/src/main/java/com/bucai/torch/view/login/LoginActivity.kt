package com.bucai.torch.view.login

import android.annotation.SuppressLint
import android.graphics.Color
import com.bucai.torch.R
import com.bucai.torch.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by zxzhu
 *   2018/6/12.
 *   enjoy it !!
 */
class LoginActivity : BaseActivity() {
//    val model: IUserModel = UserModel()
    private var isLogin = false

    private val signFragment = SignFragment()
    private val loginFragment = LoginFragment()

    override val contentViewId: Int = R.layout.activity_login

    @SuppressLint("CommitTransaction")
    override fun initData() {
        switch()
    }

    private fun switch() {
        when (isLogin) {
            true -> {
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_replace, signFragment)
                fragmentTransaction.commit()

                isLogin = false

                below_signin.setBackgroundColor(Color.parseColor("#FE896E"))
                tx_signin.setTextColor(Color.parseColor("#FE896E"))
                below_login.setBackgroundColor(Color.parseColor("#607D8B"))
                tx_login.setTextColor(Color.parseColor("#607D8B"))
                tx_tittle.text = "注册"

                switch_signin.setOnClickListener(null)
                switch_login.setOnClickListener {
                    switch()
                }
            }
            false -> {
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_replace, loginFragment)
                fragmentTransaction.commit()

                isLogin = true

                below_signin.setBackgroundColor(Color.parseColor("#607D8B"))
                tx_signin.setTextColor(Color.parseColor("#607D8B"))
                below_login.setBackgroundColor(Color.parseColor("#FE896E"))
                tx_login.setTextColor(Color.parseColor("#FE896E"))
                tx_tittle.text = "登录"

                switch_login.setOnClickListener(null)
                switch_signin.setOnClickListener {
                    switch()
                }
            }
        }
    }

}