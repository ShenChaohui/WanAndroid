package com.sch.playandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.donkingliang.labels.LabelsView
import com.sch.playandroid.R
import com.sch.playandroid.entity.NavigationBean
import com.sch.playandroid.entity.NavigationChildrenBean

/**
 * Created by Sch.
 * Date: 2020/4/23
 * description:
 */
class NavigationAdapter : RecyclerView.Adapter<NavigationAdapter.NavigationAdapterHolder>() {
    private var list = mutableListOf<NavigationBean>()
    fun updata(list: MutableList<NavigationBean>) {
        this.list = list
        notifyDataSetChanged()
    }

    interface OnNavigationClickListener {
        fun onCollectClick(navigationPosition: Int, navigationChildrenPosition: Int)
    }

    private var navigationClickListener: OnNavigationClickListener? = null

    fun setStstemClickListener(listener: OnNavigationClickListener) {
        this.navigationClickListener = listener
    }


    class NavigationAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val labelsView: LabelsView = itemView.findViewById(R.id.labelsView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NavigationAdapter.NavigationAdapterHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_system, parent, false)
        return NavigationAdapterHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NavigationAdapterHolder, position: Int) {
        val data = list.get(position)
        holder.tvTitle.text = data.name
        holder.labelsView.setLabels(data.articles,
            object : LabelsView.LabelTextProvider<NavigationChildrenBean> {
                override fun getLabelText(
                    label: TextView,
                    position: Int,
                    data: NavigationChildrenBean
                ): CharSequence {
                    return data.title
                }
            })
        holder.labelsView.setOnLabelClickListener { label, data, childrenPosition ->
            navigationClickListener?.onCollectClick(position, childrenPosition)
        }
    }
}