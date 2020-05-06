package com.sch.wanandroid.test

import com.sch.wanandroid.base.IBasePresenter
import com.sch.wanandroid.base.IBaseView

/**
 * Created by Sch.
 * Date: 2020/5/6
 * description:
 */
class TestContract {
    interface ITestView : IBaseView {
        fun onSuccess()
    }

    interface ITestPresenter : IBasePresenter{
        fun doSomeThings()
    }
}