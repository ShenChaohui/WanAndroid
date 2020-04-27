package com.sch.playandroid.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.Glide
import com.coder.zzq.smartshow.toast.SmartToast
import com.sch.playandroid.R
import com.sch.playandroid.adapter.ArticleAdapter
import com.sch.playandroid.base.LazyFragment
import com.sch.playandroid.constants.Constants
import com.sch.playandroid.entity.ArticleBean
import com.sch.playandroid.entity.BannerBean
import com.sch.playandroid.ui.search.SearchActivity
import com.sch.playandroid.ui.web.WebActivity
import com.sch.playandroid.util.AppManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.loadingTip


class HomeFragment : LazyFragment(), HomeContract.IHomeView {
    val presenter by lazy {
        HomePresenterImpl(
            this
        )
    }
    val adapter by lazy { ArticleAdapter() }
    val articleList by lazy { mutableListOf<ArticleBean>() }
    val bannerList by lazy { mutableListOf<BannerBean>() }

    private var pageNum = 0

    private var collectPosition = 0

    /**
     * 点击收藏后将点击事件上锁,等接口有相应结果再解锁
     * 避免重复点击产生的bug  false表示没锁，true表示锁住
     */
    private var lockCollectClick = false

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun lazyInit() {
        initListerer()
        loadData()
    }

    /**
     * 加载数据
     * 初始化，网络出错重新加载，刷新均可使用
     */
    private fun loadData() {
        //banner只加载一次
        if (bannerList.size == 0) {
            presenter.getBannerData()
        }
        articleList.clear()
        adapter?.updata(articleList)
        presenter.getTopArticleData()
        pageNum = 0
        presenter.getArticleData(pageNum)
    }


    fun initListerer() {
        //加载中动画
        loadingTip.loading()
        // 设置无网络时重新加载点击事件
        loadingTip.setReloadListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                loadingTip.loading()
                loadData()
            }
        })
        rvHomeList.layoutManager = LinearLayoutManager(context)
        rvHomeList.adapter = adapter
        rvHomeList.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        adapter.setOnItemClickListener(object : ArticleAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                intent(Bundle().apply {
                    putString(Constants.WEB_URL, articleList[position].link)
                    putString(Constants.WEB_TITLE, articleList[position].title)
                }, WebActivity::class.java, false)
            }
        })
        adapter.setOnCollectClickListener(object : ArticleAdapter.OnCollectClickListener {
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
                            presenter?.collect(id)
                        } else {
                            presenter?.unCollect(id)
                        }

                    }

                }
            }
        })
        //下拉监听
        smartRefresh.setOnRefreshListener {
            loadData()
        }
        smartRefresh.setOnLoadMoreListener {
            pageNum++
            presenter.getArticleData(pageNum)
        }
        //NestedScrollView 滑动监听
        nestedView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
            val alpha = if (scrollY > 0) {
                ivSearch.isEnabled = true
                scrollY.toFloat() / (300).toFloat()
            } else {
                ivSearch.isEnabled = false
                0f
            }
            rlSearch.alpha = alpha
        })
        ivSearch.setOnClickListener {
            intent(SearchActivity::class.java, false)
            //瞬间开启activity，无动画
            activity?.overridePendingTransition(R.anim.anim_no, R.anim.anim_no)
        }

    }

    private fun initBanners() {
        banner.setAutoPlayAble(true)
        val views = mutableListOf<View>()
        bannerList.forEach { _ ->
            views.add(
                LayoutInflater.from(context).inflate(R.layout.banner_layout, null)
                    .findViewById(R.id.ivBanner)
            )
        }
        banner.setAdapter(object : BGABanner.Adapter<ImageView?, String> {
            override fun fillBannerItem(
                banner: BGABanner?,
                itemView: ImageView?,
                model: String?,
                position: Int
            ) {
                itemView?.let {
                    it.scaleType = ImageView.ScaleType.CENTER_CROP
                    val bannerEntity = bannerList[position]
                    Glide.with(itemView.context)
                        .load(bannerEntity.imagePath)
                        .into(it)
                }
            }
        })
        banner.setData(views)
        //点击事件
        banner.setDelegate(object : BGABanner.Delegate<ImageView, String> {
            override fun onBannerItemClick(
                banner: BGABanner?,
                itemView: ImageView?,
                model: String?,
                position: Int
            ) {
                intent(Bundle().apply {
                    putString(Constants.WEB_URL, bannerList[position].url)
                    putString(Constants.WEB_TITLE, bannerList[position].title)
                }, WebActivity::class.java, false)
            }

        })
    }


    override fun showBanner(list: MutableList<BannerBean>) {
        bannerList.addAll(list)
        initBanners()
    }

    override fun setTopArticleDatas(list: MutableList<ArticleBean>) {
        articleList.addAll(0, list)
        adapter.updata(articleList)
    }

    override fun onLoadArticleDatas(list: MutableList<ArticleBean>) {
        dismissRefresh()
        if (list.isNotEmpty()) {
            articleList.addAll(list)
            adapter.updata(articleList)
        } else {
            if (articleList.size == 0) loadingTip.showEmpty()
            else SmartToast.info("没有更多数据了")
        }
    }

    override fun onError(msg: String) {
        lockCollectClick = false
        //请求失败将page -1
        if (pageNum > 0) pageNum--
        dismissRefresh()
        if (articleList.size == 0) {
            loadingTip.showInternetError()
        }
        SmartToast.error(msg)
    }

    override fun collectSuccess() {
        lockCollectClick = false
        if (collectPosition < articleList.size) {
            articleList[collectPosition].collect = true
            adapter.updata(articleList)
        }
    }

    override fun unCollectSuccess() {
        lockCollectClick = false
        if (collectPosition < articleList.size) {
            articleList[collectPosition].collect = false
            adapter.updata(articleList)
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
}