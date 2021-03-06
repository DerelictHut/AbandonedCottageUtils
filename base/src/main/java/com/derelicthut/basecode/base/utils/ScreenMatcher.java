package com.derelicthut.basecode.base.utils;

import android.app.Activity;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.EncodeUtils;

/**
 * @author ...
 */
public class ScreenMatcher {

    //切图基准
    private static final float with = 375f;
    private static final float height = 667f;

    //原始值
    private static float mSrcDensity;
    private static float mSrcScaledDensity;
    private static int mSrcDensityDpi;
    //修正值
    private static float mFixDensity;
    private static float mFixScaledDensity;
    private static int mFixDensityDpi;

    public static void fix() {
        DisplayMetrics metrics = ResUtils.getResources().getDisplayMetrics();
        if (mSrcDensity == 0) {
            mSrcDensity = metrics.density;
            mSrcScaledDensity = metrics.scaledDensity;
            mSrcDensityDpi = metrics.densityDpi;
            ResUtils.getContext().registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        mSrcScaledDensity = ResUtils.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        if (mFixDensity == 0) {
//            float hDensity = metrics.heightPixels / height;
            float wDensity = metrics.widthPixels / with;
            mFixDensity = wDensity;
            mFixScaledDensity = mFixDensity * (mSrcScaledDensity / mSrcDensity);
            mFixDensityDpi = (int) (mFixDensity * 160);
        }
        metrics.density = mFixDensity;
        metrics.scaledDensity = mFixScaledDensity;
        metrics.densityDpi = mFixDensityDpi;

        Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity != null){
            //修改当前activity
            DisplayMetrics activityDisplayMetrics = topActivity.getResources().getDisplayMetrics();
            activityDisplayMetrics.density = mFixDensity;
            activityDisplayMetrics.scaledDensity = mFixScaledDensity;
            activityDisplayMetrics.densityDpi = mFixDensityDpi;
        }
    }

    public static void recover(){
        DisplayMetrics metrics = ResUtils.getResources().getDisplayMetrics();
        metrics.density = mSrcDensity;
        metrics.scaledDensity = mSrcScaledDensity;
        metrics.densityDpi = mSrcDensityDpi;
        Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity != null){
            //修改当前activity
            DisplayMetrics activityDisplayMetrics = topActivity.getResources().getDisplayMetrics();
            activityDisplayMetrics.density = mSrcDensity;
            activityDisplayMetrics.scaledDensity = mSrcScaledDensity;
            activityDisplayMetrics.densityDpi = mSrcDensityDpi;
        }
    }

    public static void recoverDensity(){
        DisplayMetrics metrics = ResUtils.getResources().getDisplayMetrics();
        metrics.density = mSrcDensity;
    }

    public static float getSrcDensity(){
        return mSrcDensity;
    }

    public static float getFixDensity(){
        return mFixDensity;
    }
}
