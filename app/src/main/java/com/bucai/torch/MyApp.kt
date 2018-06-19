package com.bucai.torch

import android.support.multidex.MultiDexApplication
import com.avos.avoscloud.AVOSCloud
import com.bucai.torch.util.Apis

/**
 * Created by zxzhu
 *   2018/6/19.
 *   enjoy it !!
 */
class MyApp : MultiDexApplication(){
    override fun onCreate() {
        super.onCreate()
        AVOSCloud.initialize(this, Apis.LeanCloudId, Apis.LeanCloudKey)
        AVOSCloud.setDebugLogEnabled(true)
    }

}