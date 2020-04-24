package com.sch.playandroid.ui.main.mine

import android.os.Bundle
import com.sch.lolcosmos.base.BaseFragment
import com.sch.playandroid.R
import com.sch.playandroid.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_me.*
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x
import java.net.HttpURLConnection
import java.net.URL

class MeFragment : BaseFragment() {
    override fun init(savedInstanceState: Bundle?) {
        btnLogin.setOnClickListener {
            intent(LoginActivity::class.java, false)
        }
        btnLogout.setOnClickListener {
            val params = RequestParams("https://www.wanandroid.com/user/logout/json")
            x.http().get(params, object : Callback.CommonCallback<String> {
                override fun onFinished() {

                }

                override fun onSuccess(result: String?) {
                    tvResult.setText(result)
                }

                override fun onCancelled(cex: Callback.CancelledException?) {

                }

                override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                    tvResult.setText(ex.toString())
                }

            })
        }
        getMyCoin.setOnClickListener {
            val params = RequestParams("https://www.wanandroid.com/lg/coin/userinfo/json")
            x.http().get(params, object : Callback.CommonCallback<String> {
                override fun onFinished() {

                }

                override fun onSuccess(result: String?) {
                    tvResult.setText(result)
                }

                override fun onCancelled(cex: Callback.CancelledException?) {

                }

                override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                    tvResult.setText(ex.toString())
                }

            })
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_me
    }
}