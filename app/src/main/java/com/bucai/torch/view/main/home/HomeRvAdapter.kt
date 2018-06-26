package com.bucai.torch.view.main.home

import android.annotation.SuppressLint
import android.app.Activity
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
import com.bucai.torch.bean.Lecturer
import com.bucai.torch.bean.Location
import com.bucai.torch.util.LocationUtils
import com.bucai.torch.util.ThreadPool
import com.bucai.torch.util.getStar
import com.bucai.torch.util.leancloud.GetDataModel
import com.bucai.torch.util.leancloud.IGetDataModel
import com.bucai.torch.util.leancloud.MessageModel
import com.bucai.torch.util.network.HttpUtil
import com.bucai.torch.view.message.MessageActivity
import com.bucai.torch.view.teacher.TeacherDetailActivity
import com.bumptech.glide.Glide
import com.jude.rollviewpager.RollPagerView
import rx.Subscriber

/**
 * Created by zia on 2018/5/25.
 */
class HomeRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val SEARCH = 0
    private val GALLARY = 1
    private val ADJUST = 2
    private val CLASSIFY = 3
    private val NORMAL = 4
    private var lecturers = ArrayList<Lecturer>()
    private val getDataModel: IGetDataModel = GetDataModel()

    fun freshLecturer(lecturers: ArrayList<Lecturer>) {
        this.lecturers = lecturers
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchHolder -> {
                MessageModel.getInstance().getConversation(object : MessageModel.QueryCallback {
                    override fun start() {
                    }

                    override fun error(e: AVIMException?) {
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
                        Log.d("///", list.toString())
                        if (list.isNotEmpty()) {
                            holder.message.setImageResource(R.mipmap.message_red)
                        }
                    }

                })
                val http = HttpUtil()
                http.getCity(LocationUtils.getInstance(holder.itemView.context)!!.showLocation()!!.longitude,
                        LocationUtils.getInstance(holder.itemView.context)!!.showLocation()!!.longitude)
                        .subscribe(object : Subscriber<Location>() {
                            override fun onNext(t: Location?) {
                                holder.location.text = t!!.data.address_components[1].short_name
                            }

                            override fun onCompleted() {
//                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onError(e: Throwable?) {
//                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                holder.location.text = "获取位置失败"
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
                ThreadPool.instance.cachedThreadPool.execute {
                    val leftUrl = getDataModel.getImageUrl("情绪调节左")
                    val rightUrl = getDataModel.getImageUrl("情绪调节右")
                    val context = holder.itemView.context
                    (context as Activity).runOnUiThread {
                        Glide.with(context).load(leftUrl).into(holder.leftImg)
                        Glide.with(context).load(rightUrl).into(holder.rightImg)
                    }
                }
            }
            is ClassifyHolder -> {

            }
            is NormalHolder -> {
                val lecturer = lecturers[position - 4]
                holder.star.text = getStar(lecturer.star)
                holder.name.text = lecturer.teaName
                holder.price.text = "${lecturer.price}元"
                Glide.with(holder.itemView.context).load(lecturer.head).into(holder.head)
                holder.itemView.setOnClickListener {
                    val intent = Intent(holder.itemView.context, TeacherDetailActivity::class.java)
                    intent.putExtra("lecturer", lecturer)
                    holder.itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return lecturers.size + 4
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

    class AdjustHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val leftImg = itemView.findViewById<ImageView>(R.id.item_home_rv_adjust_left)
        val rightImg = itemView.findViewById<ImageView>(R.id.item_home_rv_adjust_right)
    }

    class ClassifyHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class NormalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val head = itemView.findViewById<ImageView>(R.id.item_home_rv_normal_imageView)
        val name = itemView.findViewById<TextView>(R.id.item_home_rv_normal_name)
        val location = itemView.findViewById<TextView>(R.id.item_home_rv_normal_address)
        val price = itemView.findViewById<TextView>(R.id.item_home_rv_normal_price)
        val star = itemView.findViewById<TextView>(R.id.item_home_rv_normal_star)
    }

}
