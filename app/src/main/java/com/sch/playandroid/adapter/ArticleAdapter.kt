package com.sch.playandroid.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sch.playandroid.R
import com.sch.playandroid.entity.ArticleBean

class ArticleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //设置类型标志
    private val TYPE_HAVE_IMAGE = 0x00
    private val TYPE_NO_IMAGE = 0x01

    val list = ArrayList<ArticleBean>()
    var mContext: Context? = null
    var inflater: LayoutInflater? = null

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class noImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvChapterName: TextView = itemView.findViewById(R.id.tvChapterName)
    }

    class haveImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
        val view: View
        val viewHolder: RecyclerView.ViewHolder
        if (mContext == null) {
            mContext = parent.context
        }
        if (inflater == null) {
            inflater = LayoutInflater.from(mContext)
        }
        if (viewType == TYPE_HAVE_IMAGE) {//有图
            view = inflater!!.inflate(R.layout.item_project, parent, false)
            viewHolder = haveImageViewHolder(view)
        } else {
            view = inflater!!.inflate(R.layout.item_home_article, parent, false)
            viewHolder = noImageViewHolder(view)
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list.get(position)
        Log.e("test", "getItemViewType: " + getItemViewType(position))
        if (getItemViewType(position) == TYPE_HAVE_IMAGE) {
            (holder as haveImageViewHolder).tvTitle.text = data.title
            (holder as haveImageViewHolder).tvDes.text = data.desc
            (holder as haveImageViewHolder).tvNameData.text = data.niceDate + "  " + data.author
            mContext?.let {
                Glide.with(it).load(data.envelopePic)
                    .into((holder as haveImageViewHolder).ivTitle)
            }
        } else {
            (holder as noImageViewHolder).tvAuthor.text = data.shareUser
            (holder as noImageViewHolder).tvTitle.text = data.title
            (holder as noImageViewHolder).tvDate.text = data.niceDate
            (holder as noImageViewHolder).tvChapterName.text = data.superChapterName
        }

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                listener?.onClick(position)
            }
        })
    }

    fun updata(list: List<ArticleBean>?) {
        list?.let {
            this.list.clear()
            this.list.addAll(it)
            notifyDataSetChanged()
        }
    }
}