package com.sch.playandroid.ui.main.discover.navigation

import com.sch.playandroid.entity.NavigationBean
import com.sch.playandroid.util.GsonUtil
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
class NavigationPresenterImpl(val view: NavigationContract.INavigationView?) :
    NavigationContract.INavigationPresenter {
    override fun getNavigationData() {
        val params = RequestParams("https://www.wanandroid.com/navi/json")
        x.http().get(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                val obj = JSONObject(result)
                if (obj.getInt("errorCode") != 0) {
                    view?.onError(obj.getString("errorMsg"))
                    return
                }
                doAsync {
                    val data = GsonUtil.parseJsonArrayWithGson(
                        obj.getString("data"),
                        NavigationBean::class.java
                    )
                    uiThread {
                        view?.setNavigationData(data)
                    }
                }
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                view?.onError(ex.toString())
            }
        })
    }
}