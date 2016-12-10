package com.magic.express;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by ZhaoXiuFei on 2016/12/10.
 */

public class RemarkListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Remark> mData;
    private Context context;

    public RemarkListAdapter(Context context, List<Remark> data) {
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
            view = mInflater.inflate(R.layout.remark_list, viewGroup, false);//加载布局
            holder = new ViewHolder();
            holder.text = (TextView) view.findViewById(R.id.remark_list_text);
            holder.btn = (Button) view.findViewById(R.id.remark_list_del);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Remark remark = mData.get(i);
        holder.text.setText(remark.getText());
        holder.btn.setTag(i);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Remark item = (Remark) getItem(i);
                new AlertDialog.Builder(context)
                        .setTitle("是否删除?")
                        .setMessage("备注:" + item.getText())
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                OkHttpUtils
                                        .post()
                                        .url(App.DOMAIN + "/remark/del")
                                        .addParams("id", item.getId() + "")
                                        .build()
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onResponse(String response, int id) {
                                                mData.remove(i);
                                                notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onError(Call call, Exception e, int id) {
                                                Toast.makeText(context, "异常信息:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                        });
                            }
                        })
                        .show();
            }
        });
        return view;
    }

    //这个ViewHolder只能服务于当前这个特定的adapter，因为ViewHolder里会指定item的控件，不同的ListView，item可能不同，所以ViewHolder写成一个私有的类
    private class ViewHolder {
        TextView text;
        Button btn;
    }
}
