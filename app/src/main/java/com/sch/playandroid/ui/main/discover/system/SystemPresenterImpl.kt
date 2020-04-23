package com.sch.playandroid.ui.main.discover.system

import com.sch.playandroid.entity.SystemBean
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
class SystemPresenterImpl(val view: SystemContract.ISystemView) : SystemContract.ISystemPresenter {
    override fun getSystemData() {
        val params = RequestParams("https://www.wanandroid.com/tree/json")
        x.http().get(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                doAsync {
                    val objects = JSONObject(result)
                    val data = GsonUtil.parseJsonArrayWithGson(
                        objects.getString("data"),
                        SystemBean::class.java
                    )
                    uiThread {
                        view?.setSystemData(data)
                    }
                }
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                view?.setError(ex.toString())
            }
        })
    }
}