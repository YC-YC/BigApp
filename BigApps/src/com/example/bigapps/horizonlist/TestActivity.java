/**
 * 
 */
package com.example.bigapps.horizonlist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bigapps.R;
import com.example.bigapps.horizonlist.HorizontalList.CurSelItemChangeCallback;
import com.example.bigapps.horizonlist.HorizontalList.ItemOnClickListener;
import com.example.bigapps.horizonlist.HorizontalList.ItemPlayPauseOnClickListener;

/**
 * @author YC
 * @time 2016-3-30 下午2:08:09
 */
public class TestActivity extends Activity {

	private HorizontalList mList;
	private List<CityItem> cityList;
	private HorizontalListAdapter mAdapter;
	private HorizontalListItemManager manager;
	protected String TAG = getClass().getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_test);
		mList = (HorizontalList) findViewById(R.id.id_gallery);
		getData();
		mAdapter = new HorizontalListAdapter(this, cityList);
		manager = new HorizontalListItemManager(this, cityList);
		mList.setAdapter(mAdapter);
//		mList.setPlayPauseState(new int[]{2}, new boolean[]{true});
		mList.setPlayPauseState(null, null);
		mList.setItemOnClickListener(new ItemOnClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position) {
				Toast.makeText(TestActivity.this, "点击了" + cityList.get(position).getCityName(), Toast.LENGTH_SHORT).show();
			}
		});
		mList.setItemPlayPauseOnClickListener(new ItemPlayPauseOnClickListener() {
			
			@Override
			public void onItemPlayPause(View view, int position) {
				
				Toast.makeText(TestActivity.this, "点击了播放暂停" + position, Toast.LENGTH_SHORT).show();
				
				ImageView playPause = (ImageView) view.findViewById(R.id.ItemPlayPause);
				/*if (playPause.isSelected())
				{

					Log.i(TAG , "设置为非选择状态");
					playPause.setSelected(false);
				}
				else
				{
					Log.i(TAG , "设置为选择状态");
					playPause.setSelected(true);
				}*/
			}
		});
		
		mList.setCurSelItemChangeCallback(new CurSelItemChangeCallback() {
			
			@Override
			public void onItemChange(int oldItem, int curItem) {
				Log.i(TAG, "前Item = " + oldItem + ", 当前Item = " + curItem);
//				mList.setPlayPauseState(new int[]{curItem}, new boolean[]{false});
			}
		});
	}
	
	private void getData() {
		cityList = new ArrayList<CityItem>();
		CityItem item = new CityItem("深圳", "0755", R.drawable.head3);
		cityList.add(item);
		item = new CityItem("上海", "021", R.drawable.head);
		cityList.add(item);
		item = new CityItem("广州", "020", R.drawable.head1);
		cityList.add(item);
		item = new CityItem("北京", "010", R.drawable.head2);
		cityList.add(item);
		item = new CityItem("武汉", "027", R.drawable.head3);
		cityList.add(item);
		item = new CityItem("孝感", "0712", R.drawable.head4);
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
