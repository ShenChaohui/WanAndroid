package com.sch.playandroid.ui.main.mine

import com.sch.playandroid.constants.Constants
import com.sch.playandroid.entity.UserCoinInfo
import com.sch.playandroid.util.GsonUtil
import com.sch.playandroid.util.PrefUtils
import kotlinx.android.synthetic.main.fragment_mine.*
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

/**
 * Created by Sch.
 * Date: 2020/4/26
 * description:
 */
class MinePresenterImpl(var view: MineContract.IMineView) : MineContract.IMinePresenter {
    override fun getUserCoinInfo() {
        val params = RequestParams("https://www.wanandroid.com/lg/coin/userinfo/json")
        x.http().get(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                val obj = JSONObject(result)
                val errorCode = obj.getInt("errorCode")
                if (errorCode == 0) {
                    val userCoinInfoStr = obj.getString("data")
                    val userCoinInfo =
                        GsonUtil.parseJsonWithGson(userCoinInfoStr, UserCoinInfo::class.java)
                    PrefUtils.setString(Constants.USERCOININFO, userCoinInfoStr)
                    view?.setUserCoinInfo(userCoinInfo)
                }
            }

            override fun onCancelled(cex: Callback.CancelledException?) {

            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
            }

        })
    }

}