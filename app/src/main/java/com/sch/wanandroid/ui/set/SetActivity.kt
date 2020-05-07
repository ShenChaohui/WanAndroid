package com.sch.wanandroid.ui.set

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.coder.zzq.smartshow.dialog.DialogBtnClickListener
import com.coder.zzq.smartshow.dialog.EnsureDialog
import com.coder.zzq.smartshow.dialog.LoadingDialog
import com.coder.zzq.smartshow.dialog.SmartDialog
import com.coder.zzq.smartshow.toast.SmartToast
import com.sch.wanandroid.R
import com.sch.wanandroid.base.BaseActivity
import com.sch.wanandroid.constants.Constants
import com.sch.wanandroid.util.AppManager
import com.sch.wanandroid.util.CacheDataManager
import com.sch.wanandroid.util.PrefUtils
import com.zs.wanandroid.event.LogoutEvent
import kotlinx.android.synthetic.main.activity_set.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by Sch.
 * Date: 2020/4/26
 * description:
 */
class SetActivity : BaseActivity<SetContract.IPresenter>(), SetContract.IView {
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
                    mPresenter?.logout()
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
            mPresenter?.clearCache(this)
        }
        tvVersions.setOnClickListener {
            updataDialog.showInActivity(this)
            mPresenter?.checkUpdate()
        }
        tvProject.setOnClickListener {
            val intent = Intent()
            intent.setData(Uri.parse("https://github.com/ShenChaohui/WanAndroid"))
            intent.setAction(Intent.ACTION_VIEW)
            startActivity(intent)
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

    override fun createPresenter(): SetContract.IPresenter? {
        return SetPresenterImpl()
    }

}