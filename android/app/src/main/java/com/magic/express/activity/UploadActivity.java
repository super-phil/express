package com.magic.express.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.common.collect.Lists;
import com.magic.express.App;
import com.magic.express.R;
import com.magic.express.Utils;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.Call;

public class UploadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        final SharedPreferences preferences = getSharedPreferences(App.EXPRESS_IMAGE, MODE_PRIVATE);
        @SuppressWarnings("unchecked")
        final Map<String, String> all = (Map<String, String>) preferences.getAll();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Lists.newArrayList(all.values()));
        ListView listView = (ListView) findViewById(R.id.upload_list);
        listView.setAdapter(adapter);
        findViewById(R.id.upload_button_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UploadActivity.this, "开始上传啦,去喝杯水吧!", Toast.LENGTH_LONG).show();
                for (final Map.Entry<String, String> entry : all.entrySet()) {
                    String key = entry.getKey() + ".jpg";
                    Utils.uploadManager.put(entry.getValue(), key, Utils.getToken(), new UpCompletionHandler() {
                        @Override
                        public void complete(final String key, ResponseInfo info, JSONObject res) {
                            //res包含hash、key等信息，具体字段取决于上传策略的设置
                            Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                            if (info.isOK()) {
                                Log.i("qiniu", "Upload Success");
                                OkHttpUtils
                                        .post()
                                        .url(App.DOMAIN + "/express/upload/url")
                                        .addParams("number", entry.getKey())
                                        .addParams("url", Utils.QINIU_DOMAIN + key)
                                        .build()
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onResponse(String response, int id) {
                                                Toast.makeText(UploadActivity.this, key + "上传成功!", Toast.LENGTH_SHORT).show();
                                                preferences.edit().remove(entry.getKey()).apply();
                                                //刷新listview
                                                adapter.remove(entry.getValue());
                                                adapter.notifyDataSetChanged();
                                            }
                                            @Override
                                            public void onError(Call call, Exception e, int id) {
                                                Toast.makeText(UploadActivity.this, "上传失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Log.i("qiniu", "Upload Fail");
                                //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                            }
                        }
                    }, null);

                }
            }
        });
    }
}
