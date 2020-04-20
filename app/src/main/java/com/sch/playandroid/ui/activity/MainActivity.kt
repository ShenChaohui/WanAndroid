package com.sch.playandroid.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sch.playandroid.R
import com.sch.playandroid.base.BaseActivity
import com.sch.playandroid.ui.fragment.MeFragment
import com.sch.playandroid.ui.fragment.ProjectFragment
import com.sch.playandroid.ui.fragment.SquareFragment
import com.sch.playandroid.ui.fragment.WXFragment
import com.sch.playandroid.ui.fragment.HomeFragment
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
        fragments.add(ProjectFragment())
        fragments.add(SquareFragment())
        fragments.add(WXFragment())
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
