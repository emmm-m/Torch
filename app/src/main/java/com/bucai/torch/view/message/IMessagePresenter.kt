package com.bucai.torch.view.message

import com.avos.avoscloud.AVUser
import com.bucai.torch.util.leancloud.GetDataModel
import com.bucai.torch.util.leancloud.MessageModel

/**
 * Created by zxzhu on 2018/3/5.
 */
interface IMessagePresenter {
    fun getMessage(listener: MessageModel.MessageListener)
    fun getConversation()
    fun getUserData(username: String, listener: GetDataModel.GetDataListener<AVUser>)
}