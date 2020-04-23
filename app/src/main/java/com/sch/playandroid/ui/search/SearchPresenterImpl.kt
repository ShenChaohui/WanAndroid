package com.sch.playandroid.ui.search

import android.util.Log
import com.sch.playandroid.entity.ArticleBean
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
class SearchPresenterImpl(var view: SearchContract.ISearchView) : SearchContract.ISearchPresenter {
    override fun getSearchData(keyWord: String, pageNum: Int) {
        val params = RequestParams("https://www.wanandroid.com/article/query/$pageNum/json")
        params.addParameter("k", keyWord)
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {}
            override fun onSuccess(result: String?) {
                doAsync {
                    val obj = JSONObject(result)
                    val data = obj.getJSONObject("data")

                    val datas = GsonUtil.parseJsonArrayWithGson(
                        data.getString("datas"),
                        ArticleBean::class.java
                    )
                    uiThread {
                        view?.setSearchResultData(datas)
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