package com.sch.playandroid.test

import com.sch.playandroid.base.IBasePresenter
import com.sch.playandroid.base.IBaseView

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