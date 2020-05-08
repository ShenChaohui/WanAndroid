package com.sch.wanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sch.wanandroid.R
import com.sch.wanandroid.entity.RankBean

/**
 * Created by Sch.
 * Date: 2020/4/26
 * description:排行榜列表适配器
 */
class RankAdapter : RecyclerView.Adapter<RankAdapter.RankViewHolder>() {
    val list = mutableListOf<RankBean>()
    fun updata(list: MutableList<RankBean>) {
        list?.let {
            this.list.clear()
            this.list.addAll(it)
            notifyDataSetChanged()
        }
    }

    class RankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivRank: ImageView = itemView.findViewById(R.id.ivRank)
        val tvRank: TextView = itemView.findViewById(R.id.tvRank)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvCoin: TextView = itemView.findViewById(R.id.tvCoin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rank, parent, false)
        return RankViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        val data = list[position]
        when (position) {
            0 -> {
                holder.ivRank.setImageResource(R.mipmap.gold_crown)
                holder.ivRank.visibility = View.VISIBLE
                holder.tvRank.visibility = View.INVISIBLE
            }
            1 -> {
                holder.ivRank.setImageResource(R.mipmap.silver_crown)
                holder.ivRank.visibility = View.VISIBLE
                holder.tvRank.visibility = View.INVISIBLE
            }
            2 -> {
                holder.ivRank.setImageResource(R.mipmap.cooper_crown)
                holder.ivRank.visibility = View.VISIBLE
                holder.tvRank.visibility = View.INVISIBLE
            }
            else -> {
                holder.ivRank.visibility = View.INVISIBLE
                holder.tvRank.visibility = View.VISIBLE
            }
        }
        holder.tvRank.text = "" + data.rank
        holder.tvName.text = data.username
        holder.tvCoin.text = "" + data.coinCount
    }


}