package com.sch.wanandroid.ui.main.discover.system

import com.sch.wanandroid.base.BasePresenter
import com.sch.wanandroid.entity.SystemBean
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
class SystemPresenterImpl : BasePresenter<SystemContract.IView>(), SystemContract.IPresenter {
    override fun getSystemData() {
        val params = RequestParams("https://www.wanandroid.com/tree/json")
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
                        SystemBean::class.java
                    )
                    uiThread {
                        getView()?.setSystemData(data)
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