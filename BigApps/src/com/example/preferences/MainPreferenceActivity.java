package com.example.preferences;

import com.example.bigapps.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

/**
 * 
 * @author Administrator
 * @time 2016-7-6
 * TODO：测试PreferenceActivity
 * 1、配置xml
 */
public class MainPreferenceActivity extends PreferenceActivity implements OnPreferenceChangeListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preference_settings);
		
		Preference individualNamePreference = findPreference("name");
		SharedPreferences sharedPreferences= individualNamePreference.getSharedPreferences();
		individualNamePreference.setSummary(sharedPreferences.getString("name", ""));
		if (sharedPreferences.getBoolean("save_user_info", false))
			 individualNamePreference.setEnabled(true);
		else
			 individualNamePreference.setEnabled(false);
		individualNamePreference.setOnPreferenceChangeListener(this);
		
	}

	
	@Override
	@Deprecated
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		if ("save_user_info".equals(preference.getKey())){
			findPreference("name").setEnabled(!findPreference("name").isEnabled());
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}


	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		preference.setSummary(String.valueOf(newValue));
		return true;
	}
}
