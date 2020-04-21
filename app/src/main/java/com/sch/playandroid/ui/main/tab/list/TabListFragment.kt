package com.sch.playandroid.ui.main.tab.list

import android.os.Bundle
import com.sch.lolcosmos.base.BaseFragment
import com.sch.playandroid.R
import com.sch.playandroid.adapter.ArticleAdapter
import com.sch.playandroid.entity.ArticleBean
import kotlinx.android.synthetic.main.fragment_tab_list.*

/**
 * Created by Sch.
 * Date: 2020/4/21
 * description:
 */
class TabListFragment : BaseFragment(), TabListContract.ITabListView {
    val adapter by lazy { ArticleAdapter() }
    private val presenterImpl by lazy { TabListPresenterImpl(this) }
    override fun init(savedInstanceState: Bundle?) {
        rvTabList.adapter = adapter
        presenterImpl.getListData()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_tab_list
    }

    override fun setListData(list: List<ArticleBean>) {
        adapter.updata(list)
    }
}