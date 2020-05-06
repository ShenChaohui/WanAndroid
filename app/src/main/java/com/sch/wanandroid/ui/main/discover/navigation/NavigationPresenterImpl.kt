package com.sch.wanandroid.ui.main.discover.navigation

import com.sch.wanandroid.base.BasePresenter
import com.sch.wanandroid.entity.NavigationBean
import com.sch.wanandroid.util.GsonUtil
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

/**
 * Created by Sch.
 * Date: 2020/4/23
 * description:
 */
class NavigationPresenterImpl:BasePresenter<NavigationContract.IView>(),
    NavigationContract.IPresenter {
    override fun getNavigationData() {
        val params = RequestParams("https://www.wanandroid.com/navi/json")
        x.http().get(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                val obj = JSONObject(result)
                if (obj.getInt("errorCode") != 0) {
                    getView()?.onError(obj.getString("errorMsg"))
                    return
                }
                doAsync {
                    val data = GsonUtil.parseJsonArrayWithGson(
                        obj.getString("data"),
                        NavigationBean::class.java
                    )
                    uiThread {
                        getView()?.setNavigationData(data)
                    }
                }
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                getView()?.onError(ex.toString())
            }
        })
    }
}