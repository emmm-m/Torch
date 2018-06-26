package com.bucai.torch.view.message

import android.util.Log
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.im.v2.AVIMConversation
import com.avos.avoscloud.im.v2.AVIMException
import com.bucai.torch.util.leancloud.GetDataModel
import com.bucai.torch.util.leancloud.IGetDataModel
import com.bucai.torch.util.leancloud.MessageModel

/**
 * Created by zxzhu on 2018/3/5.
 */
class MessagePresenter(var fragment: IMessage): IMessagePresenter {
    var model: IGetDataModel = GetDataModel()


    override fun getMessage(listener: MessageModel.MessageListener) {
        MessageModel.getInstance().setMessageListener(listener)
    }

    override fun getConversation() {
        MessageModel.getInstance().getConversation(object : MessageModel.QueryCallback {
            override fun start() {

            }

            override fun error(e: AVIMException) {
                Log.d("MessagePresenterTest", "error: " + e.code + e.message.toString())
            }

            override fun finish(list: List<AVIMConversation>) {
                fragment.setList(list)
            }
        })
    }

    override fun getUserData(username: String, listener: GetDataModel.GetDataListener<AVUser>) {

        model.gerUserData(username, listener)
    }
}