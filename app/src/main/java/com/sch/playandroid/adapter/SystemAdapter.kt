package com.sch.playandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.donkingliang.labels.LabelsView
import com.sch.playandroid.R
import com.sch.playandroid.entity.SystemBean
import com.sch.playandroid.entity.SystemChildrenBean

/**
 * Created by Sch.
 * Date: 2020/4/23
 * description:
 */
class SystemAdapter : RecyclerView.Adapter<SystemAdapter.SystemAdapterHolder>() {
    private var list = ArrayList<SystemBean>()
    fun updata(list: ArrayList<SystemBean>) {
        this.list = list
        notifyDataSetChanged()
    }

    interface OnSystemClickListener {
        fun onCollectClick(systemPosition: Int, systemChildrenPosition: Int)
    }

    private var ststemClickListener: OnSystemClickListener? = null

    fun setStstemClickListener(listener: OnSystemClickListener) {
        this.ststemClickListener = listener
    }


    class SystemAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val labelsView: LabelsView = itemView.findViewById(R.id.labelsView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SystemAdapter.SystemAdapterHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_system, parent, false)
        return SystemAdapterHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SystemAdapterHolder, position: Int) {
        val data = list.get(position)
        holder.tvTitle.text = data.name
        holder.labelsView.setLabels(data.children,
            object : LabelsView.LabelTextProvider<SystemChildrenBean> {
                override fun getLabelText(
                    label: TextView,
                    position: Int,
                    data: SystemChildrenBean
                ): CharSequence {
                    return data.name
                }
            })
        holder.labelsView.setOnLabelClickListener { label, data, childrenPosition ->
            ststemClickListener?.onCollectClick(position, childrenPosition)
        }
    }
}