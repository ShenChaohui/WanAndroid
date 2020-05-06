package com.sch.playandroid.ui.main.tab

import com.sch.playandroid.base.BasePresenter
import com.sch.playandroid.constants.Constants
import com.sch.playandroid.entity.TabTypeBean
import com.sch.playandroid.util.GsonUtil
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

/**
 * Created by Sch.
 * Date: 2020/4/21
 * description:
 */
class TabPresenterImpl : BasePresenter<TabContract.IView>(), TabContract.IPresenter {
    override fun getTabListData(type: Int) {
        val url: String =
            if (type == Constants.PROJECT_TYPE) {
                "https://www.wanandroid.com/project/tree/json"
            } else {
                "https://wanandroid.com/wxarticle/chapters/json"
            }
        val params = RequestParams(url)
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
                    val data =
                        GsonUtil.parseJsonArrayWithGson(
                            obj.getString("data"),
                            TabTypeBean::class.java
                        )
                    uiThread {
                        getView()?.setTabListData(data)
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