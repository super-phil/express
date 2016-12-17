package com.magic.express.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.magic.express.R;
import com.magic.express.Utils;
import com.magic.express.model.Income;

import java.util.List;

/**
 * Created by ZhaoXiuFei on 2016/12/10.
 */

public class IncomeListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Income> mData;
    private Context context;

    public IncomeListAdapter(Context context, List<Income> data) {
        mInflater = LayoutInflater.from(context);
        mData = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //这个方法才是重点，我们要为它编写一个ViewHolder
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.income_list, viewGroup, false);//加载布局
            holder = new ViewHolder();
            holder.date = (TextView) view.findViewById(R.id.income_list_date);
            holder.x = (TextView) view.findViewById(R.id.income_list_x);
            holder.w = (TextView) view.findViewById(R.id.income_list_w);
            holder.q = (TextView) view.findViewById(R.id.income_list_q);
            holder.d = (TextView) view.findViewById(R.id.income_list_d);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Income income = mData.get(i);
        holder.date.setText(Utils.format(income.getCreate_time()));
        holder.x.setText(income.getX());
        holder.w.setText(income.getW());
        holder.q.setText(income.getQ());
        holder.d.setText(income.getD());
        return view;
    }

    //这个ViewHolder只能服务于当前这个特定的adapter，因为ViewHolder里会指定item的控件，不同的ListView，item可能不同，所以ViewHolder写成一个私有的类
    private class ViewHolder {
        TextView date;
        TextView q;
        TextView x;
        TextView w;
        TextView d;
    }
}
