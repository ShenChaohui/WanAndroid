package com.sch.wanandroid.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.sch.wanandroid.PlayAndroidApplication
import com.sch.wanandroid.R
import com.sch.wanandroid.widget.GlideRoundTransform


/**
 * des 图片加载代理类
 *
 * @author zs
 * @date 2020-03-14
 */
class ImageLoad{
    companion object{

        /**
         * 默认加载
         */
        fun load(imageView: ImageView,url:String?){
            url.let {
                Glide.with(imageView.context)
                    .load(url)
                    .centerCrop()
                    .transition(withCrossFade())
                    //.placeholder(R.mipmap.image_holder)
                    //.error(R.mipmap.image_holder)
                    .into(imageView)
            }
        }

        /**
         * 加载圆角图片
         */
        fun loadRadius(imageView: ImageView,url:String?,round:Int){
            url?.let {
                Glide.with(imageView.context)
                    .load(url)
                    .centerCrop()
                    .transition(withCrossFade())
                    .transform(GlideRoundTransform(imageView.context,round))
                    //.placeholder(R.mipmap.internet_error)
                    .into(imageView)
            }

        }

        /**
         * 加载圆形图片
         */
        fun loadRound(imageView: ImageView,url:String,round:Int){
            Glide.with(imageView.context)
                .load(url)
                .centerCrop()
                .transition(withCrossFade())
                .placeholder(R.mipmap.internet_error)
                .transform(RoundedCorners(8))
                .into(imageView)
        }

        /**
         * 清除缓存
         */
        fun clearCache(){
            Glide.get(PlayAndroidApplication.getContext()).clearMemory()
            System.gc()
        }
    }
}