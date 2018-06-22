package com.bucai.torch.view.message

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVFile
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.im.v2.AVIMConversation
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage
import com.bucai.torch.R
import com.bucai.torch.util.leancloud.GetDataModel
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by zxzhu on 2018/3/5.
 */
class MessageAdapter(var context: Context, var list: List<AVIMConversation>, var presenter: IMessagePresenter) : RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false)
        return MessageAdapter.MyViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder!!.itemView.tag = list[position].conversationId
        val i = list.size - position - 1
        val type = ""
        var json: JSONObject? = null
//                    Log.d("zxccvb", list.size()+"onBindViewHolder: " + list.get(i).getLastMessage().getContent());
        try {
            if (list[i].lastMessage != null) {
                json = JSONObject(list[i].lastMessage.content)

                //                        type = list.get(i).getLastMessage().getContent().substring(list.get(i).getLastMessage().getContent().length()-3);
            }

            when {
                json!!.getString("_lctype") == "-2" -> holder!!.content.text = "[图片]"
                json.getString("_lctype") == "-3" -> holder!!.content.text = "[语音消息]"
                else -> holder!!.content.text = (list[i].lastMessage as AVIMTextMessage).text
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val updateAt = list[i].updatedAt
        val now = Date(System.currentTimeMillis())
        val dayFormat = SimpleDateFormat("MM月dd日")
        val timeFormat = SimpleDateFormat("今天 HH:mm")
        var time: String? = null
        time = if (now.day == updateAt.day) {
            timeFormat.format(updateAt)
        } else {
            dayFormat.format(updateAt)
        }
        holder!!.time.text = time

        var username = list[i].members[0]
        Log.d("iioo", list[i].members.size.toString() + "  " + list[i].members[0] + "  onBindViewHolder:  " + list[i].creator)

        if (username == AVUser.getCurrentUser().username) {
            username = list[i].members[1]
        }
        val finalUsername = username
        presenter.getUserData(username, object : GetDataModel.GetDataListener<AVUser> {
            override fun onStart() {}

            override fun onError(e: AVException) {}

            override fun onFinish(users: List<AVUser>) {
                val head = users[0].get("head") as AVFile
                Log.d("iii", "onFinish: " + head.url)
                val tag = list[position].conversationId
                try {
                    Glide.with(context).load(head.url).into(holder.header)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                holder.nickname.text = users[0].get("nickname").toString()
                holder.itemView.setOnClickListener {
                    val intent = Intent(context, ChatActivity::class.java)
                    intent.putExtra("username", finalUsername)
                    intent.putExtra("id", list[i].conversationId)
                    intent.putExtra("leftUrl", head.url)
                    intent.putExtra("rightUrl", AVUser.getCurrentUser().getAVFile<AVFile>("head").url)
                    intent.putExtra("nickname", users[0].get("nickname").toString())
                    context.startActivity(intent)
                }

            }
        })
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val header = itemView!!.findViewById<CircleImageView>(R.id.img_message_item)!!
        val nickname = itemView!!.findViewById<TextView>(R.id.nickname_message_item)!!
        val content = itemView!!.findViewById<TextView>(R.id.content_message_item)!!
        val time = itemView!!.findViewById<TextView>(R.id.time_message_item)!!
    }
}