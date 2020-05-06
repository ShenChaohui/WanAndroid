package com.sch.wanandroid.test

import com.sch.wanandroid.base.BasePresenter

/**
 * Created by Sch.
 * Date: 2020/5/6
 * description:
 */
class TestPresenterImpl :BasePresenter<TestContract.ITestView>(),TestContract.ITestPresenter {
    override fun doSomeThings() {
        getView()?.onSuccess()
    }
}