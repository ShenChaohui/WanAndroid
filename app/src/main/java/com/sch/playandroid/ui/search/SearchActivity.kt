package com.sch.playandroid.ui.search

import android.animation.ValueAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
import android.view.inputmethod.EditorInfo
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.sch.playandroid.R
import com.sch.playandroid.adapter.ArticleAdapter
import com.sch.playandroid.base.BaseActivity
import com.sch.playandroid.constants.Constants
import com.sch.playandroid.entity.ArticleBean
import com.sch.playandroid.ui.web.WebActivity
import com.sch.playandroid.util.KeyBoardUtil
import com.sch.playandroid.util.PrefUtils
import com.sch.playandroid.util.UiUtils
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Created by Sch.
 * Date: 2020/4/23
 * description:
 */
class SearchActivity : BaseActivity(), SearchContract.ISearchView {
    private val persenter by lazy { SearchPresenterImpl(this) }
    private var keyWord: String? = null
    private var pageNum: Int = 0
    private val articleList by lazy { ArrayList<ArticleBean>() }
    private val adapter by lazy { ArticleAdapter() }

    private val recordList by lazy { ArrayList<String>() }

    /**
     * 如果是第一次加载label，将label隐藏后续动画开启后再显示
     */
    private var isFirstLoad: Boolean = true

    private var rlInitWidth: Int = 0

    override fun init(savedInstanceState: Bundle?) {
        val recordStr = PrefUtils.getString(Constants.SEARCH_RECORD)
        if (recordStr != null) {
            recordList.addAll(recordStr.split("|"))
        }
        startSearchAnim()
        loadRecord()
        initListener()
        rvSearch.layoutManager = LinearLayoutManager(this)
        rvSearch.adapter = adapter
    }

    private fun initListener() {
        ivBack.setOnClickListener {
            finish()
        }
        tvClear.setOnClickListener {
            clearRecord()
        }
        ivClear.setOnClickListener {
            editText.setText("")
        }
        addSearchListener()
        smartRefresh.setOnLoadMoreListener {
            pageNum++
            keyWord?.let { persenter.getSearchData(it, pageNum) }
        }
        adapter.setOnItemClickListener(object : ArticleAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                intent(Bundle().apply {
                    putString(Constants.WEB_URL, articleList[position].link)
                    putString(Constants.WEB_TITLE, articleList[position].title)
                }, WebActivity::class.java, false)
            }

        })
    }

    private fun loadRecord() {
        labelsView.setLabels(recordList) { label, position, data ->
            if (isFirstLoad) {
                label.visibility = View.GONE
            }
            data
        }
        isFirstLoad = false
        labelsView.setOnLabelClickListener { label, data, position ->
            if (data is String) {
                editText.setText(data)
                keyWord = data
                search()
            }
        }
    }

    /**
     * 清除搜索记录
     */
    private fun clearRecord() {
        recordList?.clear()
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
                    smartRefresh.visibility = View.GONE
                    rlRecord.visibility = View.VISIBLE

                } else {
                    ivClear.visibility = View.VISIBLE
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
        KeyBoardUtil.closeKeyboard(editText, this)
        loadingTip.loading()
        rlRecord.visibility = View.GONE
        smartRefresh.visibility = View.VISIBLE
        pageNum = 0
        keyWord?.let { persenter.getSearchData(it, pageNum) }

    }

    private fun startSearchAnim() {
        rlTop.post {//保证加载完view再执行动画
            rlInitWidth = rlTop.measuredWidth
            Log.e("test", "rlTop.measuredWidth：$rlInitWidth")
            var left = rlTop.left - UiUtils.dip2px(this, 10f)
            Log.e("test", "rlTop.left：$left")

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
        overridePendingTransition(R.anim.anim_no, R.anim.anim_search_out)
    }

    override fun onDestroy() {
        super.onDestroy()
        PrefUtils.setString(Constants.SEARCH_RECORD, recordList.joinToString("|"))
    }


    override fun finish() {
        KeyBoardUtil.closeKeyboard(editText, this)
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

    override fun setSearchResultData(datas: List<ArticleBean>) {
        loadingTip.dismiss()
        smartRefresh.finishLoadMore()
        if (datas == null || datas.size == 0) {
            loadingTip.showEmpty()
        } else {
            articleList.addAll(datas)
            adapter.updata(articleList)
        }
    }

    override fun setError(ex: String) {
        loadingTip.showInternetError()
    }
}