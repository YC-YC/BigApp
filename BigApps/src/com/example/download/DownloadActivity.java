package com.example.download;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bigapps.R;
import com.example.download.entities.FileInfo;
import com.example.download.services.DownloadService;

/**
 *@Author Administrator
 *@Time 2016-5-3 下午11:25:47
 */
public class DownloadActivity extends Activity {

	private TextView mTvTitle;
	private Button mBtnStop, mBtnStart;
	private ProgressBar mPBDownLaod;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		
		initViews();
		
		initData();
	}

	private void initData() {
		final FileInfo fileInfo = new FileInfo(0, 
				"https://www.baidu.com/link?url=VK5bzrOXtEUXqVsNP1fOFmuaI-MbthtPbP2lnBDv1JlEZlXCwkJvDpEgFBRQDH5d7Ojlek6kkgvjHn5nYydL5AZEySxl_MI95KMhiEqTBkS&wd=&eqid=e666e95e02a4da45000000035728cd8e", 
				"kugou", 0, 0);
		mBtnStop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DownloadActivity.this, DownloadService.class);
				intent.setAction(DownloadService.ACTION_STOP);
				intent.putExtra("fileInfo", fileInfo);
				startService(intent);
			}
		});
		mBtnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DownloadActivity.this, DownloadService.class);
				intent.setAction(DownloadService.ACTION_START);
				intent.putExtra("fileInfo", fileInfo);
				startService(intent);
			}
		});
	}

	private void initViews() {
		mTvTitle = (TextView) findViewById(R.id.tvtitle);
		mBtnStop = (Button) findViewById(R.id.btn_stop);
		mBtnStart = (Button) findViewById(R.id.btn_start);
		mPBDownLaod = (ProgressBar) findViewById(R.id.pbdownloadprogress);
	}
}
