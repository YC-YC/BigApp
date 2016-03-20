package com.example.bigapps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bigapps.testviewpager.MainViewPager;
import com.example.bigapps.testviewpager.MainViewPager2;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}


	public void doClick(View view)
	{
		switch (view.getId()) {
		case R.id.button1:
			startActivity(new Intent(this, MainViewPager.class));
			break;
		case R.id.button2:
			startActivity(new Intent(this, MainViewPager2.class));
			break;
		default:
			break;
		}
	}

}
