package com.sch.wanandroid.ui.search

import android.animation.ValueAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
import android.view.inputmethod.EditorInfo
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coder.zzq.smartshow.dialog.DialogBtnClickListener
import com.coder.zzq.smartshow.dialog.EnsureDialog
import com.coder.zzq.smartshow.dialog.SmartDialog
import com.coder.zzq.smartshow.toast.SmartToast
import com.sch.wanandroid.R
import com.sch.wanandroid.adapter.ArticleAdapter
import com.sch.wanandroid.base.BaseActivity
import com.sch.wanandroid.constants.Constants
import com.sch.wanandroid.entity.ArticleBean
import com.sch.wanandroid.ui.web.WebActivity
import com.sch.wanandroid.util.AppManager
import com.sch.wanandroid.util.KeyBoardUtil
import com.sch.wanandroid.util.PrefUtils
import com.sch.wanandroid.util.UiUtils
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Created by Sch.
 * Date: 2020/4/23
 * description:
 */
class SearchActivity : BaseActivity<SearchContract.IPresenter>(), SearchContract.IView {
    private var keyWord: String? = null
    private var pageNum: Int = 0
    private val articleList by lazy { mutableListOf<ArticleBean>() }
    private val articleAdapter by lazy { ArticleAdapter() }

    private val recordList by lazy { mutableListOf<String>() }
    private var collectPosition = 0
    /**
     * 点击收藏后将点击事件上锁,等接口有相应结果再解锁
     * 避免重复点击产生的bug  false表示没锁，true表示锁住
     */
    private var lockCollectClick = false
    private val clearRecordDialog by lazy {
        EnsureDialog()
            .message("确定要清空搜索记录？")
            .confirmBtn("确定") { smartDialog: SmartDialog<*>?, i: Int, any: Any? ->
                smartDialog?.dismiss()
                clearRecord()
            }
            .cancelBtn("取消", object : DialogBtnClickListener<SmartDialog<*>> {
                override fun onBtnClick(p0: SmartDialog<*>?, p1: Int, p2: Any?) {
                    p0?.dismiss()
                }
            })
    }

    /**
     * 如果是第一次加载label，将label隐藏后续动画开启后再显示
     */
    private var isFirstLoad: Boolean = true

    private var rlInitWidth: Int = 0

    override fun init(savedInstanceState: Bundle?) {
        val recordStr = PrefUtils.getString(Constants.SEARCH_RECORD)
        if (!TextUtils.isEmpty(recordStr)) {
            recordList.addAll(recordStr!!.split("|"))
        }
        startSearchAnim()
        loadRecord()
        initListener()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = articleAdapter
        recyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER//取消滑动到顶部边界越界效果

    }

