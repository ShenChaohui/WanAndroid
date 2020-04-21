package com.sch.playandroid.ui.main.tab

import com.sch.playandroid.entity.ProjectTypeBean
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
class TabPresenterImpl(var view: TabContract.ITabView) : TabContract.ITabPresenter {
    override fun getTabList() {
        val params = RequestParams("https://www.wanandroid.com/project/tree/json")
        x.http().get(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                val obj = JSONObject(result)
                val data =
                    GsonUtil.parseJsonArrayWithGson(obj.getString("data"), ProjectTypeBean::class.java)
                view?.setTabList(data)
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
            }

        });
    }

}