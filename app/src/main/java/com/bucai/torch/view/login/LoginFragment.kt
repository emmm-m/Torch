package com.bucai.torch.view.login


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

    val model: IUserModel = UserModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logiin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLogin()
    }

    private fun setLogin() {
        btn_login.setOnClickListener {
            if (edit_phone_login.text.length != 11 || edit_password_login.text.toString() == "") {
                toast("请输入合法的手机号和密码")
                return@setOnClickListener
            }
            model.login(edit_phone_login.text.toString(), edit_password_login.text.toString(),
                    object : UserModel.UserListener {
                        override fun onSuccess() {
                            startActivity(Intent(activity, MainActivity::class.java))
                            activity!!.finish()
                        }

                        override fun onError(e: AVException?) {
                            log(e.toString())
                            when(e!!.code) {
                                210 -> toast("手机号密码不匹配")
                            }
                        }

                    })
        }
    }
}
