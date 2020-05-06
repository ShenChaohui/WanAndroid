package com.sch.wanandroid.ui.main.mine

import com.sch.wanandroid.base.BasePresenter
import com.sch.wanandroid.constants.Constants
import com.sch.wanandroid.entity.UserCoinInfo
import com.sch.wanandroid.util.GsonUtil
import com.sch.wanandroid.util.PrefUtils
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

/**
 * Created by Sch.
 * Date: 2020/4/26
 * description:
 */
class MinePresenterImpl : BasePresenter<MineContract.IView>(), MineContract.IPresenter {
    override fun getUserCoinInfo() {
        val params = RequestParams("https://www.wanandroid.com/lg/coin/userinfo/json")
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
                    val userCoinInfoStr = obj.getString("data")
                    val userCoinInfo =
                        GsonUtil.parseJsonWithGson(userCoinInfoStr, UserCoinInfo::class.java)
                    PrefUtils.setString(Constants.USERCOININFO, userCoinInfoStr)
                    uiThread {
                        getView()?.setUserCoinInfo(userCoinInfo)
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

}