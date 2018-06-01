package com.bucai.torch.view.main.home

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
import com.bucai.torch.R
import com.bucai.torch.util.model.GetDataModel
import com.bucai.torch.util.model.IGetDataModel
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
    private var views = ArrayList<View>()
    private val getDataModel: IGetDataModel = GetDataModel()

    fun setGalleryViews(list: ArrayList<View>) {
        this.views = list
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
            is SearchHolder -> holder.location.text = "重庆"
            is GallaryHolder -> {
                getDataModel.getRollPics(object : GetDataModel.GetDataListener<AVObject>{
                    override fun onStart() {
//                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onError(e: AVException?) {
                        Log.d("MPtest", e.toString())
                    }

                    override fun onFinish(list: MutableList<AVObject>?) {
                        holder.viewPager.setAdapter(RollPicsAdapter(list))                    }
                })

            }
            is AdjustHolder -> {

            }
            is ClassifyHolder -> {

            }
            else -> {

            }
        }
    }

    override fun getItemCount(): Int {
        return 30
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
        var imageView: ImageView = itemView.findViewById(R.id.item_home_rv_search_imageView)
    }

    class GallaryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewPager: RollPagerView = itemView.findViewById(R.id.item_home_rv_gallary_viewPager)
//        val pagerCircle: PagerCircle = itemView.findViewById(R.id.item_home_rv_gallary_pagerCircle)
    }

    class AdjustHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class ClassifyHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class NormalHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
