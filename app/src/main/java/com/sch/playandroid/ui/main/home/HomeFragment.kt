package com.sch.playandroid.ui.main.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.OverScroller
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.Glide
import com.sch.lolcosmos.base.BaseFragment
import com.sch.playandroid.R
import com.sch.playandroid.adapter.ArticleAdapter
import com.sch.playandroid.base.LazyFragment
import com.sch.playandroid.constants.Constants
import com.sch.playandroid.entity.ArticleBean
import com.sch.playandroid.entity.BannerBean
import com.sch.playandroid.ui.search.SearchActivity
import com.sch.playandroid.ui.web.WebActivity
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : LazyFragment(), HomeContract.IHomeView {
    val presenter by lazy {
        HomePresenterImpl(
            this
        )
    }
    val adapter by lazy { ArticleAdapter() }
    val articleList by lazy { ArrayList<ArticleBean>() }
    val bannerList by lazy { ArrayList<BannerBean>() }

    private var articleIndex = 0


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
        articleIndex = 0
        presenter.getArticleData(articleIndex)
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
        //下拉监听
        refreshLayout.setOnRefreshListener {
            loadData()
        }
        refreshLayout.setOnLoadMoreListener {
            articleIndex++
            presenter.getArticleData(articleIndex)
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
        val views: MutableList<View> = ArrayList()
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


    override fun showBanner(list: List<BannerBean>) {
        bannerList.addAll(list)
        initBanners()
    }

    override fun setTopArticleDatas(list: List<ArticleBean>) {
        articleList.addAll(0, list)
        adapter.updata(articleList)
    }

    override fun onLoadArticleDatas(list: List<ArticleBean>) {
        loadingTip.dismiss()
        articleList.addAll(list)
        adapter.updata(articleList)
        if (articleIndex == 0) {
            refreshLayout.finishRefresh()
        } else {
            refreshLayout.finishLoadMore()
        }
    }

    override fun onError(msg: String) {
        loadingTip.showInternetError()
    }

}