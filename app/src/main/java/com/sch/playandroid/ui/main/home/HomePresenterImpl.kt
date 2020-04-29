package com.sch.playandroid.ui.main.home

import com.sch.playandroid.entity.ArticleBean
import com.sch.playandroid.entity.BannerBean
import com.sch.playandroid.util.GsonUtil
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

class HomePresenterImpl(var view: HomeContract.IHomeView?) :
    HomeContract.IHomePresenter {

    override fun getBannerData() {
        val params = RequestParams("https://www.wanandroid.com/banner/json")
        x.http().get(params, object : Callback.CommonCallback<String> {
            override fun onSuccess(result: String?) {
                val obj = JSONObject(result)
                if (obj.getInt("errorCode") != 0) {
                    view?.onError(obj.getString("errorMsg"))
                    return
                }
                doAsync {
                    val bannerList =
                        GsonUtil.parseJsonArrayWithGson(
                            obj.getString("data"),
                            BannerBean::class.java
                        )
                    uiThread {
                        view?.setBannerData(bannerList)
                    }
                }
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                view?.onError(ex.toString())
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
                    view?.onError(obj.getString("errorMsg"))
                    return
                }
                doAsync {
                    val list =
                        GsonUtil.parseJsonArrayWithGson(
                            obj.getString("data"),
                            ArticleBean::class.java
                        )
                    uiThread {
                        view?.setTopArticleData(list)
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

    override fun getArticleData(pageNum: Int) {
        val params = RequestParams("https://www.wanandroid.com/article/list/$pageNum/json")
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
                    val list =
                        GsonUtil.parseJsonArrayWithGson(
                            obj.getJSONObject("data").getString("datas"),
                            ArticleBean::class.java
                        )
                    uiThread {
                        view?.setArticleData(list)
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

    override fun collect(id: Int) {
        val params = RequestParams("https://www.wanandroid.com/lg/collect/$id/json")
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                val obj = JSONObject(result)
                if (obj.getInt("errorCode") == 0) {
                    view?.collectSuccess()
                } else {
                    view?.onError(obj.getString("errorMsg"))
                }
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                view?.onError(ex.toString())

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
                    view?.unCollectSuccess()
                } else {
                    view?.onError(obj.getString("errorMsg"))
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