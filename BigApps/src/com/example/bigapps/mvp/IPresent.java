/**
 * 
 */
package com.example.bigapps.mvp;

import android.view.View;

/**
 * @author YC
 * @time 2016-5-3 下午8:57:59
 * MVP中的P,主要作用是实现View与Model之前的交互
 */
public interface IPresent {
	
	public void onCreate();
	public void performClick(View view);
}
