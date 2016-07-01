/**
 * 
 */
package com.example.bigapps.recyclerview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bigapps.R;

/**
 * @author YC
 * @time 2016-5-29 上午8:55:39
 * RecyclerView测试
 */
public class RecyclerViewActivity extends Activity {

	private RecyclerView mRecyclerView;
	
	private List<String> mData = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_recycler);
		
		initData();
//		mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
//		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//		mRecyclerView.setAdapter(new MyAdapter());
	}

	private void initData() {
		for (int i = 'A'; i < 'z'; i++){
			mData.add("" + (char)i);
		}
	}
	
	private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
		
		@Override
		public MyViewHolder onCreateViewHolder(ViewGroup viewgroup, int arg1) {
			MyViewHolder holder = new MyViewHolder(
					LayoutInflater.from(RecyclerViewActivity.this)
					.inflate(R.layout.recyclerview_item, 
							viewgroup, false));
			return holder;
		}

		@Override
		public int getItemCount() {
			return mData.size();
		}

		@Override
		public void onBindViewHolder(MyViewHolder holder, int location) {
			holder.tv.setText(mData.get(location));
		}

		
		class MyViewHolder extends ViewHolder{
			TextView tv;

			public MyViewHolder(View view) {
				super(view);
				this.tv = (TextView) view.findViewById(R.id.id_num);
			}
			
		}
	}
}
