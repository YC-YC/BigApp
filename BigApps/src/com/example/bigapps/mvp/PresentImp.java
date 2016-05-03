/**
 * 
 */
package com.example.bigapps.mvp;

import com.example.bigapps.R;
import com.example.bigapps.circlemenu.CircleActivity;
import com.example.bigapps.customview.CustomImageViewActivity;
import com.example.bigapps.horizonlist.HorizonListActivity;
import com.example.bigapps.horizonlist.HorizonListActivity2;
import com.example.bigapps.horizonlist.HorizonListActivity3;
import com.example.bigapps.horizonlist.TestActivity;
import com.example.bigapps.mvp.IModle.ICallback;
import com.example.bigapps.slidedel.SlideDelActivity;
import com.example.bigapps.testviewpager.MainViewPager;
import com.example.bigapps.testviewpager.MainViewPager2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;


/**
 * @author YC
 * @time 2016-5-3 ����9:09:41
 * MVP��P�ľ���ʵ�֣���Ҫ����ת��M������M�з��ص����ݣ��ٻص���V��
 */
public class PresentImp implements IPresent {

	private static final String TAG = "PresentImp";
	private Context mContext;
	private IView mView;
	private IModle mModle;
	
	public PresentImp(Context mContext, IView view) {
		super();
		this.mContext = mContext;
		mView = view;
		mModle = new Modle(mContext);
	}

	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate ��ʼ��������");
		mView.setText("��ʼ��������");
		mModle.setData(new ICallback() {
			
			@Override
			public void onResult(String string) {
				//TODO �˴���Modle���ص����ݽ��з�װ�������ص�View�У���Viewֱ�Ӵ���
				String result = "Present ������ Model��ֵΪ��" + string;
				mView.setText(result);
			}
		});
	}

	@Override
	public void performClick(View view) {
		
		mModle.performClick(view);
	}

}
