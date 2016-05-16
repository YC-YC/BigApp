/**
 * 
 */
package com.example.bigapps.horizonlist;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.bigapps.R;
import com.example.bigapps.horizonlist.MyHorizontalScollView.CurrentImageChangeListener;
import com.example.bigapps.horizonlist.MyHorizontalScollView.OnItemClickListener;

/**
 * @author YC
 * @time 2016-3-29 上午9:22:35
 */
public class HorizonListActivity3 extends Activity {

	private static final String TAG = "HorizonListActivity";
	
	private List<CityItem> cityList;
	private MyHorizontalScollView mHorizontalScollView;
	private HorizontalScrollViewAdapter mAdapter;
	private LayoutInflater mInflater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInflater = LayoutInflater.from(this);
		setContentView(R.layout.main_horizonlist3);
		
		setData();
		initViews();
	}
	
	private void initViews() {
		mHorizontalScollView = (MyHorizontalScollView) findViewById(R.id.id_horizontalscollview);
		mAdapter = new HorizontalScrollViewAdapter(this, cityList);
		mHorizontalScollView.setAdapter(mAdapter);
		mHorizontalScollView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onClick(View view, int pos) {
				Toast.makeText(getApplicationContext(), "点击了第" + pos +"项", Toast.LENGTH_SHORT).show();
			}
		});
		
		mHorizontalScollView.setCurrentImageChangeListener(new CurrentImageChangeListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onCurrentImgChanged(int position, View viewIndicator) {
				Log.i(TAG, "第一个显示项是" + position);
//				viewIndicator.setScaleX(2.5f);
//				viewIndicator.setScaleY(2.5f);
				viewIndicator.setBackgroundResource(R.drawable.ic_launcher);
			}
		});
	}

	
	private void setData() {
		cityList = new ArrayList<CityItem>();
		CityItem item = new CityItem("深圳", "0755", R.drawable.china);
		cityList.add(item);
		item = new CityItem("上海", "021", R.drawable.china);
		cityList.add(item);
		item = new CityItem("广州", "020", R.drawable.china);
		cityList.add(item);
		item = new CityItem("北京", "010", R.drawable.china);
		cityList.add(item);
		item = new CityItem("武汉", "027", R.drawable.china);
		cityList.add(item);
		item = new CityItem("孝感", "0712", R.drawable.china);
		cityList.add(item);
		cityList.addAll(cityList);
	}
	
	public class CityItem {
		private String cityName;
		private String cityCode;
		private int imgID;

		
		public CityItem(String cityName, String cityCode, int imgID) {
			super();
			this.cityName = cityName;
			this.cityCode = cityCode;
			this.imgID = imgID;
		}

		public String getCityName() {
			return cityName;
		}


		public String getCityCode() {
			return cityCode;
		}

		public int getImgID() {
			return imgID;
		}
	}
}
