/**
 * 
 */
package com.example.drawer;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

import com.example.bigapps.R;

/**
 * @author YC
 * @time 2016-6-30 上午10:07:34
 * TODO SlidingDrawer实现抽屉效果
 */
@SuppressWarnings("deprecation")
public class SlidingDrawerActivity extends Activity {

	protected static final String TAG = "SlidingDrawerActivity";
	private SlidingDrawer mSlidingDrawer;
	private ImageView mHandle;
	private GridView mContent;
	private List<ResolveInfo> apps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_slidingdrawer_horizontal);
		
		loadApps();
		
//		mSlidingDrawer = (SlidingDrawer) findViewById(R.id.slidingdrawer);
		mSlidingDrawer = (SlidingDrawer) findViewById(R.id.slidingdrawer);
		mHandle = (ImageView) findViewById(R.id.handle1);
//		mContent = (GridView) findViewById(R.id.content);
//		mContent.setAdapter(new MyGrideAdapter());
		mSlidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			
			@Override
			public void onDrawerClosed() {
				Log.i(TAG, "onDrawerClosed");
				mHandle.setImageResource(R.drawable.horizontaltoopen);
			}
		});
		mSlidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			
			@Override
			public void onDrawerOpened() {
				Log.i(TAG, "onDrawerOpened");
				mHandle.setImageResource(R.drawable.horizontaltoclose);
			}
		});
	}

	private void loadApps() {
		Intent it = new Intent(Intent.ACTION_MAIN, null);
		it.addCategory(Intent.CATEGORY_LAUNCHER);
		apps = getPackageManager().queryIntentActivities(it, 0);
	}
	
	private class MyGrideAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return apps.size();
		}

		@Override
		public Object getItem(int position) {
			return apps.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = null;
			if (convertView == null){
				imageView = new ImageView(SlidingDrawerActivity.this);
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				imageView.setLayoutParams(new GridView.LayoutParams(50, 50));
			}else{
				imageView = (ImageView) convertView;
			}
			
			ResolveInfo info = apps.get(position);
			imageView.setImageDrawable(info.activityInfo.loadIcon(getPackageManager()));
			
			return imageView;
		}
		
	}
}
