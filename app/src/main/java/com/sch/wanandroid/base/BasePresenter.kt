package com.sch.wanandroid.base

import java.lang.ref.WeakReference

/**
 * Created by Sch.
 * Date: 2020/5/6
 * description:
 */
abstract class BasePresenter<V : IBaseView> : IBasePresenter {
    protected var mView: WeakReference<V>? = null
    override fun attachView(view: IBaseView) {
        mView = WeakReference(view as V)
    }

    override fun detachView() {
        mView?.clear()
    }

    fun getView(): V? {
        return mView?.get()
    }

}