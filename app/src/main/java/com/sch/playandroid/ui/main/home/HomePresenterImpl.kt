package com.sch.playandroid.ui.main.home

import com.sch.playandroid.base.BasePresenter
import com.sch.playandroid.entity.ArticleBean
import com.sch.playandroid.entity.BannerBean
import com.sch.playandroid.util.GsonUtil
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

class HomePresenterImpl:BasePresenter<HomeContract.IView>(),
    HomeContract.IPresenter {

    override fun getBannerData() {
        val params = RequestParams("https://www.wanandroid.com/banner/json")
        x.http().get(params, object : Callback.CommonCallback<String> {
            override fun onSuccess(result: String?) {
                val obj = JSONObject(result)
                if (obj.getInt("errorCode") != 0) {
                    getView()?.onError(obj.getString("errorMsg"))
                    return
                }
                doAsync {
                    val bannerList =
                        GsonUtil.parseJsonArrayWithGson(
                            obj.getString("data"),
                            BannerBean::class.java
                        )
                    uiThread {
                        getView()?.setBannerData(bannerList)
                    }
                }
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                getView()?.onError(ex.toString())
            }

            override fun onFinished() {

            }


            override fun onCancelled(cex: Callback.CancelledException?) {
            }


        })
    }

    override fun getTopArticleData() {
        val params = RequestParams("https://www.wanandroid.com/article/top/json")
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
                    val list =
                        GsonUtil.parseJsonArrayWithGson(
                            obj.getString("data"),
                            ArticleBean::class.java
                        )
                    uiThread {
                        getView()?.setTopArticleData(list)
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

    override fun getArticleData(pageNum: Int) {
        val params = RequestParams("https://www.wanandroid.com/article/list/$pageNum/json")
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
                    val list =
                        GsonUtil.parseJsonArrayWithGson(
                            obj.getJSONObject("data").getString("datas"),
                            ArticleBean::class.java
                        )
                    uiThread {
                        getView()?.setArticleData(list)
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

    override fun collect(id: Int) {
        val params = RequestParams("https://www.wanandroid.com/lg/collect/$id/json")
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                val obj = JSONObject(result)
                if (obj.getInt("errorCode") == 0) {
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
                if (obj.getInt("errorCode") == 0) {
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