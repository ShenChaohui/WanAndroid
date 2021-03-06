package com.sch.wanandroid.ui.my.collect

import com.sch.wanandroid.base.BasePresenter
import com.sch.wanandroid.entity.CollectBean
import com.sch.wanandroid.util.GsonUtil
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

/**
 * Created by Sch.
 * Date: 2020/4/28
 * description:
 */
class MyCollectPresenterImpl : BasePresenter<MyCollectContract.IView>(),
    MyCollectContract.IPresenter {
    override fun getCollectList(pageNum: Int) {
        val params = RequestParams("https://www.wanandroid.com/lg/collect/list/$pageNum/json")
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
                    val data = obj.getJSONObject("data")
                    val datas = GsonUtil.parseJsonArrayWithGson(
                        data.getString("datas"),
                        CollectBean::class.java
                    )
                    uiThread {
                        getView()?.showCollectList(datas)
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

    override fun unCollect(id: Int, originId: Int) {
        val params = RequestParams("https://www.wanandroid.com/lg/uncollect/$id/json")
        params.addParameter("originId", originId)
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

    override fun addCollect(title: String, author: String?, link: String) {
        val params = RequestParams("https://www.wanandroid.com/lg/collect/add/json")
        params.addParameter("title", title)
        params.addParameter("author", author)
        params.addParameter("link", link)
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                val obj = JSONObject(result)
                if (obj.getInt("errorCode") == 0) {
                    getView()?.addCollectSuccess()
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