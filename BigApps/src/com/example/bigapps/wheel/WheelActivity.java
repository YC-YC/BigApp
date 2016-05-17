/**
 * 
 */
package com.example.bigapps.wheel;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bigapps.R;
import com.zh.uitls.Utils;

/**
 * @author YC
 * @time 2016-5-17 下午4:03:32
 */
public class WheelActivity extends Activity implements OnWheelChangedListener{
private JSONObject mJsonObj;
	
	private WheelView mProvince, mCity, mArea;
	
	/** ����ʡ */
	private String[] mProvinceDatas;
	/** key - ʡ value - ��s */
	private Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/** key - �� values - ��s */
	private Map<String, String[]> mAreaDatasMap = new HashMap<String, String[]>();
	
	/** ��ǰʡ������ */
	private String mCurrentProviceName;
	/** ��ǰ�е����� */
	private String mCurrentCityName;
	/** ��ǰ�������� */
	private String mCurrentAreaName ="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wheel);
		Utils.getInstance().startTime("initJsonData");
		initJsonData();
		Utils.getInstance().endUseTime("initJsonData");
		Utils.getInstance().startTime("initDatas");
		initDatas();
		Utils.getInstance().endUseTime("initDatas");
		
		mProvince = (WheelView) findViewById(R.id.id_province);
		mCity = (WheelView) findViewById(R.id.id_city);
		mArea = (WheelView) findViewById(R.id.id_area);
		
		mProvince.setViewAdapter(new ArrayWheelAdapter<String>(this, mProvinceDatas));  
        // ���change�¼�  
        mProvince.addChangingListener(this);  
        // ���change�¼�  
        mCity.addChangingListener(this);  
        // ���change�¼�  
        mArea.addChangingListener(this);  
  
        mProvince.setVisibleItems(5);  
        mCity.setVisibleItems(5);  
        mArea.setVisibleItems(5); 
        
        updateCities();  
//        updateAreas();  
	}

	/**
	 * ���ݵ�ǰ���У�������WheelView����Ϣ
	 */
	private void updateAreas()
	{
		int pCurrent = mCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mAreaDatasMap.get(mCurrentCityName);

		if (areas == null)
		{
			areas = new String[] { "" };
		}
		mArea.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
		mArea.setCurrentItem(0);
		if (mAreaDatasMap.get(mCurrentCityName) != null)
		{
			mCurrentAreaName = mAreaDatasMap.get(mCurrentCityName)[0];  
		}
	}

	/**
	 * ���ݵ�ǰ��ʡ��������WheelView����Ϣ
	 */
	private void updateCities()
	{
		int pCurrent = mProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null)
		{
			cities = new String[] { "" };
		}
		mCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
		mCity.setCurrentItem(0);
		updateAreas();
	}
	
	/** ��assert�ļ����ж�ȡʡ������json�ļ���Ȼ��ת��Ϊjson���� */
	private void initJsonData()
	{
		try
		{
			StringBuffer sb = new StringBuffer();
			InputStream is = getAssets().open("city.json");
			int len = -1;
			byte[] buf = new byte[1024];
			while ((len = is.read(buf)) != -1)
			{
				sb.append(new String(buf, 0, len, "gbk"));
			}
			is.close();
			mJsonObj = new JSONObject(sb.toString());
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
	}
	
	/** ��������Json������ɺ��ͷ�Json������ڴ� */
	private void initDatas()
	{
		try
		{
			JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
			mProvinceDatas = new String[jsonArray.length()];
			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject jsonP = jsonArray.getJSONObject(i);// ÿ��ʡ��json����
				String province = jsonP.getString("p");// ʡ����

				mProvinceDatas[i] = province;

				JSONArray jsonCs = null;
				try
				{
					/**
					 * Throws JSONException if the mapping doesn't exist or is
					 * not a JSONArray.
					 */
					jsonCs = jsonP.getJSONArray("c");
				} catch (Exception e1)
				{
					continue;
				}
				String[] mCitiesDatas = new String[jsonCs.length()];
				for (int j = 0; j < jsonCs.length(); j++)
				{
					JSONObject jsonCity = jsonCs.getJSONObject(j);
					String city = jsonCity.getString("n");// ������
					mCitiesDatas[j] = city;
					JSONArray jsonAreas = null;
					try
					{
						/**
						 * Throws JSONException if the mapping doesn't exist or
						 * is not a JSONArray.
						 */
						jsonAreas = jsonCity.getJSONArray("a");
					} catch (Exception e)
					{
						continue;
					}

					String[] mAreasDatas = new String[jsonAreas.length()];// ��ǰ�е�������
					for (int k = 0; k < jsonAreas.length(); k++)
					{
						String area = jsonAreas.getJSONObject(k).getString("s");// ���������
						mAreasDatas[k] = area;
					}
					mAreaDatasMap.put(city, mAreasDatas);
				}

				mCitisDatasMap.put(province, mCitiesDatas);
			}

		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		mJsonObj = null;
	}
	
	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
	 if (wheel == mProvince)  
        {  
            updateCities();  
        } else if (wheel == mCity)  
        {  
            updateAreas();  
        } else if (wheel == mArea)  
        {  
            mCurrentAreaName = mAreaDatasMap.get(mCurrentCityName)[newValue];  
        }  
	}
	
	public void showChoose(View view)
	{
		Toast.makeText(this, mCurrentProviceName + mCurrentCityName + mCurrentAreaName, 1).show();
	}
}
