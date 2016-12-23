package com.magic.express.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.magic.express.App;
import com.magic.express.R;
import com.magic.express.Utils;
import com.magic.express.model.Remark;
import com.magic.express.model.Type;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;

public class ScanActivity extends AppCompatActivity {
    private EditText numberEditText;
    private String result;
    private EditText priceEditText;
    private EditText descEditText;
    private SharedPreferences preferences;
    //定义一个保存图片的File变量
    private File currentImageFile;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        Intent intent = getIntent();
        code = intent.getStringExtra("code");
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
                .url(App.DOMAIN + "/remark/list")
                .addParams("password", "123")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject jsonObject = JSON.parseObject(response);
                        Object data = jsonObject.get("data");
                        List<Remark> remarks = JSON.parseArray(data.toString(), Remark.class);
                        List<String> objects = Lists.newArrayList();
                        objects.add("默认备注");
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
                                descEditText.setText(adapterView.getSelectedItem().toString());
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
                400);//延迟弹出

        findViewById(R.id.scan_button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = numberEditText.getText().toString();
                String type = Type.getCode(result);
                String price = priceEditText.getText().toString();
                String desc = descEditText.getText().toString();
                preferences = getSharedPreferences(App.USER, MODE_PRIVATE);
                //取出数据,第一个参数是存取的键，第二个参数-->如果该key不存在，返回默认值，这里返回的默认值是""
                OkHttpUtils
                        .post()
                        .url(App.DOMAIN + "/express/save")
                        .addParams("number", number)
                        .addParams("type", type)
                        .addParams("price", price)
                        .addParams("desc", desc)
                        .addParams("userId", preferences.getString("token", ""))
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

        findViewById(R.id.scan_scan_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File dir = new File(Environment.getExternalStorageDirectory(), "express");
                if (!dir.exists()) {
                    if (dir.mkdirs()) {
                        Log.d("ScanActivity", "onClick: 已创建" + dir.getPath());
                    }
                }
                currentImageFile = new File(dir, code + ".jpg");
                it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(currentImageFile));
                startActivityForResult(it, Activity.DEFAULT_KEYS_DIALER);
            }
        });
    }

    //重写onActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            preferences = getSharedPreferences(App.EXPRESS_IMAGE, MODE_PRIVATE);//获取待上传文件
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(code, currentImageFile.getAbsolutePath());
            editor.apply();
            ImageView ivShow = (ImageView) findViewById(R.id.scan_iv);
            ivShow.setImageBitmap(Utils.getSmallBitmap(currentImageFile.getAbsolutePath(), 480, 360));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 其次把文件插入到系统图库
                    try {
                        MediaStore.Images.Media.insertImage(getContentResolver(),
                                currentImageFile.getAbsolutePath(), currentImageFile.getName(), null);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    // 最后通知图库更新
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(currentImageFile)));
                }
            }).start();
        }

    }
}
