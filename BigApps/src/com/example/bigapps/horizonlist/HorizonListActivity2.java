/**
 * 
 */
package com.example.bigapps.horizonlist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bigapps.R;

/**
 * @author YC
 * @time 2016-3-29 ����9:22:35
 */
public class HorizonListActivity2 extends Activity {

	private List<CityItem> cityList;
	private LinearLayout mGallery;
	private LayoutInflater mInflater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInflater = LayoutInflater.from(this);
		setContentView(R.layout.main_horizonlist2);
		
		setData();
		initViews();
	}
	

	private void initViews() {
		mGallery = (LinearLayout) findViewById(R.id.id_gallery);
		
		for (int i = 0; i < cityList.size(); i++)
		{
			View view = mInflater.inflate(R.layout.list_item, null);
			
			ImageView img = (ImageView) view.findViewById(R.id.ItemImage); 
			TextView tvCity = (TextView) view.findViewById(R.id.tvCity);
			TextView tvCode = (TextView) view.findViewById(R.id.tvCode);
			CityItem city = cityList.get(i);

			tvCity.setText(city.getCityName());
			tvCode.setText(city.getCityCode());
			img.setImageResource(city.getImgID());
			
			mGallery.addView(view);
			
		}
	}

	
	private void setData() {
		cityList = new ArrayList<CityItem>();
		CityItem item = new CityItem("����", "0755", R.drawable.china);
		cityList.add(item);
		item = new CityItem("�Ϻ�", "021", R.drawable.china);
		cityList.add(item);
		item = new CityItem("����", "020", R.drawable.china);
		cityList.add(item);
		item = new CityItem("����", "010", R.drawable.china);
		cityList.add(item);
		item = new CityItem("�人", "027", R.drawable.china);
		cityList.add(item);
		item = new CityItem("Т��", "0712", R.drawable.china);
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
