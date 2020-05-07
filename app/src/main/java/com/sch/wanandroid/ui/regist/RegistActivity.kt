package com.sch.wanandroid.ui.regist

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.coder.zzq.smartshow.toast.SmartToast
import com.sch.wanandroid.R
import com.sch.wanandroid.base.BaseActivity
import kotlinx.android.synthetic.main.activity_regist.*

/**
 * Created by Sch.
 * Date: 2020/4/24
 * description:
 */
class RegistActivity : BaseActivity<RegisterConstant.IPresenter>(), RegisterConstant.IView {
    /**
     * 密码是否显示明文
     */
    private var isPasswordShow = false

    /**
     * 确认密码是否显示明文
     */
    private var isRePasswordShow = false

    override fun init(savedInstanceState: Bundle?) {
        ivBack.setOnClickListener {
            finish()
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
        ivRePasswordVisibility.setOnClickListener {
            etRePassword.requestFocus()
            if (isRePasswordShow) {
                isRePasswordShow = false
                ivRePasswordVisibility.setImageResource(R.mipmap.password_hide)
                etRePassword.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                isRePasswordShow = true
                ivRePasswordVisibility.setImageResource(R.mipmap.password_show)
                etRePassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            etRePassword.setSelection(etRePassword.text.length)
        }
        llRegister.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val rePassword = etRePassword.text.toString()
            when {
                username.isEmpty() -> etUsername.setError("账号不能为空")
                password.isEmpty() -> etPassword.setError("密码不能为空")
                rePassword.isEmpty() -> etRePassword.setError("请再次输入密码")
                password != rePassword -> SmartToast.error("两次密码不一致")
                else -> {
                    setViewStatus(false)
                    mPresenter?.doRegister(username, password, rePassword)
                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_regist
    }

    /**
     * 注册时给具备点击事件的View上锁，失败时解锁
     * 并且施加动画
     */
    private fun setViewStatus(lockStatus: Boolean) {
        llRegister.isEnabled = lockStatus
        etUsername.isEnabled = lockStatus
        etPassword.isEnabled = lockStatus
        etRePassword.isEnabled = lockStatus
        if (lockStatus) {
            tvRegisterTxt.visibility = View.VISIBLE
            indicatorView.visibility = View.GONE
            indicatorView.hide()
        } else {
            tvRegisterTxt.visibility = View.GONE
            indicatorView.visibility = View.VISIBLE
            indicatorView.show()
        }
    }

    override fun registerSuccess() {
        SmartToast.success("注册成功")
        finish()
    }

    override fun onError(ex: String) {
        setViewStatus(true)
        SmartToast.error(ex)
    }

    override fun createPresenter(): RegisterConstant.IPresenter? {
        return RegisterPresenterImpl()
    }
}