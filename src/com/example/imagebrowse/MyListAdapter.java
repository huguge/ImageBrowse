package com.example.imagebrowse;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyListAdapter extends BaseAdapter{
	private ArrayList<MyBean> mList;// 反馈内容实体
	private LayoutInflater mInflater;
	private Context mContext;
	
	public MyListAdapter(Context context, ArrayList<MyBean> list) {
		mInflater=LayoutInflater.from(context);
		this.mList = list;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return mList == null ? 0 : mList.size();
	}

	@Override
	public MyBean getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.list_item, null);
			holder.avator = (ImageView) convertView.findViewById(R.id.avator);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.gridview = (NoScrollGridView) convertView.findViewById(R.id.gridView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final MyBean bean=getItem(position);
		ImageLoader.getInstance().displayImage(bean.avator, holder.avator);//头像
		holder.name.setText(bean.name);
		holder.content.setText(bean.content);
		
		if(bean.urls !=null && bean.urls.length>0){
			holder.gridview.setVisibility(View.VISIBLE);
			Log.e("bean.urls----", "----"+bean.urls);
			holder.gridview.setAdapter(new MyGridAdapter(bean.urls, mContext));
			holder.gridview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Log.e("position----", "---"+position);
					imageBrower(position,bean.urls);
					
				}
			});
			
		}else{
			holder.gridview.setVisibility(View.GONE);
		}
		return convertView;
	}
	
	private void imageBrower(int position, String[] urls) {//浏览图片，跳转到ImagePagerActivity
		Intent intent = new Intent(mContext, ImagePagerActivity.class);
		// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		mContext.startActivity(intent);
	}
	
	private static class ViewHolder {
		public TextView name;// 姓名
		public ImageView avator;// 头像
		TextView content;// 内容
		NoScrollGridView gridview;// 图片
	}

}
