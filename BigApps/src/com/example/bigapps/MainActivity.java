package com.example.bigapps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bigapps.circlemenu.CircleActivity;
import com.example.bigapps.customview.CustomImageViewActivity;
import com.example.bigapps.horizonlist.HorizonListActivity;
import com.example.bigapps.horizonlist.HorizonListActivity2;
import com.example.bigapps.horizonlist.HorizonListActivity3;
import com.example.bigapps.slidedel.SlideDelActivity;
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
		case R.id.button3:
			startActivity(new Intent(this, CircleActivity.class));
			break;
		case R.id.button4:
			startActivity(new Intent(this, SlideDelActivity.class));
			break;
		case R.id.button5:
			startActivity(new Intent(this, HorizonListActivity.class));
			break;	
		case R.id.button6:
			startActivity(new Intent(this, HorizonListActivity2.class));
			break;	
		case R.id.button7:
			startActivity(new Intent(this, HorizonListActivity3.class));
			break;
		case R.id.button8:
			startActivity(new Intent(this, CustomImageViewActivity.class));
			break;
		default:
			break;
		}
	}

}
