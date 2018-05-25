package com.bucai.torch

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bucai.torch.util.LogUtil
import com.bucai.torch.view.main.MainActivity
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import com.zia.toastex.ToastEx

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndPermission.with(this@StartActivity)
                .runtime()
                .permission(Permission.Group.STORAGE,
                        Permission.Group.CAMERA,
                        Permission.Group.MICROPHONE)
                .onGranted {
                    LogUtil.e("onGranted")
                    startActivity(Intent(this@StartActivity, MainActivity::class.java))
                    finish()
                }
                .onDenied {
                    LogUtil.e("onDenied")
                    ToastEx.warning(this@StartActivity, "权限不足").show()
                    finish()
                }
                .start()
    }
}
