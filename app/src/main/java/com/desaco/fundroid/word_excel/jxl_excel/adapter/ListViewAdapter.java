package com.desaco.fundroid.word_excel.jxl_excel.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.desaco.fundroid.R;
import com.desaco.fundroid.word_excel.jxl_excel.bean.LocationPoint;

public class ListViewAdapter extends BaseAdapter {
	
	private List<LocationPoint> list;
	private LayoutInflater inflater;
	
	static class ViewHolder{
		TextView tvId;
		TextView tvDate;
		TextView tvLon;
		TextView tvLat;
	}
	
	public ListViewAdapter(Context context,List<LocationPoint> list) {
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public LocationPoint getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if(convertView==null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.word_excel_list_item, parent,false);
			viewHolder.tvId = (TextView) convertView.findViewById(R.id.tv_id);
			viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
			viewHolder.tvLon = (TextView) convertView.findViewById(R.id.tv_lon);
			viewHolder.tvLat = (TextView) convertView.findViewById(R.id.tv_lat);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
			LocationPoint point = list.get(position);
			viewHolder.tvId.setText(point.getId()+"");
			viewHolder.tvDate.setText(point.getLocateTime());
			viewHolder.tvLon.setText(point.getLongitude()+"");
			viewHolder.tvLat.setText(point.getLatitude()+"");
		return convertView;
	}

}
