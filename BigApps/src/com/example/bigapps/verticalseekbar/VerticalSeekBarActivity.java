/**
 * 
 */
package com.example.bigapps.verticalseekbar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.bigapps.R;

/**
 * @author YC
 * @time 2016-5-26 下午4:15:24
 */
public class VerticalSeekBarActivity extends Activity {

	private VerticalSeekBar mVerticalSeekBar;
	private ReVerticalSeekBar mReVerticalSeekBar;
	private TextView mSeekBarProgress;
	private TextView mSeekBarProgress2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verticalseekbar);
		mSeekBarProgress = (TextView) findViewById(R.id.tvseekbarprogress);
		mSeekBarProgress2 = (TextView) findViewById(R.id.retvseekbarprogress);
		mVerticalSeekBar = (VerticalSeekBar) findViewById(R.id.verticalseekbar);
		mReVerticalSeekBar = (ReVerticalSeekBar) findViewById(R.id.reverticalseekbar);
		mSeekBarProgress.setText("" + mVerticalSeekBar.getProgress());
		mSeekBarProgress2.setText("" + mVerticalSeekBar.getProgress());
		
		mVerticalSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				mSeekBarProgress.setText("" + progress);
				if (fromUser){
				}
			}
		});
		
		mReVerticalSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				mSeekBarProgress2.setText("" + progress);
				if (fromUser){
				}
			}
		});
		
	}
}
