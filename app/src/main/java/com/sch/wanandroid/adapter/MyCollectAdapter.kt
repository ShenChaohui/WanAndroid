package com.sch.wanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.sch.wanandroid.R
import com.sch.wanandroid.entity.CollectBean

/**
 * Created by Sch.
 * Date: 2020/4/26
 * description:
 */
class MyCollectAdapter : RecyclerView.Adapter<MyCollectAdapter.CollectViewHolder>() {
    val list = mutableListOf<CollectBean>()
    fun updata(list: MutableList<CollectBean>) {
        list?.let {
            this.list.clear()
            this.list.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    interface OnCollectClickListener {
        fun onCollectClick(position: Int)
    }

    private var onCollectClickListener: OnCollectClickListener? = null

    fun setOnCollectClickListener(listener: OnCollectClickListener) {
        this.onCollectClickListener = listener
    }

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    class CollectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvChapterName: TextView = itemView.findViewById(R.id.tvChapterName)
        val ivCollect: ImageView = itemView.findViewById(R.id.ivCollect)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false)
        return CollectViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CollectViewHolder, position: Int) {
        val data = list[position]
        holder.itemView.setOnClickListener {
            onItemClickListener?.onClick(position)
        }
        holder.tvAuthor.text = data.author
        holder.tvTitle.text = HtmlCompat.fromHtml(data.title, HtmlCompat.FROM_HTML_MODE_LEGACY)
        holder.tvDate.text = data.niceDate
        holder.tvChapterName.text = data.chapterName
        holder.ivCollect.apply {
            setOnClickListener {
                onCollectClickListener?.onCollectClick(position)
            }
            setImageResource(R.mipmap.article_collect)
        }
    }


}