package com.sch.playandroid.adapter

import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.sch.playandroid.R
import com.sch.playandroid.entity.ArticleBean
import com.sch.playandroid.util.ImageLoad

class MyArticleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val list = mutableListOf<ArticleBean>()

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    interface OnItemLongClickListener {
        fun onLongClick(position: Int)
    }

    private var onItemClickListener: OnItemClickListener? = null
    private var onItemLongClickListener: OnItemLongClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        this.onItemLongClickListener = listener
    }

    class MyArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
        val tvTag: TextView = itemView.findViewById(R.id.tvTag)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvChapterName: TextView = itemView.findViewById(R.id.tvChapterName)
        val ivCollect: ImageView = itemView.findViewById(R.id.ivCollect)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false)
        return MyArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onItemClickListener?.onClick(position)
            }
        })
        holder.itemView.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                onItemLongClickListener?.onLongClick(position)
                return true
            }

        })
        val data = list.get(position)
        val holder = holder as MyArticleViewHolder
        holder.tvAuthor.text =
            if (!TextUtils.isEmpty(data.author)) data.author else data.shareUser
        holder.tvTag.text = if (data.type == 1) "置顶  " else ""
        holder.tvTitle.text = HtmlCompat.fromHtml(data.title, HtmlCompat.FROM_HTML_MODE_LEGACY)
        holder.tvDate.text = data.niceDate
        holder.tvChapterName.text = data.superChapterName
        holder.ivCollect.visibility = View.GONE
    }

    fun updata(list: MutableList<ArticleBean>?) {
        list?.let {
            this.list.clear()
            this.list.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun deleteAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }
}