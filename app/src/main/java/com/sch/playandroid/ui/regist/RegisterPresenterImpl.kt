package com.sch.playandroid.ui.regist

import android.util.Log
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

/**
 * Created by Sch.
 * Date: 2020/4/24
 * description:
 */
class RegisterPresenterImpl(var view: RegisterConstant.IRegisterView) :
    RegisterConstant.IRegisterPresenter {
    override fun doRegister(username: String, password: String, rePassword: String) {
        val params = RequestParams("https://www.wanandroid.com/user/register")
        params.addParameter("username", username)
        params.addParameter("password", password)
        params.addParameter("repassword", rePassword)
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {

            }

            override fun onSuccess(result: String?) {
                Log.e("test", result)
                val obj = JSONObject(result)
                val errorCode = obj.getInt("errorCode")
                if (errorCode == 0) {
                    view?.registerSuccess()
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