package com.sch.wanandroid.ui.my.articles

import com.sch.wanandroid.base.BasePresenter
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
class MyArticlePresenterImpl : BasePresenter<MyArticlesContract.IView>(),
    MyArticlesContract.IPresenter {
    override fun getArticleData(pageNum: Int) {
        val requestParams =
            RequestParams("https://wanandroid.com/user/lg/private_articles/$pageNum/json")
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
                    if (obj.getInt("errorCode") == 0) {
                        val data = obj.getJSONObject("data")
                        val shareArticles = data.getJSONObject("shareArticles")
                        val datas = GsonUtil.parseJsonArrayWithGson(
                            shareArticles.getString("datas"),
                            ArticleBean::class.java
                        )
                        uiThread {
                            getView()?.setArticleData(datas)
                        }
                    } else {
                        uiThread {
                            getView()?.onError(obj.getString("errorMsg"))
                        }
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

    override fun deleteArticle(id: Int) {
        val requestParams = RequestParams("https://wanandroid.com/lg/user_article/delete/$id/json")
        x.http().post(requestParams, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                val obj = JSONObject(result)
                if (obj.getInt("errorCode") == 0) {
                    getView()?.deleteArticleSuccess()
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

    override fun addArticle(title: String, link: String) {
        val params = RequestParams("https://www.wanandroid.com/lg/user_article/add/json")
        params.addParameter("title", title)
        params.addParameter("link", link)
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                val obj = JSONObject(result)
                if (obj.getInt("errorCode") == 0) {
                    getView()?.addArticleSuccess()
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