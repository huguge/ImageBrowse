package com.example.imagebrowse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;

public class ImagePagerActivity extends FragmentActivity{
	private static final String STATE_POSITION = "STATE_POSITION";
	public static final String EXTRA_IMAGE_INDEX = "image_index";//图片索引
	public static final String EXTRA_IMAGE_URLS = "image_urls";//图片地址
	
	private HackyViewPager mPager;//自定义viewpager
	private int pagerPosition;//当前页位置
	private TextView indicator;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_detail_pager);
		
		//获取上级页面MyListAdapter的值
		pagerPosition=getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
		String[] urls=getIntent().getStringArrayExtra(EXTRA_IMAGE_URLS);
		
		mPager=(HackyViewPager) findViewById(R.id.pager);//图片
		ImagePagerAdapter mAdapter=new ImagePagerAdapter(getSupportFragmentManager(), urls);
		mPager.setAdapter(mAdapter);
		
		
		indicator = (TextView) findViewById(R.id.indicator);//页码
		CharSequence text = getString(R.string.viewpager_indicator, 1, mPager
				.getAdapter().getCount());
		indicator.setText(text);
		
		// 更新下标
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int arg0) {
				CharSequence text = getString(R.string.viewpager_indicator,
						arg0 + 1, mPager.getAdapter().getCount());
				indicator.setText(text);
			}

		});
		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
		}

		mPager.setCurrentItem(pagerPosition);
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, mPager.getCurrentItem());
	}
	
	private class ImagePagerAdapter extends FragmentStatePagerAdapter{
		public String[] fileList;


		public ImagePagerAdapter(FragmentManager fm, String[] fileList) {
			super(fm);
			this.fileList = fileList;
		}

		@Override
		public int getCount() {
			return fileList==null?0:fileList.length;
		}
		
		@Override
		public Fragment getItem(int position) {
			String url=fileList[position];
			return ImageDetailFragment.newInstance(url);
		}

	}

}
