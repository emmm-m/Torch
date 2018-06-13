package com.bucai.torch.view.login

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avos.avoscloud.AVException
import com.bucai.torch.R
import com.bucai.torch.util.model.IUserModel
import com.bucai.torch.util.model.UserModel
import com.bucai.torch.view.main.MainActivity
import com.bucai.torch.view.main.home.toast
import kotlinx.android.synthetic.main.fragment_signin.*


/**
 * Created by zxzhu
 *   2018/6/12.
 *   enjoy it !!
 */
class SignFragment : Fragment() {
    val model: IUserModel = UserModel()
    private var counter: CountDownTimer? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSign()
        setGetCode()
    }

    private fun setSign() {
        btn_sign.setOnClickListener {
            if (edit_password_sign.text.length < 6) {
                toast("密码过短")
                return@setOnClickListener
            }
            if (edit_verification_sign.text.toString() == "") {
                toast("请输入短信验证码")
                return@setOnClickListener
            }
            if (!check_agree.isChecked) {
                toast("请同意协议")
                return@setOnClickListener
            }
            model.signUpWithPhone(edit_phone_sign.text.toString(), edit_verification_sign.text.toString(),
                    edit_password_sign.text.toString(), object : UserModel.UserListener {
                override fun onSuccess() {
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity!!.finish()
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onError(e: AVException?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
        }
    }

    private fun setGetCode() {
        btn_get_verification.setOnClickListener {
            if (edit_phone_sign.text.length != 11) {
                toast("请输入正确的手机号")
                return@setOnClickListener
            }
            model.getCode(edit_phone_sign.text.toString(), object : UserModel.UserListener {
                override fun onSuccess() {
                    setTimer()
                }

                override fun onError(e: AVException?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
        }
    }

    private fun setTimer() {
        counter = object : CountDownTimer((60 * 1000).toLong(), 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                btn_get_verification.text = "" + millisUntilFinished / 1000 + "秒"
                btn_get_verification.setTextColor(Color.parseColor("#607D8B"))
                btn_get_verification.setOnClickListener(null)
            }

            override fun onFinish() {
                btn_get_verification.text = "获取验证码"
                btn_get_verification.setTextColor(Color.parseColor("#FD6438"))
                btn_get_verification.setOnClickListener {
                    setGetCode()
                }
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (counter != null) counter!!.cancel()
    }
}