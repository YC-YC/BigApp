/**
 * 
 */
package com.example.bigapps.mvp;

import android.view.View;

/**
 * @author YC
 * @time 2016-5-3 下午8:55:43
 * MVP中的V,主要作用是定义View要实现的接口,一般为P经过M处理后回调的作用
 */
public interface IView {
	
	public void setText(String text);
}
