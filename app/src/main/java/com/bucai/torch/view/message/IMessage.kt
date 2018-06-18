package com.bucai.torch.view.message

import com.avos.avoscloud.im.v2.AVIMConversation

/**
 * Created by zxzhu on 2018/3/5.
 */
interface IMessage {
    fun setIM()
    fun setList(list: List<AVIMConversation>)
}