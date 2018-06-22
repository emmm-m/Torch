package com.bucai.torch.view.message

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.avos.avoscloud.im.v2.AVIMConversation
import com.bucai.torch.R
import com.bucai.torch.util.leancloud.MessageModel
import com.bucai.torch.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.header_include.*

class MessageActivity : BaseActivity(), IMessage{
    private val presenter = MessagePresenter(this)

    override fun setIM() {
        presenter.getMessage(MessageModel.MessageListener { _, _, _ -> presenter.getConversation() })
    }

    override fun setList(list: List<AVIMConversation>) {
        val list = list as MutableList<AVIMConversation>
        run {
            var i = 0
            while (i < list.size) {
                if (list[i].lastMessage == null) {
                    list.removeAt(i)
                    i -= 1
                }
                i++
            }
        }
        Log.d("///",list.toString())
        if (list.isEmpty()) toast("还没有消息")
        list_message.adapter = MessageAdapter(this, list, presenter)
    }

    override val contentViewId: Int = R.layout.activity_message

    override fun initData() {
        list_message.layoutManager = LinearLayoutManager(this)
        presenter.getConversation()
        header_title.text = "消息"
        back_header.visibility = View.VISIBLE
        back_header.setOnClickListener { finish() }
    }

}
