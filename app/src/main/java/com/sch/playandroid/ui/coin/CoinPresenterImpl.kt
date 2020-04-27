package com.sch.playandroid.ui.coin

import com.sch.playandroid.entity.CoinRecordBean
import com.sch.playandroid.util.GsonUtil
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

/**
 * Created by Sch.
 * Date: 2020/4/27
 * description:
 */
class CoinPresenterImpl(var view: CoinConstant.ICoinView) : CoinConstant.ICoinPresenter {
    override fun getCoinList(pageNum: Int) {
        val params = RequestParams("https://www.wanandroid.com//lg/coin/list/$pageNum/json")
        x.http().get(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                val obj = JSONObject(result)
                val data = obj.getJSONObject("data");
                val list = GsonUtil.parseJsonArrayWithGson(
                    data.getString("datas"),
                    CoinRecordBean::class.java
                )
                view?.showCoinList(list)
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                view?.onError(ex.toString())
            }

        })
    }
}