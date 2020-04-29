package com.sch.playandroid.ui.set

import android.os.Bundle
import com.coder.zzq.smartshow.dialog.DialogBtnClickListener
import com.coder.zzq.smartshow.dialog.EnsureDialog
import com.coder.zzq.smartshow.dialog.LoadingDialog
import com.coder.zzq.smartshow.dialog.SmartDialog
import com.coder.zzq.smartshow.toast.SmartToast
import com.sch.playandroid.R
import com.sch.playandroid.base.BaseActivity
import com.sch.playandroid.constants.Constants
import com.sch.playandroid.util.AppManager
import com.sch.playandroid.util.CacheDataManager
import com.sch.playandroid.util.PrefUtils
import com.zs.wanandroid.event.LogoutEvent
import kotlinx.android.synthetic.main.activity_set.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by Sch.
 * Date: 2020/4/26
 * description:
 */
class SetActivity : BaseActivity(), SetContract.ISetView {
    private val presenterImpl by lazy { SetPresenterImpl(this) }
    private val updataDialog by lazy {
        LoadingDialog()
            .large()
            .message("检查更新")
    }

    private val logoutDialog by lazy {
        EnsureDialog()
            .message("确定要退出登录吗")
            .cancelBtn("取消")
            .confirmBtn("确定", object : DialogBtnClickListener<SmartDialog<*>> {
                override fun onBtnClick(p0: SmartDialog<*>?, p1: Int, p2: Any?) {
                    presenterImpl.logout()
                }
            })

    }

    override fun init(savedInstanceState: Bundle?) {
        initListener()
        tvCacheValue.text = CacheDataManager.getTotalCacheSize(this)
        tvVersionsValue.text = AppManager.getVersionName(this)
    }

    private fun initListener() {
        ivBack.setOnClickListener {
            finish()
        }
        tvLogout.setOnClickListener {
            logoutDialog.showInActivity(this)
        }
        tvClearCache.setOnClickListener {
            presenterImpl.clearCache(this)
        }
        tvVersions.setOnClickListener {
            updataDialog.showInActivity(this)
            presenterImpl.checkUpdate()
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_set
    }

    override fun logoutSuccess() {
        PrefUtils.setBoolean(Constants.LOGIN, false)
        PrefUtils.removeKey(Constants.USERCOININFO)
        EventBus.getDefault().post(LogoutEvent())
        finish()
    }

    override fun clearCacheSuccess() {
        SmartToast.success("已清除")
        tvCacheValue.text = ""
    }

    override fun checkUpdateSuccess() {
        updataDialog.dismiss()
        SmartToast.info("已经是最新版本")
    }

    override fun onError(ex: String) {
        SmartToast.error(ex)
    }

}