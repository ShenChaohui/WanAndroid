package com.sch.playandroid.ui.discover.squrare

import android.util.Log
import com.sch.playandroid.constants.Constants
import com.sch.playandroid.entity.ArticleBean
import com.sch.playandroid.util.GsonUtil
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

/**
 * Created by Sch.
 * Date: 2020/4/21
 * description:
 */
class SquarePresenterImpl(var view: SquareContract.ISquareView) :
    SquareContract.ISquarePresenter {
    override fun getListData(curPage: Int) {
        val requestParams = RequestParams("https://wanandroid.com/user_article/list/$curPage/json")
        x.http().get(requestParams, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                val obj = JSONObject(result)
                val data = obj.getJSONObject("data")

                val datas = GsonUtil.parseJsonArrayWithGson(
                    data.getString("datas"),
                    ArticleBean::class.java
                )
                view?.setListData(datas)
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                Log.e("test", ex.toString())
                view?.setError(ex.toString())
            }
        })
    }
}