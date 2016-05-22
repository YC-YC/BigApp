/**
 * 
 */
package com.example.bigapps.progressbar;

import com.example.bigapps.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/**
 * @author YC
 * @time 2016-5-22 上午10:12:20
 */
public class MainTestProgressbar extends Activity {

	private static final int UPDATE_MSG = 0x100;
	
	private HorizontalProgressbarWithNum mProgressbar;
	private RoundProgressbarWithNum mProgressbar2;
	
	private Handler mRunHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int progress = (mProgressbar.getProgress() + 1)%101;
			mProgressbar.setProgress(progress);
			mProgressbar2.setProgress(progress);
			if (progress >= 100){
//				mRunHandler.removeMessages(UPDATE_MSG);
			}
			mRunHandler.sendEmptyMessageDelayed(UPDATE_MSG, 100);
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progressbar);
		
		mProgressbar = (HorizontalProgressbarWithNum) findViewById(R.id.progressbar1);
		
		mProgressbar2 = (RoundProgressbarWithNum) findViewById(R.id.progressbar2);
		
		mRunHandler.sendEmptyMessage(UPDATE_MSG);
	}
}
