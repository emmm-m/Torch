package com.bucai.torch.view.main.mine


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avos.avoscloud.AVUser

import com.bucai.torch.R
import com.bucai.torch.view.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * 我的
 *
 */
class MineFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_logout.setOnClickListener {
            AVUser.logOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity!!.finish()
        }
    }
}
