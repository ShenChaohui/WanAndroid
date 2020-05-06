package com.sch.playandroid.ui.main.tab

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.sch.lolcosmos.base.BaseFragment
import com.sch.playandroid.R
import com.sch.playandroid.adapter.TabFragmentAdapter
import com.sch.playandroid.entity.TabTypeBean
import com.sch.playandroid.ui.main.tab.list.TabListFragment
import kotlinx.android.synthetic.main.fragment_tab.*

/**
 * 项目页和公众号 共用
 */
class TabFragment : BaseFragment<TabContract.IPresenter>(), TabContract.IView {
    private val fragmentList by lazy { mutableListOf<Fragment>() }
    private val tabList by lazy { mutableListOf<TabTypeBean>() }
    private val fragmentAdapter by lazy {
        TabFragmentAdapter(
            fragmentList,
            tabList,
            childFragmentManager
        )
    }
    var type: Int? = null
    override fun init(savedInstanceState: Bundle?) {
        //获取type，用于判断当前是项目页还是公众号页面
        type = arguments?.getInt("type")!!
        viewPager.adapter = fragmentAdapter
        viewPager.offscreenPageLimit = 5
        //tabLayout 和viewPager绑定
        tabLayout.setupWithViewPager(viewPager)
        initListener()
        type?.let {
            //获取tab分类数据
            loadingTip.loading()
            mPresenter?.getTabListData(it)
        }
    }

    private fun initListener() {
        // 设置无网络时重新加载点击事件
        loadingTip.setReloadListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                loadingTip.loading()
                type?.let {
                    mPresenter?.getTabListData(it)
                }
            }
        })
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_tab
    }

    override fun setTabListData(list: MutableList<TabTypeBean>) {
        loadingTip.dismiss()
        tabList.clear()
        tabList.addAll(list)
        initListFragment()
    }

    private fun initListFragment() {
        fragmentList.clear()
        tabList.forEach {
            val tabListFragment = TabListFragment()
            val bundle = Bundle()
            bundle.putInt("id", it.id)
            type?.let { it1 -> bundle.putInt("type", it1) }
            bundle.putString("name", it.name)
            tabListFragment.arguments = bundle
            fragmentList.add(tabListFragment)
        }
        fragmentAdapter.updata(fragmentList)
    }

    override fun onError(ex: String) {
        loadingTip.showInternetError()
    }

    override fun createPresenter(): TabContract.IPresenter? {
        return TabPresenterImpl()
    }
}