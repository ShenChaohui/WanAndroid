package com.sch.wanandroid;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

import com.coder.zzq.smartshow.core.SmartShow;
import com.sch.wanandroid.constants.Constants;
import com.sch.wanandroid.util.ColorUtils;
import com.sch.wanandroid.util.PrefUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

import org.xutils.x;

public class WanAndroidApplication extends Application {
    private static Application baseApplication;

    //static 代码段可以防止内存泄露
    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new MaterialHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                //指定为经典Footer，默认是 BallPulseFooter
                BallPulseFooter footer = new BallPulseFooter(context);
                footer.setAnimatingColor(ColorUtils.parseColor(R.color.colorPrimary));
                footer.setBackgroundColor(ColorUtils.parseColor(R.color.theme_1));
                return footer;
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        baseApplication = this;
        SmartShow.init(this);

    }

    public static Context getContext() {
        return baseApplication;
    }
}
