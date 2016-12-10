package com.magic.express;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

public class RemarkListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remark_list);
        OkHttpUtils
                .get()
                .url("http://123.56.102.224:17051/express/remark-list")
                .addParams("username", "hyman")
                .addParams("password", "123")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject jsonObject = JSON.parseObject(response);
                        Object data = jsonObject.get("data");
                        List<Remark> remarks = JSON.parseArray(data.toString(), Remark.class);
                        RemarkListAdapter adapter = new RemarkListAdapter(RemarkListActivity.this, remarks);
                        ListView listView = (ListView) findViewById(R.id.remark_list_view);
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(RemarkListActivity.this, "异常信息:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
        List<Remark> remarks = Lists.newArrayList();
        for (int i = 0; i < 15; i++) {
            Remark remark = new Remark();
            remark.setId(i);
            remark.setText("备注_" + i);
            remarks.add(remark);
        }
        RemarkListAdapter adapter = new RemarkListAdapter(RemarkListActivity.this, remarks);
        ListView listView = (ListView) findViewById(R.id.remark_list_view);
        listView.setAdapter(adapter);
    }
}
