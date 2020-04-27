package com.sch.playandroid.ui.set

import android.os.Bundle
import com.coder.zzq.smartshow.dialog.DialogBtnClickListener
import com.coder.zzq.smartshow.dialog.EnsureDialog
import com.coder.zzq.smartshow.dialog.SmartDialog
import com.coder.zzq.smartshow.toast.SmartToast
import com.sch.playandroid.R
import com.sch.playandroid.base.BaseActivity
import com.sch.playandroid.constants.Constants
import com.sch.playandroid.util.CacheDataManager
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
class SetActivity : BaseActivity(), SetContract.ISetView {
    private val presenterImpl by lazy { SetPresenterImpl(this) }
    private val logoutDialog by lazy {
        EnsureDialog()
            .message("确定要退出登录吗")
            .cancelBtn("取消")
            .confirmBtn("确定", object : DialogBtnClickListener<SmartDialog<*>> {
                override fun onBtnClick(p0: SmartDialog<*>?, p1: Int, p2: Any?) {
                    presenterImpl?.logout()
                }

            })

    }

    override fun init(savedInstanceState: Bundle?) {
        initListener()
        tvCache.text = CacheDataManager.getTotalCacheSize(this)
    }

    private fun initListener() {
        ivBack.setOnClickListener {
            finish()
        }
        tvLogout.setOnClickListener {
            logoutDialog.showInActivity(this)
        }
        tvClearCache.setOnClickListener {
            CacheDataManager.clearAllCache(this)
            SmartToast.success("已清除")
            tvCache.text = ""
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_set
    }

    override fun logoutSuccess() {
        PrefUtils.setBoolean(Constants.LOGIN, false)
        PrefUtils.removeKey(Constants.USERINFO)
        PrefUtils.removeKey(Constants.USERCOININFO)
        EventBus.getDefault().post(LogoutEvent())
        finish()
    }

    override fun onError(ex: String) {
        SmartToast.error(ex)
    }

}