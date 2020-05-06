package com.sch.wanandroid.ui.login

import com.sch.wanandroid.base.BasePresenter
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

/**
 * Created by Sch.
 * Date: 2020/4/24
 * description:
 */
class LoginPresenterImpl: BasePresenter<LoginConstant.IView>(),LoginConstant.IPresenter {
    override fun doLogin(username: String, password: String) {
        val params = RequestParams("https://www.wanandroid.com/user/login")
        params.addParameter("username", username)
        params.addParameter("password", password)
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                val obj = JSONObject(result)
                val errorCode = obj.getInt("errorCode")
                if (errorCode == 0) {
                    getView()?.loginSuccess()
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