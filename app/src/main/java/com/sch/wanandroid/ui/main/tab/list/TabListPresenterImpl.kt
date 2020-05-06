package com.sch.wanandroid.ui.main.tab.list

import android.util.Log
import com.sch.wanandroid.base.BasePresenter
import com.sch.wanandroid.constants.Constants
import com.sch.wanandroid.entity.ArticleBean
import com.sch.wanandroid.util.GsonUtil
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
class TabListPresenterImpl:BasePresenter<TabListContract.IView>(),
    TabListContract.IPresenter{
    override fun getArticleData(type: Int, pageNum: Int, cid: Int) {
        var url: String
        if (type == Constants.PROJECT_TYPE) {
            url = "https://www.wanandroid.com/project/list/$pageNum/json?cid=$cid"
        } else {
            url = "https://wanandroid.com/wxarticle/list/$cid/$pageNum/json"
        }

        val requestParams = RequestParams(url)
        x.http().get(requestParams, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                val obj = JSONObject(result)
                if (obj.getInt("errorCode") != 0) {
                    getView()?.onError(obj.getString("errorMsg"))
                    return
                }
                doAsync {
                    val data = obj.getJSONObject("data")
                    val datas = GsonUtil.parseJsonArrayWithGson(
                        data.getString("datas"),
                        ArticleBean::class.java
                    )
                    uiThread {
                        getView()?.setArticleData(datas)
                    }
                }
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                Log.e("test", ex.toString())
                getView()?.onError(ex.toString())
            }
        })
    }

    override fun collect(id: Int) {
        val params = RequestParams("https://www.wanandroid.com/lg/collect/$id/json")
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                val obj = JSONObject(result)
                val errorCode = obj.getInt("errorCode")
                if (errorCode == 0) {
                    getView()?.collectSuccess()
                } else {
                    getView()?.onError(obj.getString("errorMsg"))
                }
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                getView()?.onError(ex.toString())
            }

        })
    }

    override fun unCollect(id: Int) {
        val params = RequestParams("https://www.wanandroid.com/lg/uncollect_originId/$id/json")
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                val obj = JSONObject(result)
                val errorCode = obj.getInt("errorCode")
                if (errorCode == 0) {
                    getView()?.unCollectSuccess()
                } else {
                    getView()?.onError(obj.getString("errorMsg"))
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