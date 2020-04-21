package com.sch.playandroid.adapter

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
    val list = ArrayList<ArticleBean>()

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class NoImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvChapterName: TextView = itemView.findViewById(R.id.tvChapterName)
    }

    class HaveImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivTitle: ImageView = itemView.findViewById(R.id.ivTitle)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDes: TextView = itemView.findViewById(R.id.tvDes)
        val tvNameData: TextView = itemView.findViewById(R.id.tvNameData)
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
                .inflate(R.layout.item_home_article, parent, false)
            return NoImageViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                listener?.onClick(position)
            }
        })
        val data = list.get(position)
        if (getItemViewType(position) == TYPE_HAVE_IMAGE) {
            (holder as HaveImageViewHolder).tvTitle.text = data.title
            (holder as HaveImageViewHolder).tvDes.text = data.desc
            (holder as HaveImageViewHolder).tvNameData.text = data.niceDate + "  " + data.author
            ImageLoad.loadRadius((holder as HaveImageViewHolder).ivTitle, data.envelopePic, 10)
        } else {
            (holder as NoImageViewHolder).tvAuthor.text = data.shareUser
            (holder as NoImageViewHolder).tvTitle.text = data.title
            (holder as NoImageViewHolder).tvDate.text = data.niceDate
            (holder as NoImageViewHolder).tvChapterName.text = data.superChapterName
        }
    }

    fun updata(list: List<ArticleBean>?) {
        list?.let {
            this.list.clear()
            this.list.addAll(it)
            notifyDataSetChanged()
        }
    }
}