package com.sch.playandroid.ui.login

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import com.coder.zzq.smartshow.toast.SmartToast
import com.sch.playandroid.R
import com.sch.playandroid.base.BaseActivity
import com.sch.playandroid.constants.Constants
import com.sch.playandroid.entity.UserBean
import com.sch.playandroid.ui.regist.RegistActivity
import com.sch.playandroid.util.AppManager
import com.sch.playandroid.util.PrefUtils
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by Sch.
 * Date: 2020/4/24
 * description:
 */
class LoginActivity : BaseActivity(), LoginConstant.ILoginView {
    private val presenterImpl by lazy { LoginPresenterImpl(this) }

    /**
     * 是否显示明文
     */
    private var isPasswordShow = false

    override fun init(savedInstanceState: Bundle?) {
        addListener()
    }

    private fun addListener() {
        llLogin.setOnClickListener {
            val userName = etUsername.text.toString()
            val passWord = etPassword.text.toString()
            when {
                userName.isEmpty() -> {
                    etUsername.setError("账号不能为空")
                }
                passWord.isEmpty() -> {
                    etPassword.setError("密码不能为空")
                }
                else -> {
                    setViewStatus(false)
                    presenterImpl.doLogin(userName, passWord)
                }
            }
        }
        ivClearUserName.setOnClickListener {
            etUsername.requestFocus()
            etUsername.setText("")
        }
        ivPasswordVisibility.setOnClickListener {
            etPassword.requestFocus()
            if (isPasswordShow) {
                isPasswordShow = false
                ivPasswordVisibility.setImageResource(R.mipmap.password_hide)
                etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                isPasswordShow = true
                ivPasswordVisibility.setImageResource(R.mipmap.password_show)
                etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            etPassword.setSelection(etPassword.text.length)
        }
        tvCancleLogin.setOnClickListener {
            finish()
        }
        tvRegistAccont.setOnClickListener {
            intent(RegistActivity::class.java, false)
        }

    }

    /**
     * 登录时所有控件变为不可点击
     */
    private fun setViewStatus(lockStatus: Boolean) {
        llLogin.isEnabled = lockStatus
        etUsername.isEnabled = lockStatus
        etPassword.isEnabled = lockStatus
        ivPasswordVisibility.isEnabled = lockStatus
        ivClearUserName.isEnabled = lockStatus
        tvRegistAccont.isEnabled = lockStatus
        if (lockStatus) {
            tvLoginTxt.visibility = View.VISIBLE
            indicatorView.visibility = View.GONE
            indicatorView.hide()
        } else {
            tvLoginTxt.visibility = View.GONE
            indicatorView.visibility = View.VISIBLE
            indicatorView.show()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun loginSuccess() {
        PrefUtils.setBoolean(Constants.LOGIN, true)
        finish()
    }

    override fun onError(ex: String) {
        setViewStatus(true)
        SmartToast.fail(ex)
    }
}