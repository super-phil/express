package com.magic.express;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ZhaoXiuFei on 2016/12/7.
 */

public class HomeListItemAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Chart> mDatas;

    public HomeListItemAdapter(Context context, List<Chart> datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //这个方法才是重点，我们要为它编写一个ViewHolder
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.home_list_item, viewGroup, false);//加载布局
            holder = new ViewHolder();
            holder.type = (TextView) view.findViewById(R.id.home_list_item_type);
            holder.sum = (TextView) view.findViewById(R.id.home_list_item_sum);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Chart chart = mDatas.get(i);
        holder.type.setText(chart.getType());
        holder.sum.setText(String.format("%s", chart.getSum()));
        return view;
    }

    //这个ViewHolder只能服务于当前这个特定的adapter，因为ViewHolder里会指定item的控件，不同的ListView，item可能不同，所以ViewHolder写成一个私有的类
    private class ViewHolder {
        TextView type;
        TextView sum;
    }
}
