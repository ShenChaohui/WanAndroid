package com.sch.playandroid.ui.main.discover.system

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sch.lolcosmos.base.BaseFragment
import com.sch.playandroid.R
import com.sch.playandroid.adapter.SystemAdapter
import com.sch.playandroid.base.LazyFragment
import com.sch.playandroid.entity.SystemBean
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.loadingTip
import kotlinx.android.synthetic.main.fragment_list.rvList
import kotlinx.android.synthetic.main.fragment_refresh_list.*

/**
 * 体系
 */

class SystemFragment : LazyFragment(), SystemContract.ISystemView {
    private val adapter by lazy { SystemAdapter() }
    private val systemList by lazy { ArrayList<SystemBean>() }
    private val presenter by lazy { SystemPresenterImpl(this) }
    override fun lazyInit() {
        rvList.layoutManager = LinearLayoutManager(context)
        rvList.layoutManager
        rvList.adapter = adapter
        adapter.setStstemClickListener(object : SystemAdapter.OnSystemClickListener {
            override fun onCollectClick(systemPosition: Int, systemChildrenPosition: Int) {
                Log.e(
                    "test",
                    systemList.get(systemPosition).children.get(systemChildrenPosition).name
                )
            }
        })
        presenter.getSystemData()
        //加载中动画
        loadingTip.loading()
        // 设置无网络时重新加载点击事件
        loadingTip.setReloadListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                loadingTip.loading()
                presenter.getSystemData()
            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    override fun setSystemData(list: List<SystemBean>) {
        loadingTip.dismiss()
        systemList.clear()
        systemList.addAll(list)
        adapter.updata(systemList)
    }

    override fun setError(ex: String) {
        loadingTip.showInternetError()
    }
}