package com.bucai.torch.view.main.information

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.avos.avoscloud.AVFile
import com.avos.avoscloud.AVObject
import com.bucai.torch.R
import com.bucai.torch.util.uiutils.MyImageView
import com.bucai.torch.view.WebActivity
import com.bumptech.glide.Glide
import java.util.*

/**
 * Created by zxzhu
 *   2018/6/10.
 *   enjoy it !!
 */
class InfoRvAdapter(var newsData: MutableList<AVObject>, val mContext: Context) : RecyclerView.Adapter<InfoRvAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view: View? = LayoutInflater.from(parent.context).inflate(R.layout.item_information_rv, parent, false)
        return MyViewHolder(view!!)
    }

    override fun getItemCount(): Int = newsData.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj = newsData[position]
        holder.tittle.text = obj.get("tittle").toString()
        val date: Date = obj.get("createdAt") as Date
        holder.date.text = "" + date.month + "月" + date.day + "日"
        holder.seen.text = obj.get("seen").toString() + "次浏览"
        val picUrl = (obj.get("pic") as AVFile).url
        if (picUrl != null) Glide.with(mContext).load(picUrl).into(holder.pic)
        //点击事件，计数器++
        holder.itemView.setOnClickListener {
            val intent = Intent(mContext, WebActivity::class.java)
            intent.putExtra("tittle", obj.get("tittle").toString())
            intent.putExtra("url", obj.get("url").toString())
            mContext.startActivity(intent)
            obj.increment("seen")
            obj.isFetchWhenSave = true
            obj.saveInBackground()
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tittle: TextView = itemView.findViewById(R.id.tittle_item_info)
        var seen: TextView = itemView.findViewById(R.id.count_seen_item_info)
        var date: TextView = itemView.findViewById(R.id.date_item_info)
        var pic: MyImageView = itemView.findViewById(R.id.pic_item_info)
    }
}