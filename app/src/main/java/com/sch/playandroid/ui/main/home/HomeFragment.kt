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
import com.sch.lolcosmos.base.BaseFragment
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


class HomeFragment : BaseFragment<HomeContract.IPresenter>(), HomeContract.IView {

    //文章适配器
    private val articleAdapter by lazy { ArticleAdapter() }

    //文章列表数据
    private val articleList by lazy { mutableListOf<ArticleBean>() }

    //轮播图列表数据
    private val bannerList by lazy { mutableListOf<BannerBean>() }

    //获取文章数据时的页码，从0开始
    private var pageNum = 0

    //收藏或取消收藏是点击的位置
    private var collectPosition = 0

    /**
     * 点击收藏后将点击事件上锁,等接口有相应结果再解锁
     * 避免重复点击产生的bug  false表示没锁，true表示锁住
     */
    private var lockCollectClick = false

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun init(savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = articleAdapter
        //取消滑动越界效果
        recyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        initListerer()
        //加载中动画
        loadingTip.loading()
        loadData()
    }

    /**
     * 加载数据
     * 初始化，网络出错重新加载，刷新均可使用
     */
    private fun loadData() {
        //banner只加载一次
        if (bannerList.size == 0) {
            mPresenter?.getBannerData()
        }
        articleList.clear()
        articleAdapter.updata(articleList)
        mPresenter?.getTopArticleData()
        pageNum = 0
        mPresenter?.getArticleData(pageNum)
    }


    fun initListerer() {
        // 设置无网络时重新加载点击事件
        loadingTip.setReloadListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                loadingTip.loading()
                loadData()
            }
        })

        //recycleView item 点击事件
        articleAdapter.setOnItemClickListener(object : ArticleAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                //打开web页面，全局用一个，传入url 和 title
                intent(Bundle().apply {
                    putString(Constants.WEB_URL, articleList[position].link)
                    putString(Constants.WEB_TITLE, articleList[position].title)
                }, WebActivity::class.java, false)
            }
        })
        // recycleView上的收藏按钮 点击事件
        articleAdapter.setOnCollectClickListener(object : ArticleAdapter.OnCollectClickListener {
            override fun onCollectClick(position: Int) {
                if (!AppManager.isLogin()) {
                    SmartToast.info("请先登录")
                    return
                }
                //避免数组越界   并且 当前 收藏按钮没加锁
                if (position < articleList.size && !lockCollectClick) {
                    //加锁，收藏成功或失败前不可再点击收藏
                    lockCollectClick = true
                    //记录当前点击的位置
                    collectPosition = position
                    //判断当前文章是否被收藏，如果收藏过，则调用取消收藏
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
        //下拉监听
        smartRefresh.setOnRefreshListener {
            loadData()
        }
        smartRefresh.setOnLoadMoreListener {
            pageNum++
            mPresenter?.getArticleData(pageNum)
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
            //改变搜索栏透明度
            rlSearch.alpha = alpha
        })
        ivSearch.setOnClickListener {
            intent(SearchActivity::class.java, false)
            //瞬间开启activity，无动画
            activity?.overridePendingTransition(R.anim.anim_no, R.anim.anim_no)
        }

    }

    /**
     * 初始化banner控件
     */
    private fun initBanners() {
        //设置自动播放
        banner.setAutoPlayAble(true)
        val views = mutableListOf<View>()
        //添加banner的view
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

    /**
     * 获取banner数据成功
     */
    override fun setBannerData(list: MutableList<BannerBean>) {
        bannerList.addAll(list)
        initBanners()
    }

    /**
     * 获取置顶数据成功
     */
    override fun setTopArticleData(list: MutableList<ArticleBean>) {
        articleList.addAll(0, list)
        articleAdapter.updata(articleList)
    }

    /**
     * 获取文章数据成功
     */
    override fun setArticleData(list: MutableList<ArticleBean>) {
        dismissRefresh()
        //如果list不是空，刷新列表
        if (list.isNotEmpty()) {
            articleList.addAll(list)
            articleAdapter.updata(articleList)
        } else {
            //如果当前页面没有数据，展示无数据
            if (articleList.size == 0) loadingTip.showEmpty()
            //如果当前页面有数据，则没有更多数据
            else SmartToast.info("没有更多数据了")
        }
    }

    override fun onError(msg: String) {
        //释放收藏锁
        lockCollectClick = false
        //请求失败将page -1
        if (pageNum > 0) pageNum--
        dismissRefresh()
        //如果当前没有数据，展示网络异常
        if (articleList.size == 0) {
            loadingTip.showInternetError()
        }
        SmartToast.error(msg)
    }

    /**
     * 收藏成功
     */
    override fun collectSuccess() {
        lockCollectClick = false
        if (collectPosition < articleList.size) {
            articleList[collectPosition].collect = true
            articleAdapter.updata(articleList)
        }
    }

    /**
     * 收藏失败
     */
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

    override fun createPresenter(): HomeContract.IPresenter? {
        return HomePresenterImpl()
    }
}