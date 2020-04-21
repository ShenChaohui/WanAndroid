package com.sch.playandroid.ui.main.tab.list

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
class TabListPresenterImpl(var view: TabListContract.ITabListView) :
    TabListContract.ITabListPresenter {
    override fun getListData(type: Int, curPage: Int, cid: Int) {
        Log.e("test", "getListData" + type)
        var url: String
        if (type == Constants.PROJECT_TYPE) {
            url = "https://www.wanandroid.com/project/list/$curPage/json?cid=$cid"
        } else {
            url = "https://wanandroid.com/wxarticle/list/$cid/$curPage/json"
        }

        val requestParams = RequestParams(url)
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
                view.setListData(datas)
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                Log.e("test", ex.toString())
            }
        })
    }

}