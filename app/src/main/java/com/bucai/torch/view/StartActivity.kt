package com.bucai.torch.view

import android.content.Intent
import com.avos.avoscloud.AVUser
import com.bucai.torch.R
import com.bucai.torch.util.LogUtil
import com.bucai.torch.view.base.BaseActivity
import com.bucai.torch.view.login.LoginActivity
import com.bucai.torch.view.main.MainActivity
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import com.zia.toastex.ToastEx

class StartActivity : BaseActivity() {
    override val contentViewId: Int = R.layout.activity_start

    override fun initData() {

        Thread(Runnable {
            try {
                Thread.sleep(500)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            AndPermission.with(this@StartActivity)
                    .runtime()
                    .permission(Permission.Group.STORAGE,
                            Permission.Group.CAMERA,
                            Permission.Group.LOCATION)
                    .onGranted {
                        LogUtil.e("onGranted")
                        if (AVUser.getCurrentUser() != null) {
                            startActivity(Intent(this@StartActivity, MainActivity::class.java))
                            finish()
                        } else {
                            startActivity(Intent(this@StartActivity, LoginActivity::class.java))
                            finish()
                        }
                    }
                    .onDenied {
                        LogUtil.e("onDenied")
                        ToastEx.warning(this@StartActivity, "权限不足").show()
                        finish()
                    }
                    .start()
        }).start()
    }

}
