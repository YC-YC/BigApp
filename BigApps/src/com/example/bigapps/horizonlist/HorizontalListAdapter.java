/**
 * 
 */
package com.example.bigapps.horizonlist;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bigapps.R;
import com.example.bigapps.horizonlist.TestActivity.CityItem;

/**
 * @author YC
 * @time 2016-3-29 上午11:29:09
 */
public class HorizontalListAdapter extends BaseAdapter {

	private List<CityItem> cityList;
	private Context mContext;
	private LayoutInflater mInflater;
	
	public HorizontalListAdapter(Context context, List<CityItem> list) {
		this.cityList = list;
		this.mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		return cityList.size();
	}

	@Override
	public Object getItem(int position) {
		return cityList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		ViewHolder holder = null;
		if (convertView == null)
		{
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.list_item, null);
			holder.mImg = (ImageView) convertView.findViewById(R.id.ItemImage);
			holder.mState = (ImageView) convertView.findViewById(R.id.ItemPlayPause);
			holder.mTvCity = (TextView) convertView.findViewById(R.id.tvCity);
			holder.mTvCode = (TextView) convertView.findViewById(R.id.tvCode);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		CityItem city = cityList.get(position);

		holder.mTvCity.setText(city.getCityName());
		holder.mTvCode.setText(city.getCityCode());
		holder.mImg.setImageResource(city.getImgID());
		return convertView;
	}
	
	 private class ViewHolder  
	    {  
	        ImageView mImg;  
	        ImageView mState;
	        TextView mTvCity;  
	        TextView mTvCode;
	    }  
}
