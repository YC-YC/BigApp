package com.example.bigapps.testviewpager;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 *@Author Administrator
 *@Time 2016-3-19 下午11:46:16
 */
@SuppressLint("NewApi")
public class RotatePageTransformer implements ViewPager.PageTransformer{

	private static final String tag = "RotatePageTransformer"; 
	private static final float MAX_ROTATE = 20f;	//最大旋转角度
	private float mRotate;//当前旋转角度
	
	@Override
	public void transformPage(View view, float position) {
		Log.i(tag, "position = " + position);
		//position指位置，从[-1,1]
		
		 if (position < -1) { // [-Infinity,-1)
	            // This page is way off-screen to the left.
			 view.setRotation(0);

	        } else if (position <= 0) { // [-1,0]对应[-20,0]
	            // Use the default slide transition when moving to the left page

	        	view.setPivotX(view.getWidth()/2);//设置缩放点为底部中心
	        	view.setPivotY(view.getHeight());
	        	mRotate = position * MAX_ROTATE;
	        	view.setRotation(mRotate);
	        } else if (position <= 1) { // (0,1]对应[0,20]
	            // Fade the page out.
	        	view.setPivotX(view.getWidth()/2);//设置缩放点为底部中心
	        	view.setPivotY(view.getHeight());
	        	mRotate = position * MAX_ROTATE;
	        	view.setRotation(mRotate);
	        } else { // (1,+Infinity]
	            // This page is way off-screen to the right.
	        	 view.setRotation(0);
	        }
	}

}
