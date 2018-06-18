package com.bucai.torch.view.message

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.avos.avoscloud.im.v2.AVIMConversation
import com.bucai.torch.R
import com.bucai.torch.util.model.MessageModel
import com.bucai.torch.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_message.*

class MessageActivity : BaseActivity() , IMessage{
    private val presenter = MessagePresenter(this)

    override fun setIM() {
        presenter.getMessage(MessageModel.MessageListener { _, _, _ -> presenter.getConversation() })
    }

    override fun setList(list: List<AVIMConversation>) {
        val list = list as MutableList<AVIMConversation>
        Log.d("zxccvb", "setList: " + list.size)
        run {
            var i = 0
            while (i < list.size) {
                Log.d("zhuzhuzhu", "setList: " + list[i].name)
                if (list[i].lastMessage == null) {
                    list.removeAt(i)
                    i -= 1
                }
                Log.d("zxccvb", "setList: " + list.size)
                i++
            }
        }
        Log.d("///",list.toString())
        list_message.adapter = MessageAdapter(this, list, presenter)
    }

    override val contentViewId: Int = R.layout.activity_message

    override fun initData() {
        list_message.layoutManager = LinearLayoutManager(this)
        presenter.getConversation()
    }

}
