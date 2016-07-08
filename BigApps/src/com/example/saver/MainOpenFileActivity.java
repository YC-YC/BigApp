/**
 * 
 */
package com.example.saver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bigapps.R;

/**
 * @author YC
 * @time 2016-7-8 上午9:21:20 
 * TODO:测试文件存储,文件以UTF-8格式保存
 */
public class MainOpenFileActivity extends Activity {

	private EditText mInputContent;
	private TextView mReadContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file);
		mInputContent = (EditText) findViewById(R.id.inputcontent);
		mReadContent = (TextView) findViewById(R.id.readcontent);
		
		
	}

	public void writeFile(View view) {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			File sdCardDir = Environment.getExternalStorageDirectory();
			File file = new File(sdCardDir, "test.txt");
			try {
				FileOutputStream outputStream =this.openFileOutput("test.txt", Context.MODE_WORLD_READABLE);
//				FileOutputStream outputStream = new FileOutputStream(file);
				outputStream.write(mInputContent.getText().toString().getBytes());
				outputStream.close();
			} catch (Exception e) {
			}
		}
	}

	public void readFile(View view) {

		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			File sdCardDir = Environment.getExternalStorageDirectory();
			File file = new File(sdCardDir, "test.txt");
			try {
				
//				FileInputStream inputStream = new FileInputStream(file);
				FileInputStream inputStream =this.openFileInput("test.txt"); //获得输入流
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int length = -1;
				while((length = inputStream.read(buffer)) != -1){
					byteArrayOutputStream.write(buffer);
				}
				inputStream.close();
				byteArrayOutputStream.close();
				mReadContent.setText("读到的内容为：" + byteArrayOutputStream.toString());
			} catch (Exception e) {
			}
		}
	}
	
	
	
}
