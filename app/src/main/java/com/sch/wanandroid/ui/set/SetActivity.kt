package com.sch.wanandroid.ui.set

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.coder.zzq.smartshow.dialog.DialogBtnClickListener
import com.coder.zzq.smartshow.dialog.EnsureDialog
import com.coder.zzq.smartshow.dialog.LoadingDialog
import com.coder.zzq.smartshow.dialog.SmartDialog
import com.coder.zzq.smartshow.toast.SmartToast
import com.sch.wanandroid.R
import com.sch.wanandroid.base.BaseActivity
import com.sch.wanandroid.constants.Constants
import com.sch.wanandroid.event.NightModel
import com.sch.wanandroid.ui.main.MainActivity
import com.sch.wanandroid.ui.web.WebActivity
import com.sch.wanandroid.util.AppManager
import com.sch.wanandroid.util.CacheDataManager
import com.sch.wanandroid.util.PrefUtils
import com.zs.wanandroid.event.LogoutEvent
import kotlinx.android.synthetic.main.activity_set.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by Sch.
 * Date: 2020/4/26
 * description:设置
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
    private var copyrightDialog: Dialog? = null

    private var appAuthorInfoDialog: Dialog? = null

    override fun init(savedInstanceState: Bundle?) {
        initListener()
        initAppAuthorInfoDialog()
        initCopyrightInfoDialog()
        tvCacheValue.text = CacheDataManager.getTotalCacheSize(this)
        tvVersionsValue.text = "v ${AppManager.getVersionName(this)}"
        switchNightModel.isChecked = PrefUtils.getBoolean(Constants.NIGHT_MODEL, false)
    }

    private fun initAppAuthorInfoDialog() {
        val builder = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_app_author_info, null)
        val tvConfirm = view.findViewById<TextView>(R.id.tvConfirm)
        tvConfirm.setOnClickListener {
            appAuthorInfoDialog?.dismiss()
        }
        val tvWx = view.findViewById<TextView>(R.id.tvWx)
        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)
        val tvJianShu = view.findViewById<TextView>(R.id.tvJianShu)
        tvWx.setOnClickListener {
            AppManager.copy(this, this.getString(R.string.AppAuthorWX))
            appAuthorInfoDialog?.dismiss()

        }
        tvEmail.setOnClickListener {
            AppManager.copy(this, this.getString(R.string.AppAuthorEmail))
            appAuthorInfoDialog?.dismiss()

        }
        tvJianShu.setOnClickListener {
            AppManager.copy(this, this.getString(R.string.AppAuthorJanshu))
            appAuthorInfoDialog?.dismiss()

        }
        builder.setView(view)
        appAuthorInfoDialog = builder.create()
    }

    private fun initCopyrightInfoDialog() {
        val builder = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_copyright_info, null)
        val tvConfirm = view.findViewById<TextView>(R.id.tvConfirm)
        tvConfirm.setOnClickListener {
            copyrightDialog?.dismiss()
        }
        builder.setView(view)
        copyrightDialog = builder.create()
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
            intent(Bundle().apply {
                putString(Constants.WEB_URL, getString(R.string.AppAuthorGithub))
                putString(Constants.WEB_TITLE, getString(R.string.app_name))
            }, WebActivity::class.java, false)
        }
        tvAuthor.setOnClickListener {
            appAuthorInfoDialog?.show()
        }
        tvCopyright.setOnClickListener {
            copyrightDialog?.show()
        }
        tvNightModel.setOnClickListener {
            if (switchNightModel.isChecked) {
                switchNightModel.isChecked = false
                PrefUtils.setBoolean(Constants.NIGHT_MODEL, false)

            } else {
                switchNightModel.isChecked = true
                PrefUtils.setBoolean(Constants.NIGHT_MODEL, true)

            }
            window.setWindowAnimations(R.style.NightModelAnim)
            switchNightModel.postDelayed(Runnable {
                EventBus.getDefault().post(NightModel())
                recreate()
            },200)
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