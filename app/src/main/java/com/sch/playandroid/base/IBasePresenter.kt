package com.sch.playandroid.base
/**
 * @author yyx
 */

interface IBasePresenter{
    fun attachView(view: IBaseView)

    fun detachView()
}
