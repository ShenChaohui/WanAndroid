package com.sch.wanandroid.base
/**
 * @author yyx
 */

interface IBasePresenter{
    fun attachView(view: IBaseView)

    fun detachView()
}
