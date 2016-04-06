/**
 * 
 */
package com.example.bigapps.horizonlist;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bigapps.R;
import com.example.bigapps.horizonlist.TestActivity.CityItem;

/**
 * @author YC
 * @time 2016-4-1 ÏÂÎç2:46:57
 */
public class HorizontalListItemManager {
	private List<CityItem> cityList;
	private Context mContext;
	private LayoutInflater mInflater;
	private String TAG = getClass().getSimpleName();

	/**
	 * @param cityList
	 * @param mContext
	 */
	public HorizontalListItemManager(Context context, List<CityItem> cityList) {
		super();
		this.cityList = cityList;
		this.mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}

	public int getCount() {
		return cityList.size();
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.list_item, null);
			holder.mImg = (ImageView) convertView.findViewById(R.id.ItemImage);
			holder.mTvCity = (TextView) convertView.findViewById(R.id.tvCity);
			holder.mTvCode = (TextView) convertView.findViewById(R.id.tvCode);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		CityItem city = cityList.get(position);

		holder.mTvCity.setText(city.getCityName());
		holder.mTvCode.setText(city.getCityCode());
		holder.mImg.setImageResource(city.getImgID());
		
//		convertView.measure(0, 0/*MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
//				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)*/);
//		Log.i(TAG , "getView width = " + convertView.getMeasuredWidth() + ", height = " + convertView.getMeasuredHeight());
		return convertView;
	}

	private class ViewHolder {
		ImageView mImg;
		TextView mTvCity;
		TextView mTvCode;
	}

}
