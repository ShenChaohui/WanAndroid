package com.sch.playandroid.ui.regist

import android.util.Log
import com.sch.playandroid.base.BasePresenter
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

/**
 * Created by Sch.
 * Date: 2020/4/24
 * description:
 */
class RegisterPresenterImpl:BasePresenter<RegisterConstant.IView>(),
    RegisterConstant.IPresenter {
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
                    getView()?.registerSuccess()
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