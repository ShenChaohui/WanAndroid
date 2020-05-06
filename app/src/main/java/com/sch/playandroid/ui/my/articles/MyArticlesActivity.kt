package com.sch.playandroid.ui.my.articles

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coder.zzq.smartshow.dialog.DialogBtnClickListener
import com.coder.zzq.smartshow.dialog.EnsureDialog
import com.coder.zzq.smartshow.dialog.SmartDialog
import com.coder.zzq.smartshow.toast.SmartToast
import com.sch.playandroid.R
import com.sch.playandroid.adapter.MyArticleAdapter
import com.sch.playandroid.base.BaseActivity
import com.sch.playandroid.constants.Constants
import com.sch.playandroid.entity.ArticleBean
import com.sch.playandroid.ui.web.WebActivity
import kotlinx.android.synthetic.main.activity_myarticles.*

/**
 * Created by Sch.
 * Date: 2020/4/28
 * description:
 */
class MyArticlesActivity : BaseActivity<MyArticlesContract.IPresenter>(),
    MyArticlesContract.IView {
    private val myArticleAdapter by lazy { MyArticleAdapter() }
    private var pageNum = 1
    private val articleList by lazy { mutableListOf<ArticleBean>() }
    private var addArticleDialog: Dialog? = null
    private var delatePosition: Int = 0

    private val delateDialog by lazy {
        EnsureDialog().message("是否删除该文章")
            .cancelBtn("取消")
            .confirmBtn("确定", object : DialogBtnClickListener<SmartDialog<*>> {
                override fun onBtnClick(p0: SmartDialog<*>?, p1: Int, p2: Any?) {
                    p0?.dismiss()
                    mPresenter?.deleteArticle(articleList[delatePosition].id)
                }
            })
    }

    override fun init(savedInstanceState: Bundle?) {
        initListener()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myArticleAdapter
        recyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER//取消滑动到顶部边界越界效果
        initAddArticleDialog()
        //加载中动画
        loadingTip.loading()
        loadData()
    }

    private fun loadData() {
        articleList.clear()
        myArticleAdapter.updata(articleList)
        pageNum = 1
        mPresenter?.getArticleData(pageNum)
    }

    private fun initAddArticleDialog() {
        val builder = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_add_article, null)
        val ivShare = view.findViewById<ImageView>(R.id.ivShare)
        val etTitle = view.findViewById<EditText>(R.id.etTitle)
        val etLink = view.findViewById<EditText>(R.id.etLink)
        ivShare.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val link = etLink.text.toString().trim()
            when {
                title.isEmpty() -> {
                    etTitle.setError("标题不能为空")
                }
                link.isEmpty() -> {
                    etLink.setError("链接地址不能为空")
                }
                else -> {
                    etTitle.setText("")
                    etLink.setText("")
                    mPresenter?.addArticle(title, link)
                    addArticleDialog?.dismiss()
                }
            }
        }
        builder.setView(view)
        addArticleDialog = builder.create()
    }

    private fun initListener() {
        ivBack.setOnClickListener {
            finish()
        }
        ivAdd.setOnClickListener {
            addArticleDialog?.show()
        }
        myArticleAdapter.setOnItemClickListener(object : MyArticleAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                intent(Bundle().apply {
                    putString(Constants.WEB_URL, articleList[position].link)
                    putString(Constants.WEB_TITLE, articleList[position].title)
                }, WebActivity::class.java, false)
            }
        })
        myArticleAdapter.setOnItemLongClickListener(object : MyArticleAdapter.OnItemLongClickListener {
            override fun onLongClick(position: Int) {
                delatePosition = position
                delateDialog.showInActivity(this@MyArticlesActivity)
            }
        })
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
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_myarticles
    }

    override fun setArticleData(list: MutableList<ArticleBean>) {
        dismissRefresh()
        if (list.isNotEmpty()) {
            articleList.addAll(list)
            myArticleAdapter.updata(articleList)
        } else {
            if (articleList.size == 0) loadingTip.showEmpty()
            else SmartToast.info("没有更多数据了")
        }
    }

    override fun onError(ex: String) {
        //请求失败将page -1
        if (pageNum > 0) pageNum--
        dismissRefresh()
        if (articleList.size == 0) {
            loadingTip.showInternetError()
        }
        SmartToast.error(ex)
    }

    override fun deleteArticleSuccess() {
        articleList.removeAt(delatePosition)
        myArticleAdapter.deleteAt(delatePosition)
        if (articleList.size == 0) loadingTip.showEmpty()
    }

    override fun addArticleSuccess() {
        loadingTip.loading()
        pageNum = 0
        mPresenter?.getArticleData(pageNum)
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

    override fun createPresenter(): MyArticlesContract.IPresenter? {
        return MyArticlePresenterImpl()
    }
}