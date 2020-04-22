package com.sch.playandroid.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sch.playandroid.R
import com.sch.playandroid.base.BaseActivity
import com.sch.playandroid.constants.Constants
import com.sch.playandroid.ui.discover.DiscoverFragment
import com.sch.playandroid.ui.main.home.HomeFragment
import com.sch.playandroid.ui.main.mine.MeFragment
import com.sch.playandroid.ui.main.tab.TabFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private var lastIndex = 0
    private var fragments: MutableList<Fragment> = mutableListOf()

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun init(savedInstanceState: Bundle?) {
        initFragment()
        initBottom()
    }

    private fun initBottom() {
        btmNavigation.run {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_home -> setFragmentPosition(0)
                    R.id.menu_project -> setFragmentPosition(1)
                    R.id.menu_square -> setFragmentPosition(2)
                    R.id.menu_official_account -> setFragmentPosition(3)
                    R.id.menu_mine -> setFragmentPosition(4)
                }
                true
            }
        }
    }

    private fun initFragment() {
        fragments.add(HomeFragment())

        val projectFragment = TabFragment()
        val proBundle = Bundle()
        proBundle.putInt("type", Constants.PROJECT_TYPE)
        projectFragment.arguments = proBundle
        fragments.add(projectFragment)

        fragments.add(DiscoverFragment())

        val wxarticleFragment = TabFragment()
        val wxBundle = Bundle()
        wxBundle.putInt("type", Constants.WX_TYPE)
        wxarticleFragment.arguments = wxBundle
        fragments.add(wxarticleFragment)

        fragments.add(MeFragment())
        setFragmentPosition(0)
    }

    private fun setFragmentPosition(position: Int) {
        val ft = supportFragmentManager.beginTransaction()
        val currentFragment: Fragment = fragments[position]
        val lastFragment: Fragment = fragments[lastIndex]
        lastIndex = position
        ft.hide(lastFragment)
        if (!currentFragment.isAdded) {
            ft.add(R.id.container, currentFragment)
        }
        ft.show(currentFragment)
        ft.commit()
    }

}
