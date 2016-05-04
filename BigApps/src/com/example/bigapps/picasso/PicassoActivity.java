/**
 * 
 */
package com.example.bigapps.picasso;

import com.example.bigapps.R;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * @author YC
 * @time 2016-5-4 œ¬ŒÁ6:17:27
 * ≤‚ ‘picasso
 */
public class PicassoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picasso);
		ImageView img = (ImageView) findViewById(R.id.imgpicasso);
		Picasso.with(this).load(R.drawable.hui).placeholder(R.drawable.ic_launcher)
		.error(R.drawable.head).into(img);
	}
}
