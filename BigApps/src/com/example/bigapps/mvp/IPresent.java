/**
 * 
 */
package com.example.bigapps.mvp;

import android.view.View;

/**
 * @author YC
 * @time 2016-5-3 ����8:57:59
 * MVP�е�P,��Ҫ������ʵ��View��Model֮ǰ�Ľ���
 */
public interface IPresent {
	
	public void onCreate();
	
	public void performClick(View view);
}
