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
 * @time 2016-3-29 ����11:57:40
 * 
 * ��ȡ�ߴ���ع�����
 * 
 * px (pixels)���� �C �����أ�������Ļ��ʵ�ʵ����ص㵥λ
 * dip��dp (device independent pixels)�豸�������أ� ���豸��Ļ�й�
 * dpi(dot per inch):��Ļ�����ܶȣ�ÿӢ���������
 * 
 * ���㹫ʽ��px = dip * (dpi / 160)
 * DisplayMetrics�е�density = dpi / 160 
 * DisplayMetrics�е�densityDpi����dpi
 */
public class SizeUtils {
	private static final String TAG = "SizeUtils";
	/**
	 * ��ȡ��Ļ����
	 * @param context
	 * @return
	 */
	public static DisplayMetrics getScreenMatrics(Context context)
	{
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		Log.i(TAG, "��Ļ�ߴ� " +outMetrics.toString());
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
