/**
 * 
 */
package com.example.bigapps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * @author YC
 * @time 2016-5-3 上午9:23:23
 * 启动页
 */
public class LaunchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		/**定时方式*/
		/*new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				startActivity(new Intent(LaunchActivity.this, MainActivity.class));
				LaunchActivity.this.finish();
			}
		}, 1000);*/
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				runOnUiThread(new Runnable() {//返回主线程（UI线程）,和Handle的post一样原理
					@Override
					public void run() {
						startActivity(new Intent(LaunchActivity.this, MainActivity.class));
						LaunchActivity.this.finish();
					}
				});
			}
		}).start();
	}
}
