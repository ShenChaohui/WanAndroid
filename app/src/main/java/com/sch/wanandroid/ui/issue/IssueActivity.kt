package com.sch.wanandroid.ui.issue

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coder.zzq.smartshow.toast.SmartToast
import com.sch.wanandroid.R
import com.sch.wanandroid.adapter.ArticleAdapter
import com.sch.wanandroid.base.BaseActivity
import com.sch.wanandroid.constants.Constants
import com.sch.wanandroid.entity.ArticleBean
import com.sch.wanandroid.ui.web.WebActivity
import com.sch.wanandroid.util.AppManager
import kotlinx.android.synthetic.main.activity_issue.*

/**
 * Created by Sch.
 * Date: 2020/4/27
 * description: 问答页面
 */
class IssueActivity : BaseActivity<IssueContract.IPresenter>(), IssueContract.IView {
    private val articleAdapter by lazy { ArticleAdapter() }
    private var pageNum = 1
    val articleList by lazy { mutableListOf<ArticleBean>() }
    private var collectPosition = 0

    /**
     * 点击收藏后将点击事件上锁,等接口有相应结果再解锁
     * 避免重复点击产生的bug  false表示没锁，true表示锁住
     */
    private var lockCollectClick = false
    override fun init(savedInstanceState: Bundle?) {
        ivBack.setOnClickListener {
            finish()
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = articleAdapter
        recyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER//取消滑动到顶部边界越界效果
        initListener()

//加载中动画
        loadingTip.loading()
        loadData()
    }

    private fun loadData() {
        articleList.clear()
        articleAdapter.updata(articleList)
        pageNum = 1
        mPresenter?.getArticleData(pageNum)
    }

    private fun initListener() {
        // 设置无网络时重新加载点击事件
        loadingTip.setReloadListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                loadingTip.loading()
                loadData()
            }
        })
        //下拉监听
        smartRefresh.setOnRefreshListener {
            loadData()
        }
        smartRefresh.setOnLoadMoreListener {
            pageNum++
            mPresenter?.getArticleData(pageNum)
        }
        articleAdapter.setOnItemClickListener(object : ArticleAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                intent(Bundle().apply {
                    putString(Constants.WEB_URL, articleList[position].link)
                    putString(Constants.WEB_TITLE, articleList[position].title)
                }, WebActivity::class.java, false)
            }
        })
        articleAdapter.setOnCollectClickListener(object : ArticleAdapter.OnCollectClickListener {
            override fun onCollectClick(position: Int) {
                if (!AppManager.isLogin()) {
                    SmartToast.info("请先登录")
                    return
                }
                if (position < articleList.size && !lockCollectClick) {
                    lockCollectClick = true
                    collectPosition = position
                    articleList[position].apply {
                        if (!collect) {
                            mPresenter?.collect(id)
                        } else {
                            mPresenter?.unCollect(id)
                        }

                    }

                }
            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_issue
    }

    override fun setArticleData(list: MutableList<ArticleBean>) {
        dismissRefresh()
        if (list.isNotEmpty()) {
            articleList.addAll(list)
            articleAdapter.updata(articleList)
        } else {
            if (articleList.size == 0) loadingTip.showEmpty()
            else SmartToast.info("没有更多数据了")
        }
    }

    override fun onError(ex: String) {
        lockCollectClick = false
        //请求失败将page -1
        if (pageNum > 0) pageNum--
        dismissRefresh()
        if (articleList.size == 0) {
            loadingTip.showInternetError()
        }
        SmartToast.error(ex)
    }

    override fun collectSuccess() {
        lockCollectClick = false
        if (collectPosition < articleList.size) {
            articleList[collectPosition].collect = true
            articleAdapter.updata(articleList)
        }
    }

    override fun unCollectSuccess() {
        lockCollectClick = false
        if (collectPosition < articleList.size) {
            articleList[collectPosition].collect = false
            articleAdapter.updata(articleList)
        }
    }

    /**
     * 隐藏刷新加载
     */
    private fun dismissRefresh() {
        loadingTip.dismiss()
        if (smartRefresh.state.isOpening) {
            smartRefresh.finishLoadMore()
            smartRefresh.finishRefresh()
        }
    }

    override fun createPresenter(): IssueContract.IPresenter? {
        return IssuePresenterImpl()
    }
}