    private fun initListener() {
        // 设置无网络时重新加载点击事件
        loadingTip.setReloadListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                loadingTip.loading()
                search()
            }
        })
        ivBack.setOnClickListener {
            finish()
        }
        tvClear.setOnClickListener {
            clearRecordDialog.showInActivity(this)
        }
        ivClear.setOnClickListener {
            editText.setText("")
        }
        addSearchListener()
        smartRefresh.setOnLoadMoreListener {
            pageNum++
            keyWord?.let { mPresenter?.getSearchData(it, pageNum) }
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
        labelsView.setOnLabelClickListener { label, data, position ->
            if (data is String) {
                editText.setText(data)
                keyWord = data
                search()
            }
        }
    }

    private fun loadRecord() {
//        labelsView.setLabels(recordList,object :LabelsView.LabelTextProvider<String>{})
        //最后一个参数是函数，可以提到外部
        labelsView.setLabels(recordList) { label, position, data ->
            if (isFirstLoad) {
                label.visibility = View.GONE
            }
            data
        }
        isFirstLoad = false
    }

    /**
     * 清除搜索记录
     */
    private fun clearRecord() {
        recordList.clear()
        loadRecord()
    }

    private fun addSearchListener() {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //搜索框为空的时候显示搜索历史
                if (editText.text!!.toString().isEmpty()) {
                    loadRecord()
                    ivClear.visibility = View.GONE
                    loadingTip.dismiss()
                    smartRefresh.visibility = View.GONE//搜索结果关闭
                    rlRecord.visibility = View.VISIBLE//搜索历史显示
                } else {
                    ivClear.visibility = View.VISIBLE
                    editText.setSelection(editText.text.length)
                }
            }
        })
        editText.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                keyWord = editText.text.toString().trim()
                if (!TextUtils.isEmpty(keyWord)) {
                    //将已存在的key移除，避免存在重复数据
                    for (index in 0 until recordList.size) {
                        if (recordList[index] == keyWord) {
                            recordList.removeAt(index)
                            break
                        }
                    }
                    recordList.add(keyWord!!)
                    search()
                    return@setOnEditorActionListener false
                } else {
                    return@setOnEditorActionListener true
                }
            }
            return@setOnEditorActionListener false
        }
    }

    private fun search() {
        articleList.clear()//清空上一次的结果
        articleAdapter.updata(articleList)
        KeyBoardUtil.closeKeyboard(editText, this)//关闭键盘
        loadingTip.loading()//加载中动画
        rlRecord.visibility = View.GONE//搜索历史关闭
        smartRefresh.visibility = View.VISIBLE//搜索结果显示
        pageNum = 0
        keyWord?.let { mPresenter?.getSearchData(it, pageNum) }

    }

    private fun startSearchAnim() {
        rlTop.post {//保证加载完view再执行动画
            rlInitWidth = rlTop.measuredWidth
            val left = rlTop.left - UiUtils.dip2px(this, 10f)
            val animation = ValueAnimator.ofInt(rlInitWidth, left)
            animation.duration = 300
            animation.interpolator = DecelerateInterpolator()
            animation.addUpdateListener {
                //获取改变后的值
                val currentValue = animation.animatedValue as Int
                //输出改变后的值
                val params = rlTop.layoutParams as RelativeLayout.LayoutParams
                params.width = currentValue
                rlTop.layoutParams = params
                //展开完毕后开启标签动画，并弹出软键盘
                if (currentValue == left) {
                    startLabelAnim()
                    editText.isFocusable = true
                    editText.isFocusableInTouchMode = true
                    editText.requestFocus()
                    KeyBoardUtil.openKeyboard(editText, this)
                }
            }
            animation.start()
        }
    }

    private fun startLabelAnim() {
        for (index in 0 until labelsView.childCount) run {
            val view: View = labelsView.getChildAt(index)
            view.visibility = View.VISIBLE
            val aa = ScaleAnimation(0f, 1f, 0.5f, 1f)
            aa.interpolator = DecelerateInterpolator()
            aa.duration = 500
            view.startAnimation(aa)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun onPause() {
        super.onPause()
        KeyBoardUtil.closeKeyboard(editText, this)
        overridePendingTransition(R.anim.anim_no, R.anim.anim_search_out)
    }

    override fun onDestroy() {
        super.onDestroy()
        PrefUtils.setString(Constants.SEARCH_RECORD, recordList.joinToString("|"))
    }


    override fun finish() {
        super.finish()
        val rlWidth = rlTop.measuredWidth
        val animator = ValueAnimator.ofInt(rlWidth, rlInitWidth)
        animator.duration = 500
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener {
            //获取改变后的值
            val currentValue = animator.animatedValue as Int
            //输出改变后的值
            val params = rlTop.layoutParams as RelativeLayout.LayoutParams
            params.width = currentValue
            rlTop.layoutParams = params
        }
        animator.start()
    }

    override fun setSearchResultData(datas: MutableList<ArticleBean>) {
        dismissRefresh()
        if (datas.isNotEmpty()) {
            articleList.addAll(datas)
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

    override fun createPresenter(): SearchContract.IPresenter? {
        return SearchPresenterImpl()
    }
}