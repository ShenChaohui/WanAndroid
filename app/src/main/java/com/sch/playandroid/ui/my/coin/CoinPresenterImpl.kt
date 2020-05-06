package com.sch.playandroid.ui.my.coin

import com.sch.playandroid.base.BasePresenter
import com.sch.playandroid.entity.CoinRecordBean
import com.sch.playandroid.util.GsonUtil
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

/**
 * Created by Sch.
 * Date: 2020/4/27
 * description:
 */
class CoinPresenterImpl :BasePresenter<CoinConstant.IView>(), CoinConstant.IPresenter {
    override fun getCoinData(pageNum: Int) {
        val params = RequestParams("https://www.wanandroid.com//lg/coin/list/$pageNum/json")
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
                    val data = obj.getJSONObject("data");
                    val list = GsonUtil.parseJsonArrayWithGson(
                        data.getString("datas"),
                        CoinRecordBean::class.java
                    )
                    uiThread {
                        getView()?.setCoinData(list)
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