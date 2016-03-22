/**
 * 
 */
package com.example.bigapps.circlemenu;

import com.example.bigapps.R;
import com.example.bigapps.circlemenu.CircleMenuLayout.OnMenuItemClickListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * @author YC
 * @time 2016-3-21 上午11:08:07
 */
public class CircleActivity extends Activity {

	private CircleMenuLayout mCircleMenuLayout;
	
	private String[] mItemTexts = new String[] { "音乐 ", "电话", "USB",
			"设置", "收音机"/*, "信用卡"*/ };
	private int[] mItemImgs = new int[] { R.drawable.music_selector,
			R.drawable.bt_selector, R.drawable.usb_selector,
			R.drawable.setup_selector, R.drawable.radio_selector/*,
			R.drawable.home_mbank_6_normal*/};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_circlemenu);
	
		mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
		mCircleMenuLayout.setMenuAttr(mItemImgs, mItemTexts, 60, 1000);
		mCircleMenuLayout.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public void itemClick(View view, int pos) {
				Toast.makeText(CircleActivity.this, mItemTexts[pos],
						Toast.LENGTH_SHORT).show();

			}
		});
	}
}
