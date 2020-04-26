package com.sch.playandroid.ui.set

import android.os.Bundle
import com.sch.playandroid.R
import com.sch.playandroid.base.BaseActivity
import com.sch.playandroid.constants.Constants
import com.sch.playandroid.util.PrefUtils
import com.zs.wanandroid.event.LogoutEvent
import kotlinx.android.synthetic.main.activity_set.*
import org.greenrobot.eventbus.EventBus
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

/**
 * Created by Sch.
 * Date: 2020/4/26
 * description:
 */
class SetActivity : BaseActivity() {
    override fun init(savedInstanceState: Bundle?) {
        btnLogout.setOnClickListener {
            val params = RequestParams("https://www.wanandroid.com/user/logout/json")
            x.http().get(params, object : Callback.CommonCallback<String> {
                override fun onFinished() {

                }

                override fun onSuccess(result: String?) {
                    PrefUtils.setBoolean(Constants.LOGIN, false)
                    PrefUtils.removeKey(Constants.USERINFO)
                    PrefUtils.removeKey(Constants.USERCOININFO)
                    EventBus.getDefault().post(LogoutEvent())
                    finish()
                }

                override fun onCancelled(cex: Callback.CancelledException?) {

                }

                override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                }

            })
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_set
    }

}