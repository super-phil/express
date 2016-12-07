package com.magic.express;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
                .url("http://123.56.102.224:17051/express/chart-type")
                .addParams("username", "hyman")
                .addParams("password", "123")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject jsonObject = JSON.parseObject(response);
                        Object data = jsonObject.get("data");
                        List<Chart> charts = JSON.parseArray(data.toString(), Chart.class);
                        HomeListItemAdapter adapter = new HomeListItemAdapter(HomeActivity.this, charts);
                        ListView listView = (ListView) findViewById(R.id.list_view);
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(HomeActivity.this, "异常信息:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
        findViewById(R.id.home_button_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(HomeActivity.this, CaptureActivity.class), 0);
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
