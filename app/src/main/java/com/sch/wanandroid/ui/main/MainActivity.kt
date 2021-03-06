package com.sch.wanandroid.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.coder.zzq.smartshow.toast.SmartToast
import com.sch.wanandroid.R
import com.sch.wanandroid.base.BaseActivity
import com.sch.wanandroid.base.IBasePresenter
import com.sch.wanandroid.constants.Constants
import com.sch.wanandroid.event.NightModel
import com.sch.wanandroid.ui.main.discover.DiscoverFragment
import com.sch.wanandroid.ui.main.home.HomeFragment
import com.sch.wanandroid.ui.main.mine.MineFragment
import com.sch.wanandroid.ui.main.tab.TabFragment
import com.sch.wanandroid.util.PrefUtils
import com.zs.wanandroid.event.LoginEvent
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : BaseActivity<IBasePresenter>() {
    private var lastIndex = 0 //上一次切换的fragment下标
    private val fragmentList by lazy { mutableListOf<Fragment>() }//切换的fragment集合
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        val temp = savedInstanceState?.getInt("lastIndex")
        temp?.let {
            lastIndex = it
            setFragmentPosition(lastIndex)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("lastIndex", lastIndex)
        super.onSaveInstanceState(outState)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun init(savedInstanceState: Bundle?) {
        initFragment()
        initBottom()
    }

    override fun onError(ex: String) {

    }

    /**
     * 初始化下方切换控件 BottomNavigationView
     */
    private fun initBottom() {
        btmNavigation.run {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_home -> setFragmentPosition(0)
                    R.id.menu_project -> setFragmentPosition(1)
                    R.id.menu_discover -> setFragmentPosition(2)
                    R.id.menu_official_account -> setFragmentPosition(3)
                    R.id.menu_mine -> setFragmentPosition(4)
                }
                true
            }
        }
    }

    /**
     * 初始化fragment，默认显示第0个
     */
    private fun initFragment() {
        //添加home页
        fragmentList.add(HomeFragment())
        //项目页和微信公众号页面使用同一种逻辑和页面，传入的type区分是项目页还是微信公众号页
        //添加项目页
        val projectFragment = TabFragment()
        val proBundle = Bundle()
        proBundle.putInt("type", Constants.PROJECT_TYPE)
        projectFragment.arguments = proBundle
        fragmentList.add(projectFragment)
        //添加发现页
        fragmentList.add(DiscoverFragment())
        //添加微信公众号页
        val wxarticleFragment = TabFragment()
        val wxBundle = Bundle()
        wxBundle.putInt("type", Constants.WX_TYPE)
        wxarticleFragment.arguments = wxBundle
        fragmentList.add(wxarticleFragment)
        //添加我的页面
        fragmentList.add(MineFragment())
        //默认展示第0个页面
        setFragmentPosition(lastIndex)
    }

    private fun setFragmentPosition(position: Int) {
        val ft = supportFragmentManager.beginTransaction()
        //当前要展示的fragment
        val currentFragment: Fragment = fragmentList[position]
        //切换前的fragment
        val lastFragment: Fragment = fragmentList[lastIndex]

        lastIndex = position
        //隐藏上一个页面
        ft.hide(lastFragment)
        //如果要展示的页面没有添加
        if (!currentFragment.isAdded) {
            //避免之前添加过被回收，先删除
            supportFragmentManager.beginTransaction().remove(currentFragment).commit()
            ft.add(R.id.container, currentFragment)
        }
        //展示当前fragment
        ft.show(currentFragment)
        ft.commit()
    }

    override fun createPresenter(): IBasePresenter? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    /**
     * 夜间模式
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun nightModelEvent(nightModel: NightModel) {
        recreate()
    }
    var lastTime: Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() - this.lastTime > 2000L) {
            SmartToast.show("再按一次退出程序")
            this.lastTime = System.currentTimeMillis()
            return
        } else {
            super.onBackPressed()
        }
    }
}
