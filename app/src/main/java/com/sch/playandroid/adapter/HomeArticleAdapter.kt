package com.sch.playandroid.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sch.playandroid.entity.HomeArticleBean
import com.sch.playandroid.widget.HomeArticleItemView

class HomeArticleAdapter : RecyclerView.Adapter<HomeArticleAdapter.HomeViewHolder>() {
    val list = ArrayList<HomeArticleBean>()

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(HomeArticleItemView(parent.context))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        // 如果是最后一条，不需要刷新view
        if (position == list.size) return

        val data = list.get(position)
        val itemView = holder.itemView as HomeArticleItemView
        itemView.setData(data)
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                listener?.onClick(position)
            }
        })

    }

    fun updata(list: List<HomeArticleBean>?) {
        list?.let {
            this.list.clear()
            this.list.addAll(it)
            notifyDataSetChanged()
        }
    }
}