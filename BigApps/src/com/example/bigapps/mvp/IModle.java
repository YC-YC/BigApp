/**
 * 
 */
package com.example.bigapps.mvp;

import android.view.View;

/**
 * @author YC
 * @time 2016-5-3 ����9:17:16
 */
public interface IModle {
	/** modleһ���ǱȽϺ�ʱ�Ĳ��������ͨ��callback�ص�����ͨ��P */
	public void setData(ICallback callback);

	public interface ICallback {
		public void onResult(String string);
	}
	
	/**��һЩ����*/
	public void performClick(View view);
}
