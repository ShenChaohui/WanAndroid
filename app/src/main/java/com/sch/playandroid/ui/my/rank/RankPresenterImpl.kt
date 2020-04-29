package com.sch.playandroid.ui.my.rank

import com.sch.playandroid.entity.RankBean
import com.sch.playandroid.util.GsonUtil
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

/**
 * Created by Sch.
 * Date: 2020/4/26
 * description:
 */
class RankPresenterImpl(var view: RankConstant.IRankView?) :
    RankConstant.IRankPresenter {
    override fun getRankData(pageNum: Int) {
        val params = RequestParams("https://www.wanandroid.com/coin/rank/$pageNum/json")
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
                    val data = obj.getJSONObject("data")
                    val datas =
                        GsonUtil.parseJsonArrayWithGson(
                            data.getString("datas"),
                            RankBean::class.java
                        )
                    uiThread {
                        view?.setRankData(datas)
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