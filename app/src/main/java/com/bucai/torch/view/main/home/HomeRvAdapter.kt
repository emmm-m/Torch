package com.bucai.torch.view.main.home

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.im.v2.AVIMConversation
import com.avos.avoscloud.im.v2.AVIMException
import com.bucai.torch.R
import com.bucai.torch.bean.Teacher
import com.bucai.torch.util.model.GetDataModel
import com.bucai.torch.util.model.IGetDataModel
import com.bucai.torch.util.model.MessageModel
import com.bucai.torch.view.message.MessageActivity
import com.bumptech.glide.Glide
import com.jude.rollviewpager.RollPagerView

/**
 * Created by zia on 2018/5/25.
 */
class HomeRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val SEARCH = 0
    private val GALLARY = 1
    private val ADJUST = 2
    private val CLASSIFY = 3
    private val NORMAL = 4
    private var teachers = ArrayList<Teacher>()
    private val getDataModel: IGetDataModel = GetDataModel()

    fun freshTeacher(teachers: ArrayList<Teacher>) {
        this.teachers = teachers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View? = null
        when (getItemViewType(viewType)) {
            SEARCH -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_rv_search, parent, false)
                return SearchHolder(view)
            }
            GALLARY -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_rv_gallary, parent, false)
                return GallaryHolder(view)
            }
            ADJUST -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_rv_adjust, parent, false)
                return AdjustHolder(view)
            }
            CLASSIFY -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_rv_classify, parent, false)
                return ClassifyHolder(view)
            }
            NORMAL -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_rv_normal, parent, false)
                return NormalHolder(view)
            }
        }
        view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_rv_normal, parent, false)
        return NormalHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchHolder -> {
                MessageModel.getInstance().getConversation(object : MessageModel.QueryCallback{
                    override fun start() {
//                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun error(e: AVIMException?) {
//                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun finish(list: MutableList<AVIMConversation>?) {
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
                        if (list.isNotEmpty()) {
                            holder.message.setImageResource(R.mipmap.message_red)
                        }
                    }

                })
                holder.location.text = "重庆"
                holder.message.setOnClickListener {
                    holder.itemView.context.startActivity(Intent(holder.itemView.context, MessageActivity::class.java))
                }
            }
            is GallaryHolder -> {
                getDataModel.getRollPics(object : GetDataModel.GetDataListener<AVObject> {
                    override fun onStart() {
//                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onError(e: AVException?) {
                        Log.d("MPtest", e.toString())
                    }

                    override fun onFinish(list: MutableList<AVObject>?) {
                        holder.viewPager.setAdapter(RollPicsAdapter(list))
                    }
                })

            }
            is AdjustHolder -> {

            }
            is ClassifyHolder -> {

            }
            is NormalHolder -> {
                val teacher = teachers[position - 4]
                holder.name.text = teacher.name.toString()
                holder.price.text = teacher.price
                Glide.with(holder.itemView.context).load(teacher.head).into(holder.head)
            }
        }
    }

    override fun getItemCount(): Int {
        return teachers.size + 4
    }

    override fun getItemViewType(position: Int): Int {
        when (position) {
            0 -> return SEARCH
            1 -> return GALLARY
            2 -> return ADJUST
            3 -> return CLASSIFY
        }
        return NORMAL
    }

    class SearchHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var location: TextView = itemView.findViewById(R.id.item_home_rv_search_location)
        var editText: EditText = itemView.findViewById(R.id.item_home_rv_search_editText)
        var message: ImageView = itemView.findViewById(R.id.item_home_rv_search_message)
    }

    class GallaryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewPager: RollPagerView = itemView.findViewById(R.id.item_home_rv_gallary_viewPager)
//        val pagerCircle: PagerCircle = itemView.findViewById(R.id.item_home_rv_gallary_pagerCircle)
    }

    class AdjustHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class ClassifyHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class NormalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val head = itemView.findViewById<ImageView>(R.id.item_home_rv_normal_imageView)
        val name = itemView.findViewById<TextView>(R.id.item_home_rv_normal_name)
        val location = itemView.findViewById<TextView>(R.id.item_home_rv_normal_address)
        val price = itemView.findViewById<TextView>(R.id.item_home_rv_normal_price)
        val star = itemView.findViewById<TextView>(R.id.item_home_rv_normal_star)
    }

}
