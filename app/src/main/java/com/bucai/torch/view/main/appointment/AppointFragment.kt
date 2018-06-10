package com.bucai.torch.view.main.appointment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bucai.torch.R
import kotlinx.android.synthetic.main.fragment_appoint.*

/**
 * 预约
 *
 */
class AppointFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appoint, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appointRV.layoutManager = LinearLayoutManager(context)
        appointRV.adapter = Adapter()
    }

    class Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view: View? = null
            when (getItemViewType(viewType)) {
                0 -> {
                    view = LayoutInflater.from(parent.context).inflate(R.layout.item_measure_head, parent, false)
                    return HeadHolder(view)
                }
                1 -> {
                    view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_rv_normal, parent, false)
                    return NormalHolder(view)
                }
            }
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_rv_normal, parent, false)
            return NormalHolder(view)
        }

        override fun getItemViewType(position: Int): Int {
            return position
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                holder.itemView.context.startActivity(
                        Intent(holder.itemView.context, TeaInfoActivity::class.java))
            }
        }

        class HeadHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        }

        class NormalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        }
    }
}
