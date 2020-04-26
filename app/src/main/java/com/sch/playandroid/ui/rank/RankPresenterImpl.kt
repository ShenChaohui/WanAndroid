package com.sch.playandroid.ui.rank

import com.sch.playandroid.entity.RankBean
import com.sch.playandroid.util.GsonUtil
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

/**
 * Created by Sch.
 * Date: 2020/4/26
 * description:
 */
class RankPresenterImpl(var view: RankConstant.IRankView) : RankConstant.IRankPresenter {
    override fun getRankList(pageNum: Int) {
        val params = RequestParams("https://www.wanandroid.com/coin/rank/$pageNum/json")
        x.http().get(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                val obj = JSONObject(result)
                val data = obj.getJSONObject("data")
                val datas =
                    GsonUtil.parseJsonArrayWithGson(data.getString("datas"), RankBean::class.java)
                view?.showRankList(datas)
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                view?.onError(ex.toString())
            }

        })
    }
}