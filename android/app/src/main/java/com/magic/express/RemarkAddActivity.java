package com.magic.express;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class RemarkAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remark_add);
        findViewById(R.id.remark_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView text = (TextView) findViewById(R.id.remark_text);
                OkHttpUtils
                        .post()
                        .url(App.DOMAIN + "/remark/add")
                        .addParams("text", text.getText().toString())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onResponse(String response, int id) {
                                Toast.makeText(RemarkAddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RemarkAddActivity.this, RemarkListActivity.class));
                            }

                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(RemarkAddActivity.this, "异常信息:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        });
            }
        });
    }
}
