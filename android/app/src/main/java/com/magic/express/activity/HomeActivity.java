package com.magic.express.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.magic.express.App;
import com.magic.express.adapter.HomeListAdapter;
import com.magic.express.R;
import com.magic.express.model.Chart;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        OkHttpUtils
                .get()
                .url(App.DOMAIN + "/express/chart-type")
                .addParams("username", "hyman")
                .addParams("password", "123")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject jsonObject = JSON.parseObject(response);
                        Object data = jsonObject.get("data");
                        List<Chart> charts = JSON.parseArray(data.toString(), Chart.class);
                        HomeListAdapter adapter = new HomeListAdapter(HomeActivity.this, charts);
                        ListView listView = (ListView) findViewById(R.id.home_list_view);
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(HomeActivity.this, "异常信息:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
        //扫码
        findViewById(R.id.home_button_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(HomeActivity.this, CaptureActivity.class), 0);
            }
        });
        //列表
        findViewById(R.id.home_button_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ListActivity.class));
            }
        });
        //常用语
        findViewById(R.id.home_button_remark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, RemarkListActivity.class));
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String code = bundle.getString("result");
            Intent intent = new Intent(HomeActivity.this, ScanActivity.class);
            intent.putExtra("code", code);
            startActivity(intent);
        } else {
            Toast.makeText(this, "识别失败,请重试或手动输入", Toast.LENGTH_SHORT).show();
        }
    }
}
