package com.example.bigapps.testviewpager;

import java.util.ArrayList;
import java.util.List;

import com.example.bigapps.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 *@Author Administrator
 *@Time 2016-3-19 下午10:31:48
 */
public class MainViewPager2 extends Activity {

	private CustomViewPager mViewPager;
	
	private int[] mImgIds = new int[]{
			R.drawable.guide_image1,
			R.drawable.guide_image2,
			R.drawable.guide_image3
	};
	
	private List<ImageView> mViews = new ArrayList<ImageView>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_viewpager2);
		initViews();
	}
	private void initViews() {
		mViewPager = (CustomViewPager) findViewById(R.id.viewpager_main);	
		//设置切换动画
		//上下切换
//		mViewPager.setPageTransformer(true, new DepthPageTransformer());
		//缩小切换
//		mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

		//自定义风车翻转
//		mViewPager.setPageTransformer(true, new RotatePageTransformer());

		mViewPager.setAdapter(new PagerAdapter() {
			
			//初始化显示的View
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				ImageView imageView = new ImageView(MainViewPager2.this);
				imageView.setImageResource(mImgIds[position]);
				imageView.setScaleType(ScaleType.CENTER_CROP);//设置图片形状子防止变形
				container.addView(imageView);
				mViews.add(imageView);
				mViewPager.setViewForPosition(imageView, position);
				return imageView;
			}
			
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(mViews.get(position));
				mViewPager.removeViewFromPosition(position);
			}


			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				//一般固定写法
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				return mImgIds.length;
			}
		});
	}
}
