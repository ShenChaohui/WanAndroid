package com.sch.wanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sch.wanandroid.R
import com.sch.wanandroid.entity.CoinRecordBean

/**
 * Created by Sch.
 * Date: 2020/4/26
 * description:我的积分列表适配器
 */
class CoinRecordAdapter : RecyclerView.Adapter<CoinRecordAdapter.CoinRecordViewHolder>() {
    val list = mutableListOf<CoinRecordBean>()
    fun updata(list: MutableList<CoinRecordBean>) {
        list?.let {
            this.list.clear()
            this.list.addAll(it)
            notifyDataSetChanged()
        }
    }

    class CoinRecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDesc: TextView = itemView.findViewById(R.id.tvDesc)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvAddCoinCount: TextView = itemView.findViewById(R.id.tvAddCoinCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinRecordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coin, parent, false)
        return CoinRecordViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CoinRecordViewHolder, position: Int) {
        val data = list[position]
        val desc = data.desc.split("积分：")[1]
        val date = data.desc.substring(0, 19)
        holder.tvDesc.text = "${data.reason}积分$desc"
        holder.tvAddCoinCount.text = "${data.coinCount}"
        holder.tvDate.text = date
    }


}