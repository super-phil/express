package com.magic.express;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;

public class ScanActivity extends AppCompatActivity {
    EditText numberEditText;
    String result;
    EditText priceEditText;
    EditText descEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        Intent intent = getIntent();
        String code = intent.getStringExtra("code");
        numberEditText = (EditText) findViewById(R.id.scan_edit_number);
        numberEditText.setText(code);
        ArrayList<String> objects = Lists.newArrayList();
        objects.add("现金");
        objects.add("微信");
        objects.add("欠款");
        objects.add("月结");
        objects.add("代收");
        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, objects);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.scan_spinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                result = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        descEditText = (EditText) findViewById(R.id.scan_edit_desc);
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
                        List<String> objects = Lists.newArrayList();
                        for (Remark remark : remarks) {
                            objects.add(remark.getText());
                        }
                        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(ScanActivity.this, android.R.layout.simple_spinner_item, objects);
                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Spinner spinner = (Spinner) findViewById(R.id.scan_spinner_remark);
                        spinner.setAdapter(spinnerAdapter);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                descEditText.setTag(adapterView.getSelectedItem().toString());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(ScanActivity.this, "异常信息:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });

        priceEditText = (EditText) findViewById(R.id.scan_edit_price);
        priceEditText.setFocusable(true);
        priceEditText.setFocusableInTouchMode(true);
        priceEditText.requestFocus(); //请求获取焦点
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) priceEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(priceEditText, 0);
                           }
                       },
                500);//延迟弹出

        findViewById(R.id.scan_button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = numberEditText.getText().toString();
                String type = Type.getCode(result);
                String price = priceEditText.getText().toString();
                String desc = descEditText.getText().toString();
                OkHttpUtils
                        .post()
                        .url("http://123.56.102.224:17051/express/save")
                        .addParams("number", number)
                        .addParams("type", type)
                        .addParams("price", price)
                        .addParams("desc", desc)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(ScanActivity.this, "异常信息:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Intent intent = new Intent(ScanActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        });
            }
        });

    }


}
