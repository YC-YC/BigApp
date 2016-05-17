/**
 * 
 */
package com.example.bigapps.customview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bigapps.R;

/**
 * @author YC
 * @time 2016-5-17 下午4:00:02
 */
public class SwipeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swipe);
		
		(findViewById(R.id.delete_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SwipeActivity.this, "click", Toast.LENGTH_SHORT).show();
            }
        });
	}
}
