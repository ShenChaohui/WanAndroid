package com.sch.playandroid.ui.my.collect

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coder.zzq.smartshow.toast.SmartToast
import com.sch.playandroid.R
import com.sch.playandroid.adapter.MyCollectAdapter
import com.sch.playandroid.base.BaseActivity
import com.sch.playandroid.constants.Constants
import com.sch.playandroid.entity.CollectBean
import com.sch.playandroid.ui.web.WebActivity
import com.sch.playandroid.util.AppManager
import kotlinx.android.synthetic.main.activity_collect.*

/**
 * Created by Sch.
 * Date: 2020/4/28
 * description: 我的收藏页面
 */
class MyCollectActivity : BaseActivity(),
    MyCollectContract.IMyCollectView {
    private val presenterImpl by lazy {
        MyCollectPresenterImpl(
            this
        )
    }
    private var pageNum: Int = 0
    private val myCollectAdapter by lazy { MyCollectAdapter() }
    private val collectList by lazy { mutableListOf<CollectBean>() }
    private var addCollectDialog: Dialog? = null

    /**
     * 点击收藏后将点击事件上锁,等接口有相应结果再解锁
     * 避免重复点击产生的bug  false表示没锁，true表示锁住
     */
    private var lockCollectClick = false
    private var collectPosition = 0

    override fun init(savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myCollectAdapter
        recyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER//取消滑动到顶部边界越界效果
        initListener()
        initAddCollectDialog()
        loadingTip.loading()
        loadData()
    }
    private fun initAddCollectDialog() {
        val builder = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_add_collect, null)
        val ivAddCollect = view.findViewById<ImageView>(R.id.ivAddCollect)
        val etTitle = view.findViewById<EditText>(R.id.etTitle)
        val etAuthor = view.findViewById<EditText>(R.id.etAuthor)
        val etLink = view.findViewById<EditText>(R.id.etLink)
        ivAddCollect.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val author = etAuthor.text.toString().trim()
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
                    etAuthor.setText("")
                    presenterImpl.addCollect(title, author, link)
                    addCollectDialog?.dismiss()
                }
            }
        }
        builder.setView(view)
        addCollectDialog = builder.create()
    }

    private fun loadData() {
        collectList.clear()
        myCollectAdapter.updata(collectList)
        pageNum = 1
        presenterImpl.getCollectList(pageNum)
    }

    private fun initListener() {
        ivBack.setOnClickListener {
            finish()
        }
        ivAdd.setOnClickListener {
            addCollectDialog?.show()
        }
        // 设置无网络时重新加载点击事件
        loadingTip.setReloadListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                loadingTip.loading()
                presenterImpl.getCollectList(pageNum)
            }
        })
        smartRefresh.setOnRefreshListener {
            pageNum = 0
            collectList.clear()
            myCollectAdapter.updata(collectList)
            presenterImpl.getCollectList(pageNum)
        }
        smartRefresh.setOnLoadMoreListener {
            pageNum++
            presenterImpl.getCollectList(pageNum)
        }
        myCollectAdapter.setOnCollectClickListener(object : MyCollectAdapter.OnCollectClickListener {
            override fun onCollectClick(position: Int) {
                if (!AppManager.isLogin()) {
                    SmartToast.info("请先登录")
                    return
                }
                if (position < collectList.size && !lockCollectClick) {
                    lockCollectClick = true
                    collectPosition = position
                    presenterImpl.unCollect(
                        collectList[position].id,
                        collectList[position].originId
                    )
                }
            }

        })
        myCollectAdapter.setOnItemClickListener(object : MyCollectAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                intent(Bundle().apply {
                    putString(Constants.WEB_URL, collectList[position].link)
                    putString(Constants.WEB_TITLE, collectList[position].title)
                }, WebActivity::class.java, false)
            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_collect
    }

    override fun showCollectList(list: MutableList<CollectBean>) {
        dismissRefresh()
        if (list.isNotEmpty()) {
            collectList.addAll(list)
            myCollectAdapter.updata(collectList)
        } else {
            if (collectList.size == 0) loadingTip.showEmpty()
            else SmartToast.info("没有更多数据了")
        }
    }

    override fun onError(ex: String) {
        lockCollectClick = false
        dismissRefresh()
        //请求失败将page -1
        if (pageNum > 1) pageNum--
        if (collectList.size == 0) {
            loadingTip.showInternetError()
        }
        SmartToast.error(ex)
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

    override fun unCollectSuccess() {
        lockCollectClick = false
        collectList.removeAt(collectPosition)
        myCollectAdapter.removeAt(collectPosition)
    }

    override fun addCollectSuccess() {
        loadingTip.loading()
        pageNum = 0
        presenterImpl.getCollectList(pageNum)

    }
}