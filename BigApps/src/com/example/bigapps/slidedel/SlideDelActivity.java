package com.example.bigapps.slidedel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.bigapps.R;
import com.example.bigapps.slidedel.DelListView.OnDelListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 *@Author Administrator
 *@Time 2016-3-22 下午10:51:09
 */
public class SlideDelActivity extends Activity{

	private DelListView mListView;
	
	private ArrayAdapter<String> mAdapter;  
    private List<String> mDatas; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_dellist);
		
		mListView = (DelListView) findViewById(R.id.lv_del);
		
		mDatas = new ArrayList<String>(Arrays.asList("HelloWorld", "Welcome", "Java", "Android", "Servlet", "Struts",  
                "Hibernate", "Spring", "HTML5", "Javascript", "Lucene")); 
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
		
		mListView.setAdapter(mAdapter);
		
		mListView.setOnDelListener(new OnDelListener() {

			@Override
			public void onDel(int position) {
				Toast.makeText(SlideDelActivity.this, "del " + position + ":" + mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
				mAdapter.remove(mAdapter.getItem(position));
			}
			
		});
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				Toast.makeText(SlideDelActivity.this, "click " + position + ":" + mAdapter.getItem(position), Toast.LENGTH_SHORT).show();

			}
		});
		
	}
}
