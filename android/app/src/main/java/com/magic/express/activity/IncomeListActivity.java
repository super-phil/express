package com.magic.express.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.magic.express.App;
import com.magic.express.R;
import com.magic.express.adapter.IncomeListAdapter;
import com.magic.express.model.Income;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

public class IncomeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        OkHttpUtils
                .get()
                .url(App.DOMAIN + "/income/list")
                .addParams("username", "hyman")
                .addParams("password", "123")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(String response, int id) {
                        List<Income> charts = JSON.parseArray(response, Income.class);
                        IncomeListAdapter adapter = new IncomeListAdapter(IncomeListActivity.this, charts);
                        ListView listView = (ListView) findViewById(R.id.income_list_view);
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(IncomeListActivity.this, "异常信息:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
    }
}
