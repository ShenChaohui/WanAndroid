package com.sch.playandroid.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sch.playandroid.R
import com.sch.playandroid.entity.ArticleBean
import com.sch.playandroid.util.ImageLoad

class ArticleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //设置类型标志
    private val TYPE_HAVE_IMAGE = 0x00
    private val TYPE_NO_IMAGE = 0x01
    val list = mutableListOf<ArticleBean>()

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    interface OnCollectClickListener {
        fun onCollectClick(position: Int)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    private var onCollectClickListener: OnCollectClickListener? = null

    fun setOnCollectClickListener(listener: OnCollectClickListener) {
        this.onCollectClickListener = listener
    }

    class NoImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
        val tvTag: TextView = itemView.findViewById(R.id.tvTag)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvChapterName: TextView = itemView.findViewById(R.id.tvChapterName)
        val ivCollect: ImageView = itemView.findViewById(R.id.ivCollect)
    }

    class HaveImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivTitle: ImageView = itemView.findViewById(R.id.ivTitle)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDes: TextView = itemView.findViewById(R.id.tvDes)
        val tvNameData: TextView = itemView.findViewById(R.id.tvNameData)
        val ivCollect: ImageView = itemView.findViewById(R.id.ivCollect)

    }

    override fun getItemViewType(position: Int): Int {
        if (list.get(position).envelopePic != null && list.get(position).envelopePic.length > 0) {//有图片
            return TYPE_HAVE_IMAGE
        } else {
            return TYPE_NO_IMAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HAVE_IMAGE) {//有图
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_project, parent, false)
            return HaveImageViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_article, parent, false)
            return NoImageViewHolder(view)
        }
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
        val data = list.get(position)
        if (getItemViewType(position) == TYPE_HAVE_IMAGE) {
            val holder = holder as HaveImageViewHolder
            holder.tvTitle.text = data.title
            holder.tvDes.text = data.desc
            holder.tvNameData.text = data.niceDate + " | " + data.author
            ImageLoad.loadRadius(holder.ivTitle, data.envelopePic, 10)
            holder.ivCollect.apply {
                setOnClickListener {
                    onCollectClickListener?.onCollectClick(position)
                }
                if (data.collect) {
                    setImageResource(R.mipmap.article_collect)
                } else {
                    setImageResource(R.mipmap.article_un_collect)
                }
            }
        } else {
            val holder = holder as NoImageViewHolder
            holder.tvAuthor.text =
                if (!TextUtils.isEmpty(data.author)) data.author else data.shareUser
            holder.tvTag.text = if (data.type == 1) "置顶  " else ""
            holder.tvTitle.text = data.title
            holder.tvDate.text = data.niceDate
            holder.tvChapterName.text = data.superChapterName
            holder.ivCollect.apply {
                setOnClickListener {
                    onCollectClickListener?.onCollectClick(position)
                }
                if (data.collect) {
                    setImageResource(R.mipmap.article_collect)
                } else {
                    setImageResource(R.mipmap.article_un_collect)
                }
            }
        }
    }

    fun updata(list: MutableList<ArticleBean>?) {
        list?.let {
            this.list.clear()
            this.list.addAll(it)
            notifyDataSetChanged()
        }
    }
}