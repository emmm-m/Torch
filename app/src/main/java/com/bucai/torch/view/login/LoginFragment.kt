package com.bucai.torch.view.login


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avos.avoscloud.AVException

import com.bucai.torch.R
import com.bucai.torch.util.leancloud.IUserModel
import com.bucai.torch.util.leancloud.UserModel
import com.bucai.torch.view.main.MainActivity
import com.bucai.torch.view.main.home.log
import com.bucai.torch.view.main.home.toast
import kotlinx.android.synthetic.main.fragment_logiin.*

// TODO: Rename parameter arguments, choose names that match

/**
 * A simple [Fragment] subclass.
 *
 */
class LoginFragment : Fragment() {
    private var dialog : ProgressDialog? = null
    val model: IUserModel = UserModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logiin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog = ProgressDialog(context)
        super.onViewCreated(view, savedInstanceState)
        setLogin()
    }

    private fun setLogin() {
        btn_login.setOnClickListener {
            if (edit_phone_login.text.length != 11 || edit_password_login.text.toString() == "") {
                toast("请输入合法的手机号和密码")
                return@setOnClickListener
            }
            showDialog()
            model.login(edit_phone_login.text.toString(), edit_password_login.text.toString(),
                    object : UserModel.UserListener {
                        override fun onSuccess() {
                            hideDialog()
                            startActivity(Intent(activity, MainActivity::class.java))
                            activity!!.finish()
                        }

                        override fun onError(e: AVException?) {
                            hideDialog()
                            log(e.toString())
                            when(e!!.code) {
                                210 -> toast("手机号密码不匹配")
                            }
                        }

                    })
        }
    }

    private fun showDialog() {
        dialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)// 设置进度条的形式为圆形转动的进度条
        dialog!!.setCancelable(true)// 设置是否可以通过点击Back键取消
        dialog!!.setCanceledOnTouchOutside(true)// 设置在点击Dialog外是否取消Dialog进度条
        dialog!!.setTitle("正在登录")
        dialog!!.setMessage("稍等")
        dialog!!.show()
    }

    fun hideDialog() {
        dialog!!.dismiss()
    }
}
