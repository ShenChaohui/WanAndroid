package com.sch.playandroid.base

import android.os.Bundle
import com.sch.lolcosmos.base.BaseFragment

/**
 * Created by Sch.
 * Date: 2020/4/22
 * description:
 */
abstract class LazyFragment : BaseFragment() {
    private var isLoaded = false
    override fun onResume() {
        super.onResume()
        //增加了Fragment是否可见的判断
        if (!isLoaded && !isHidden) {
            lazyInit()
            isLoaded = true
        }
    }

    override fun init(savedInstanceState: Bundle?) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded = false
    }

    abstract fun lazyInit()
}