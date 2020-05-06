package com.sch.wanandroid.ui.main.discover.system

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sch.wanandroid.R
import com.sch.wanandroid.adapter.SystemAdapter
import com.sch.wanandroid.base.LazyFragment
import com.sch.wanandroid.constants.Constants
import com.sch.wanandroid.entity.SystemBean
import com.sch.wanandroid.ui.main.discover.system.list.SystemListActivity
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * 体系
 */

class SystemFragment : LazyFragment<SystemContract.IPresenter>(), SystemContract.IView {
    private val systemAdapter by lazy { SystemAdapter() }
    private val systemList by lazy { mutableListOf<SystemBean>() }
    override fun lazyInit() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = systemAdapter
        recyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER//取消滑动到顶部边界越界效果

        initListener()
        //加载中动画
        loadingTip.loading()
        mPresenter?.getSystemData()
    }

    private fun initListener() {
        systemAdapter.setStstemClickListener(object : SystemAdapter.OnSystemClickListener {
            override fun onCollectClick(systemPosition: Int, systemChildrenPosition: Int) {
                intent(Bundle().apply {
                    putInt(
                        Constants.SYSTEM_ID,
                        systemList[systemPosition].children[systemChildrenPosition].id
                    )
                    putString(
                        Constants.SYSTEM_TITLE,
                        systemList[systemPosition].children[systemChildrenPosition].name
                    )
                }, SystemListActivity::class.java, false)
            }
        })
        // 设置无网络时重新加载点击事件
        loadingTip.setReloadListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                loadingTip.loading()
                mPresenter?.getSystemData()
            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    override fun setSystemData(list: MutableList<SystemBean>) {
        loadingTip.dismiss()
        systemList.clear()
        systemList.addAll(list)
        systemAdapter.updata(systemList)
    }

    override fun onError(ex: String) {
        loadingTip.showInternetError()
    }

    override fun createPresenter(): SystemContract.IPresenter? {
        return SystemPresenterImpl()
    }
}