package com.bucai.torch

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bucai.torch.view.main.MainActivity

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this@StartActivity, MainActivity::class.java))
        finish()
    }
}
