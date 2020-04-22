package com.sch.playandroid.ui.main.tab.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sch.lolcosmos.base.BaseFragment
import com.sch.playandroid.R
import com.sch.playandroid.adapter.ArticleAdapter
import com.sch.playandroid.base.LazyFragment
import com.sch.playandroid.constants.Constants
import com.sch.playandroid.entity.ArticleBean
import com.sch.playandroid.ui.web.WebActivity
import kotlinx.android.synthetic.main.fragment_tab_list.*
import kotlinx.android.synthetic.main.fragment_tab_list.loadingTip

/**
 * Created by Sch.
 * Date: 2020/4/21
 * description:
 */
class TabListFragment : LazyFragment(), TabListContract.ITabListView {
    private val adapter by lazy { ArticleAdapter() }
    private val presenterImpl by lazy { TabListPresenterImpl(this) }
    private var curPage = 1
    val articleList by lazy { ArrayList<ArticleBean>() }

    override fun lazyInit() {
        val cid = arguments?.getInt("id")!!
        val type = arguments?.getInt("type")!!
        Log.e("TabListFragment", arguments?.getString("name"))
        rvTabList.layoutManager = LinearLayoutManager(context)
        rvTabList.adapter = adapter
        adapter.setOnItemClickListener(object : ArticleAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                intent(Bundle().apply {
                    putString(Constants.WEB_URL, articleList[position].link)
                    putString(Constants.WEB_TITLE, articleList[position].title)
                }, WebActivity::class.java, false)
            }
        })
        //加载中动画
        loadingTip.loading()
        // 设置无网络时重新加载点击事件
        loadingTip.setReloadListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                loadingTip.loading()
                presenterImpl.getListData(type, curPage, cid)
            }
        })
        //下拉监听
        refreshLayout.setOnRefreshListener {
            curPage = 1
            articleList.clear()
            adapter.updata(articleList)
            presenterImpl.getListData(type, curPage, cid)

        }
        refreshLayout.setOnLoadMoreListener {
            curPage++
            presenterImpl.getListData(type, curPage, cid)

        }
        presenterImpl.getListData(type, curPage, cid)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_tab_list
    }

    override fun setListData(list: List<ArticleBean>) {
        loadingTip.dismiss()
        articleList.addAll(list)
        adapter.updata(articleList)
        if (curPage == 1) {
            refreshLayout.finishRefresh()
        } else {
            refreshLayout.finishLoadMore()
        }
    }

    override fun setError(ex: String) {
        loadingTip.showInternetError()
    }
}