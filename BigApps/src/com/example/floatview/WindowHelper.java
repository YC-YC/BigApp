/**
 * 
 */
package com.example.floatview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerScrollListener;

import com.example.bigapps.R;

/**
 * 
 * @author YC
 * @time 2016-6-29 下午12:03:42
 * TODO 悬浮窗口管理
 */
public class WindowHelper {
	
	protected static final String TAG = "WindowHelper";

	private static WindowHelper instances;
	public static WindowHelper getInstaces(){
		if (instances == null){
			synchronized (WindowHelper.class) {
				if (instances == null){
					instances = new WindowHelper();
				}
			}
		}
		return instances;
	}
	
	WindowManager mWindowManager;
	
	private View mSmallView, mBigView;
	private WindowManager.LayoutParams mSmallViewParams, mBigViewParams;
	
	private SlidingDrawer slidingDrawer;
	private WindowManager manager;
	@SuppressWarnings("deprecation")
	public boolean createSmallView(Context context){
		Log.i(TAG, "createSmallView");
		manager = getWindowManager(context);
		if (mSmallView == null){
			mSmallView = LayoutInflater.from(context.getApplicationContext())
					.inflate(R.layout.layout_slidingdrawer_horizontal, null);
			
			if (mSmallViewParams == null){
				mSmallViewParams = new WindowManager.LayoutParams();
				
				mSmallViewParams.type = LayoutParams.TYPE_PHONE;
//				mSmallViewParams.type = LayoutParams.TYPE_TOAST;	//添加这个类型不需要打开系统悬浮权限
				mSmallViewParams.format = PixelFormat.RGBA_8888;
				//设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）  
				mSmallViewParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE
						|LayoutParams.FLAG_TRANSLUCENT_STATUS;	//忽略状态栏
				//调整悬浮窗显示的停靠位置为左侧置顶  
				mSmallViewParams.gravity = Gravity.RIGHT; 
				
				// 以屏幕左上角为原点，设置x、y初始值，相对于gravity  
				mSmallViewParams.x = 0;  
				mSmallViewParams.y = 0;  
		  
		        //设置悬浮窗口长宽数据    
				mSmallViewParams.width = 10;  
				mSmallViewParams.height = WindowManager.LayoutParams.MATCH_PARENT;
			}
			manager.addView(mSmallView, mSmallViewParams);  
			Log.i(TAG, "addView");
			slidingDrawer = (SlidingDrawer) mSmallView.findViewById(R.id.slidingdrawer);
			slidingDrawer.setOnDragListener(new OnDragListener() {
				
				@Override
				public boolean onDrag(View v, DragEvent event) {
					Log.i(TAG, "onDrag action = " + event.getAction());
					return false;
				}
			});
			
			slidingDrawer.setOnDrawerScrollListener(new OnDrawerScrollListener() {
				
				@Override
				public void onScrollStarted() {
					Log.i(TAG, "onScrollStarted");
				}
				
				@Override
				public void onScrollEnded() {
					Log.i(TAG, "onScrollEnded");
				}
			});
			
			slidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {
				
				@Override
				public void onDrawerClosed() {
					Log.i(TAG, "onDrawerClosed");
					makeSlidingDrawInVisible();
				}
			});
			
			((Button)mSmallView.findViewById(R.id.lock)).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Log.i(TAG, "点击了加锁键");
					closeSlidingDrawer();
					makeSlidingDrawInVisible();
				}
			});
			
			LinearLayout smallbar = (LinearLayout) mSmallView.findViewById(R.id.smallbar);
			smallbar.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Log.i(TAG, "smallbar.onTouch getAction = " + event.getAction());
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						slidingDrawer.onInterceptTouchEvent(event);
						return true;
					case MotionEvent.ACTION_MOVE:
						int count = event.getPointerCount();
						Log.i(TAG, "smallbar.onTouch ACTION_MOVE count = " + count);
						if (mSmallViewParams.width != 300 /*&& count >= 3*/)
						{
							makeSlidingDrawVisible();
							openSlidingDrawer();
						}
						break;
					default:
						break;
					}
					slidingDrawer.onTouchEvent(event);
					return false;
				}
			});
			return true;
		}
		
		return false;
	}
	
	private void openSlidingDrawer(){
		slidingDrawer.open();
//		slidingDrawer.animateOpen();
	}
	
	private void closeSlidingDrawer(){
		slidingDrawer.close();
//		slidingDrawer.animateClose();
	}
	
	private void makeSlidingDrawVisible(){
		Log.i(TAG, "makeSlidingDrawVisible");
		mSmallViewParams.width = 300;
		manager.updateViewLayout(mSmallView, mSmallViewParams);
	}
	
	private void makeSlidingDrawInVisible(){
		Log.i(TAG, "makeSlidingDrawInVisible");
		mSmallViewParams.width = 10;
		manager.updateViewLayout(mSmallView, mSmallViewParams);
	}
	
	public void removeSmallView(Context context){
		 if(mSmallView != null){  
	            WindowManager windowManager = getWindowManager(context);  
	            windowManager.removeView(mSmallView);  
	            mSmallView = null;  
		 }  
	}
	
	private DrawerLayout mDrawerLayout;
	public boolean createDrawerLayoutView(Context context){
		Log.i(TAG, "createDrawerLayoutView");
		manager = getWindowManager(context);
		if (mBigView == null){
			mBigView = LayoutInflater.from(context.getApplicationContext())
					.inflate(R.layout.layout_drawerlayout, null);
			
			if (mBigViewParams == null){
				mBigViewParams = new WindowManager.LayoutParams();
				
				mBigViewParams.type = LayoutParams.TYPE_PHONE;
//				mBigViewParams.type = LayoutParams.TYPE_TOAST;	//添加这个类型不需要打开系统悬浮权限
				mBigViewParams.format = PixelFormat.RGBA_8888;
				//设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）  
				mBigViewParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE
						|LayoutParams.FLAG_TRANSLUCENT_STATUS;	//忽略状态栏
				//调整悬浮窗显示的停靠位置为左侧置顶  
				mBigViewParams.gravity = Gravity.RIGHT; 
				
				// 以屏幕左上角为原点，设置x、y初始值，相对于gravity  
				mBigViewParams.x = 0;  
				mBigViewParams.y = 0;  
		  
		        //设置悬浮窗口长宽数据    
				mBigViewParams.width = 10;  
				mBigViewParams.height = WindowManager.LayoutParams.MATCH_PARENT;

			}
			manager.addView(mBigView, mBigViewParams);  
			Log.i(TAG, "addView");
			mDrawerLayout = (DrawerLayout) mBigView.findViewById(R.id.drawer_layout);
//			openLeft();
			((Button)mDrawerLayout.findViewById(R.id.lock)).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Log.i(TAG, "点击了加锁");
					close();
				}
			});
			mDrawerLayout.setScrimColor(Color.TRANSPARENT);	//去除阴影
