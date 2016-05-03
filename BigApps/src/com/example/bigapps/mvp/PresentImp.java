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
 * @time 2016-5-3 下午9:09:41
 * MVP中P的具体实现，主要作中转到M及修饰M中返回的数据，再回调到V中
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
		Log.i(TAG, "onCreate 开始更新数据");
		mView.setText("开始更新数据");
		mModle.setData(new ICallback() {
			
			@Override
			public void onResult(String string) {
				//TODO 此处对Modle返回的数据进行封装处理，返回到View中，供View直接处理
				String result = "Present 处理了 Model的值为：" + string;
				mView.setText(result);
			}
		});
	}

	@Override
	public void performClick(View view) {
		
		mModle.performClick(view);
	}

}
