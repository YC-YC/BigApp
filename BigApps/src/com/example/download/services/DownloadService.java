package com.example.download.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.download.entities.FileInfo;

/**
 *@Author Administrator
 *@Time 2016-5-4 上午12:01:41
 */
public class DownloadService extends Service {

	public static final String ACTION_START = "ACTION_START";
	public static final String ACTION_STOP = "ACTION_STOP";
	private static final String TAG = "DownloadService";
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if (ACTION_START.equals(intent.getAction())){
			FileInfo info = (FileInfo) intent.getSerializableExtra("fileInfo");
			Log.i(TAG, "onStartCommand Start getData = " + info.toString());
		} else if (ACTION_STOP.equals(intent.getAction())){
			FileInfo info = (FileInfo) intent.getSerializableExtra("fileInfo");
			Log.i(TAG, "onStartCommand Stop getData = " + info.toString());
		}
		return super.onStartCommand(intent, flags, startId);
	}

}
