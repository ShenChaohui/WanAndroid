package com.sch.playandroid.test

import android.os.Bundle
import android.util.Log
import com.sch.playandroid.R
import com.sch.playandroid.base.BaseActivity

/**
 * Created by Sch.
 * Date: 2020/5/6
 * description:
 */
class TestActivity : BaseActivity<TestContract.ITestPresenter>(), TestContract.ITestView {

    override fun getLayoutId(): Int {
        return R.layout.activity_test
    }

    override fun init(savedInstanceState: Bundle?) {
        mPresenter?.doSomeThings()
    }

    override fun onSuccess() {
        Log.e("test", "success")
    }

    override fun onError(ex: String) {
    }

    override fun createPresenter(): TestContract.ITestPresenter? {
        return TestPresenterImpl()
    }

}