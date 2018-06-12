package com.bucai.torch.view.main.appointment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.FindCallback
import com.bucai.torch.R
import com.bucai.torch.bean.Teacher
import com.bucai.torch.util.model.GetDataModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_appoint.*

/**
 * 预约
 *
 */
class AppointFragment : Fragment() {

    private val adapter = Adapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_appoint, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appointRV.layoutManager = LinearLayoutManager(context)
        appointRV.adapter = adapter
        GetDataModel().getTeachersList(object  : GetDataModel.GetDataListener<Teacher>{
            override fun onStart() {

            }

            override fun onError(e: AVException?) {
                e?.printStackTrace()
            }

            override fun onFinish(list: MutableList<Teacher>?) {
                Log.e("zia",list?.toString())
                this@AppointFragment.activity?.runOnUiThread {
                    adapter.fresh(list as ArrayList<Teacher>)
                }
            }
        })
    }

    class Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private var list = ArrayList<Teacher>()
        private val NORMAL = -1
        private val HEAD = -2

        fun fresh(list: ArrayList<Teacher>) {
            this.list = list
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view: View? = null
            when (viewType) {
                HEAD -> {
                    view = LayoutInflater.from(parent.context).inflate(R.layout.item_measure_head, parent, false)
                    return HeadHolder(view)
                }
                NORMAL -> {
                    view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_rv_normal, parent, false)
                    return NormalHolder(view)
                }
            }
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_rv_normal, parent, false)
            return NormalHolder(view)
        }

        override fun getItemViewType(position: Int): Int {
            if (position == 0) return HEAD
            return NORMAL
        }

        override fun getItemCount(): Int {
            return list.size + 1
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder) {
                is NormalHolder -> {
                    val teacher = list[position - 1]
                    holder.itemView.setOnClickListener {
                        val intent = Intent(holder.itemView.context, TeaInfoActivity::class.java)
                        intent.putExtra("teacher", teacher)
                        holder.itemView.context.startActivity(intent)
                    }
                    holder.name.text = teacher.name.toString()
                    holder.price.text = teacher.price
                    Glide.with(holder.itemView.context).load(teacher.head).into(holder.head)
                }
                is HeadHolder -> {

                }
            }
        }

        class HeadHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        }

        class NormalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val head = itemView.findViewById<ImageView>(R.id.item_home_rv_normal_imageView)
            val name = itemView.findViewById<TextView>(R.id.item_home_rv_normal_name)
            val location = itemView.findViewById<TextView>(R.id.item_home_rv_normal_address)
            val price = itemView.findViewById<TextView>(R.id.item_home_rv_normal_price)
            val star = itemView.findViewById<TextView>(R.id.item_home_rv_normal_star)
        }
    }
}
