package com.sch.playandroid.ui.main.home

import com.sch.playandroid.entity.ArticleBean
import com.sch.playandroid.entity.BannerBean
import com.sch.playandroid.util.GsonUtil
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

class HomePresenterImpl(var view: HomeContract.IHomeView) :
    HomeContract.IHomePresenter {

    override fun getBannerData() {
        val params = RequestParams("https://www.wanandroid.com/banner/json")
        x.http().get(params, object : Callback.CommonCallback<String> {
            override fun onSuccess(result: String?) {
                val obj = JSONObject(result)
                val bannerList =
                    GsonUtil.parseJsonArrayWithGson(obj.getString("data"), BannerBean::class.java)
                view?.showBanner(bannerList)

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

    override fun getArticleData(index: Int) {
        val params = RequestParams("https://www.wanandroid.com/article/list/$index/json")
        x.http().get(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                val obj = JSONObject(result)
                val list =
                    GsonUtil.parseJsonArrayWithGson(
                        obj.getJSONObject("data").getString("datas"),
                        ArticleBean::class.java
                    )
                view?.onLoadArticleDatas(list)

            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                view?.onError(ex.toString())
            }

        })
    }
}