//			mDrawerLayout.setDrawerShadow(R.drawable.item_default, GravityCompat.START);
			mDrawerLayout.setDrawerListener(new DrawerListener() {
				
				@Override
				public void onDrawerStateChanged(int state) {
					// TODO Auto-generated method stub
					Log.i(TAG, "onDrawerStateChanged state = " + state);
				}
				
				@Override
				public void onDrawerSlide(View drawerView, float slideOffset) {
					Log.i(TAG, "onDrawerSlide tag = " + drawerView.getTag() + ", slideOffset = " + slideOffset);
//					int offset = (int) ((slideOffset*290) + 10);
//					setDrawerLayoutOffset(offset);
//					mDrawerLayout.findViewById(R.id.smallview).setAlpha(0);
				}
				
				
				@Override
				public void onDrawerOpened(View drawerView) {
					Log.i(TAG, "onDrawerOpened tag = " + drawerView.getTag());
				/*	if ("LEFT".equals(drawerView.getTag())){
						makeDrawerLayoutInVisible();
//						openRight();
					}*/
				}
				
				@Override
				public void onDrawerClosed(View drawerView) {
					Log.i(TAG, "onDrawerClosed tag = " + drawerView.getTag());
					if ("RIGHT".equals(drawerView.getTag())){
//						openLeft();	
						makeDrawerLayoutInVisible();
					}
				}
			});
			mDrawerLayout.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Log.i(TAG, "onDrawer onTouch action = " + event.getAction());
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						//将事件设置到右边的边距上
						event.setLocation(event.getX()+300, event.getY());
						makeDrawerLayoutVisible();
						break;

					default:
						break;
					}
					return false;
				}
			});
			return true;
		}
		
		return false;
	}
	
	private void openLeft(){
		mDrawerLayout.openDrawer(Gravity.LEFT);
		Log.i(TAG, "openLeft Drawer");
	}
	
	private void openRight(){
//		mDrawerLayout.openDrawer(Gravity.RIGHT);
		mDrawerLayout.openDrawer(mBigView.findViewById(R.id.right));
		Log.i(TAG, "openRight Drawer");
	}
	
	private void close(){
		mDrawerLayout.closeDrawers();
	}
	
	private void makeDrawerLayoutVisible(){
		Log.i(TAG, "makeDrawerLayoutVisible");
		mBigViewParams.width = 300;
		manager.updateViewLayout(mBigView, mBigViewParams);
	}
	
	private void setDrawerLayoutOffset(int offset){
		Log.i(TAG, "setDrawerLayoutOffset offset = " + offset);
		mBigViewParams.width = offset;
		manager.updateViewLayout(mBigView, mBigViewParams);
	}
	
	
	private void makeDrawerLayoutInVisible(){
		Log.i(TAG, "makeDrawerLayoutInVisible");
		mBigViewParams.width = 10;
		manager.updateViewLayout(mBigView, mBigViewParams);
	}
	
	public void removeDrawerLayoutView(Context context){
		 if(mBigView != null){  
	            WindowManager windowManager = getWindowManager(context);  
	            windowManager.removeView(mBigView);  
	            mBigView = null;  
		 }  
	}
	
	
	
	private WindowHelper(){
		
	}
	
	private WindowManager getWindowManager(Context context){
		if (mWindowManager == null){
			 mWindowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);  
		}
		return mWindowManager;
	}
	
}
