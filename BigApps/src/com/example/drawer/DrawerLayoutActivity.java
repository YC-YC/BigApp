/**
 * 
 */
package com.example.drawer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.example.bigapps.R;

/**
 * @author YC
 * @time 2016-6-29 下午5:42:34
 * TODO
 */
public class DrawerLayoutActivity extends Activity {

	protected static final String TAG = "DrawerLayoutActivity";
	private DrawerLayout mDrawerLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawerlayout);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setDrawerListener(new DrawerListener() {
			
			@Override
			public void onDrawerStateChanged(int state) {
				// TODO Auto-generated method stub
				Log.i(TAG, "onDrawerStateChanged state = " + state);
				
			}
			
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				Log.i(TAG, "onDrawerSlide tag = " + drawerView.getTag() + ", slideOffset = " + slideOffset);
			}
			
			@Override
			public void onDrawerOpened(View drawerView) {
				Log.i(TAG, "onDrawerOpened tag = " + drawerView.getTag());
			}
			
			@Override
			public void onDrawerClosed(View drawerView) {
				Log.i(TAG, "onDrawerClosed tag = " + drawerView.getTag());
			}
		});
		
		//锁住右边，只能编码打开右边
		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
		
	}
	
	public void openLeft(View view){
		mDrawerLayout.openDrawer(Gravity.LEFT);
		
	}

	public void openMid(View view){
		mDrawerLayout.closeDrawers();
	}
	
	public void openRight(View view){
		mDrawerLayout.openDrawer(Gravity.RIGHT);
		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.RIGHT);
		
	}
	
}
