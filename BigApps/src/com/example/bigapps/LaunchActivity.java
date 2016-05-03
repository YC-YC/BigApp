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
 * @time 2016-5-3 ����9:23:23
 * ����ҳ
 */
public class LaunchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		/**��ʱ��ʽ*/
		/*new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				startActivity(new Intent(LaunchActivity.this, MainActivity.class));
				LaunchActivity.this.finish();
			}
		}, 3000);*/
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				runOnUiThread(new Runnable() {//�������̣߳�UI�̣߳�,��Handle��postһ��ԭ��
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