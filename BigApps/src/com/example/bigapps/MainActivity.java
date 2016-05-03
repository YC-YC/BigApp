package com.example.bigapps;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigapps.circlemenu.CircleActivity;
import com.example.bigapps.customview.CustomImageViewActivity;
import com.example.bigapps.horizonlist.HorizonListActivity;
import com.example.bigapps.horizonlist.HorizonListActivity2;
import com.example.bigapps.horizonlist.HorizonListActivity3;
import com.example.bigapps.horizonlist.TestActivity;
import com.example.bigapps.mvp.IPresent;
import com.example.bigapps.mvp.IView;
import com.example.bigapps.mvp.PresentImp;
import com.example.bigapps.slidedel.SlideDelActivity;
import com.example.bigapps.testviewpager.MainViewPager;
import com.example.bigapps.testviewpager.MainViewPager2;

public class MainActivity extends Activity implements IView{

	private TextView mTvContent;
	
	/**MVP框架*/
	private IPresent mPresent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTvContent = (TextView) findViewById(R.id.tvContent);
		mPresent = new PresentImp(this, this);
		mPresent.onCreate();
	}

	private boolean bQuit = false;
	@Override
	public void onBackPressed() {
		if (!bQuit){
			Toast.makeText(this, "再按一次返回退出", 500).show();
			new Timer().schedule(new TimerTask() {
				
				@Override
				public void run() {
					bQuit = false;
				}
			}, 2000);
			bQuit = true;
		} else {
			super.onBackPressed();
			finish();
		}
	}

	
	public void doClick(View view)
	{
		//TODO 一般做一些处理处理的操作，在这个例子里只是打开一个Activity有点小题大作了（只作演示用）
		mPresent.performClick(view);
	}


	@Override
	public void setText(final String text) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mTvContent.setText(text);
			}
		});
	}

}
