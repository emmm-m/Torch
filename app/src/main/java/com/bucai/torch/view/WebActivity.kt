package com.bucai.torch.view

import android.view.View
import com.bucai.torch.R
import com.bucai.torch.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : BaseActivity() {
    override val contentViewId: Int = R.layout.activity_web

    override fun initData() {
        val title = intent.getStringExtra("tittle")
        val url = intent.getStringExtra("url")
        back_header.visibility = View.VISIBLE
        header_title.text = title
        back_header.setOnClickListener { finish() }
        webView.loadUrl(url)
    }

}