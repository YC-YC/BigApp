/**
 * 
 */
package com.example.bigapps.mvp;

import android.view.View;

/**
 * @author YC
 * @time 2016-5-3 下午9:17:16
 */
public interface IModle {
	/** modle一般是比较耗时的操作，因此通过callback回调方法通过P */
	public void setData(ICallback callback);

	public interface ICallback {
		public void onResult(String string);
	}
	
	/**做一些操作*/
	public void performClick(View view);
}
