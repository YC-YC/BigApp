/**
 * 
 */
package com.example.bigapps.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

/**
 * @author YC
 * @time 2016-3-29 上午11:57:40
 * 
 * 获取尺寸相关工具类
 * 
 * px (pixels)像素 – 是像素，就是屏幕上实际的像素点单位
 * dip或dp (device independent pixels)设备独立像素， 与设备屏幕有关
 * dpi(dot per inch):屏幕像素密度，每英寸多少像素
 * 
 * 换算公式：px = dip * (dpi / 160)
 * DisplayMetrics中的density = dpi / 160 
 * DisplayMetrics中的densityDpi就是dpi
 */
public class SizeUtils {
	private static final String TAG = "SizeUtils";
	/**
	 * 获取屏幕参数
	 * @param context
	 * @return
	 */
	public static DisplayMetrics getScreenMatrics(Context context)
	{
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		Log.i(TAG, "屏幕尺寸 " +outMetrics.toString());
		return outMetrics;
	}
	
	/**
	 * dip -->px
	 */
	public static int dip2px(Context context, int dipValue)
	{
		return (int) (getScreenMatrics(context).density*dipValue + 0.5f);
	}
	
	/**
	 * px -->dip
	 */
	public static int dx2dip(Context context, int pxValue)
	{
		return (int) (pxValue/getScreenMatrics(context).density);
	}
}
