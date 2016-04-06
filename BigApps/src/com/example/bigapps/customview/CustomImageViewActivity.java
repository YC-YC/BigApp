/**
 * 
 */
package com.example.bigapps.customview;

import com.example.bigapps.R;
import com.example.bigapps.R.layout;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

/**
 * @author YC
 * @time 2016-3-29 ÏÂÎç8:21:21
 */
public class CustomImageViewActivity extends Activity {

	private CustomImageView mImg1, mImg2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_customimageview);
		mImg1 = (CustomImageView) findViewById(R.id.customimage1);
		mImg2 = (CustomImageView) findViewById(R.id.customimage2);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head1);
		mImg1.setImageBitmap(bitmap);
		mImg2.setImageBitmap(bitmap);
	}
}
