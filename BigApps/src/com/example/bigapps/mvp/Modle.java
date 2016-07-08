/**
 * 
 */
package com.example.bigapps.mvp;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.bigapps.R;
import com.example.bigapps.circlemenu.CircleActivity;
import com.example.bigapps.customview.CustomImageViewActivity;
import com.example.bigapps.floding.MainFlodingActivity;
import com.example.bigapps.customview.SwipeActivity;
import com.example.bigapps.horizonlist.HorizonListActivity;
import com.example.bigapps.horizonlist.HorizonListActivity2;
import com.example.bigapps.horizonlist.HorizonListActivity3;
import com.example.bigapps.horizonlist.TestActivity;
import com.example.bigapps.picasso.PicassoActivity;
import com.example.bigapps.progressbar.MainTestProgressbar;
import com.example.bigapps.recyclerview.RecyclerViewActivity;
import com.example.bigapps.slidedel.SlideDelActivity;
import com.example.bigapps.testviewpager.MainViewPager;
import com.example.bigapps.testviewpager.MainViewPager2;
import com.example.download.DownloadActivity;
import com.example.drawer.DrawerLayoutActivity;
import com.example.drawer.SlidingDrawerActivity;
import com.example.floatview.WindowHelper;
import com.example.preferences.MainPreferenceActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.bigapps.verticalseekbar.VerticalSeekBarActivity;
import com.example.bigapps.wheel.WheelActivity;

/**
 * @author YC
 * @time 2016-5-3 下午9:15:12
 * MVP中的M,作数据的处理，一般是耗时的操作
 */

public class Modle implements IModle{

	private Context mContext;
	public Modle(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public void setData(final ICallback callback) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					//在这里延时模拟耗时操作
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				callback.onResult("Modle");
			}
		}).start();
	}

	@Override
	public void performClick(View view) {
		switch (view.getId()) {
		case R.id.button1:
			mContext.startActivity(new Intent(mContext, MainViewPager.class));
			break;
		case R.id.button2:
			mContext.startActivity(new Intent(mContext, MainViewPager2.class));
			break;
		case R.id.button3:
			mContext.startActivity(new Intent(mContext, CircleActivity.class));
			break;
		case R.id.button4:
			mContext.startActivity(new Intent(mContext, SlideDelActivity.class));
			break;
		case R.id.button5:
			mContext.startActivity(new Intent(mContext, HorizonListActivity.class));
			break;	
		case R.id.button6:
			mContext.startActivity(new Intent(mContext, HorizonListActivity2.class));
			break;	
		case R.id.button7:
			mContext.startActivity(new Intent(mContext, HorizonListActivity3.class));
			break;
		case R.id.button8:
			mContext.startActivity(new Intent(mContext, CustomImageViewActivity.class));
			break;
		case R.id.button9:
			mContext.startActivity(new Intent(mContext, TestActivity.class));
			break;
		case R.id.button10:
			mContext.startActivity(new Intent(mContext, DownloadActivity.class));
			break;
		case R.id.button11:
			mContext.startActivity(new Intent(mContext, MainFlodingActivity.class));
			break;
		case R.id.btnswipe:
			mContext.startActivity(new Intent(mContext, SwipeActivity.class));
			break;
		case R.id.btnwheel:
			mContext.startActivity(new Intent(mContext, WheelActivity.class));
			break;
		case R.id.btnpicasso:
			mContext.startActivity(new Intent(mContext, PicassoActivity.class));
			break;
		case R.id.btnprogressbar:
			mContext.startActivity(new Intent(mContext, MainTestProgressbar.class));
			break;
		case R.id.btnverticalseekbar:
			mContext.startActivity(new Intent(mContext, VerticalSeekBarActivity.class));
			break;
		case R.id.btrecyclerview:
			mContext.startActivity(new Intent(mContext, RecyclerViewActivity.class));
			break;
		case R.id.floatview_drawerlayout:
//			WindowHelper.getInstaces().createSmallView(mContext);
			WindowHelper.getInstaces().removeSmallView(mContext);
			WindowHelper.getInstaces().createDrawerLayoutView(mContext);
			break;
		case R.id.floatview_slidingdrawer:
			WindowHelper.getInstaces().removeDrawerLayoutView(mContext);
			WindowHelper.getInstaces().createSmallView(mContext);
			break;
		case R.id.drawerlayout:
			mContext.startActivity(new Intent(mContext, DrawerLayoutActivity.class));
			break;
		case R.id.slidingdrawlayout:
			mContext.startActivity(new Intent(mContext, SlidingDrawerActivity.class));
			break;
		case R.id.testpreference:
			mContext.startActivity(new Intent(mContext, MainPreferenceActivity.class));
			break;
		default:
			break;
		}
	}

}
