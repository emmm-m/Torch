package com.bucai.torch.view.main.searchresult

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bucai.torch.R
import com.bucai.torch.bean.Lecturer
import com.bucai.torch.util.getStar
import com.bucai.torch.view.teacher.TeacherDetailActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_home_rv_normal.view.*

/**
 * Created by zxzhu
 *   2018/6/27.
 *   enjoy it !!
 */
class SearchResultRvAdapter(val data: MutableList<Lecturer>) : RecyclerView.Adapter<SearchResultRvAdapter.MyHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_rv_normal, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val lecturer = data[position]
        holder.itemView.item_home_rv_normal_star.text = getStar(lecturer.star)
        holder.itemView.item_home_rv_normal_name.text = lecturer.teaName
        holder.itemView.item_home_rv_normal_price.text = "${lecturer.price}元"
        holder.itemView.item_home_rv_normal_introduce.text = lecturer.simpleIntroduce
        holder.itemView.item_home_rv_normal_address.text = lecturer.address
        Glide.with(holder.itemView.context).load(lecturer.head).into(holder.itemView.item_home_rv_normal_imageView)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, TeacherDetailActivity::class.java)
            intent.putExtra("lecturer", lecturer)
            holder.itemView.context.startActivity(intent)
        }
    }

    class MyHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
}