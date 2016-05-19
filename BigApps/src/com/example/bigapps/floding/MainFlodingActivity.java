package com.example.bigapps.floding;

import android.app.Activity;
import android.os.Bundle;

/**
 *@Author Administrator
 *@Time 2016-5-16 下午10:36:45
 */
public class MainFlodingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(new PolyToPolyView(this));
//		setContentView(new PolyToPolyViewWithShadow(this));
		setContentView(new PolyToPolyViewWithFloding(this));
	}
}
