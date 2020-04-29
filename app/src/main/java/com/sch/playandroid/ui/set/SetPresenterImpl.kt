package com.sch.playandroid.ui.set

import android.content.Context
import com.coder.zzq.smartshow.toast.SmartToast
import com.sch.playandroid.util.CacheDataManager
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

/**
 * Created by Sch.
 * Date: 2020/4/27
 * description:
 */
class SetPresenterImpl(var view: SetContract.ISetView?) : SetContract.ISetPresenter {
    override fun logout() {
        val params = RequestParams("https://www.wanandroid.com/user/logout/json")
        x.http().get(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {

            }

            override fun onSuccess(result: String?) {
                val obj = JSONObject(result)
                val errorCode = obj.getInt("errorCode")
                if (errorCode == 0) {
                    view?.logoutSuccess()
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

    override fun clearCache(context: Context) {
        CacheDataManager.clearAllCache(context)
        view?.clearCacheSuccess()
    }

    override fun checkUpdate() {
        doAsync {
            Thread.sleep(1500)
            uiThread {
                view?.checkUpdateSuccess()
            }
        }
    }
}