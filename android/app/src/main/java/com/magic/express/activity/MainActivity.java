package com.magic.express.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.magic.express.App;
import com.magic.express.R;
import com.magic.express.model.User;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences(App.USER, MODE_PRIVATE);
        //取出数据,第一个参数是存取的键，第二个参数-->如果该key不存在，返回默认值，这里返回的默认值是""
        String username = preferences.getString("username", "");
        String password = preferences.getString("password", "");
        intent = new Intent(MainActivity.this, HomeActivity.class);
        if (!"".equals(username) && !"".equals(password)) {
            startActivity(intent);
        } else {
            findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText username = (EditText) findViewById(R.id.edit_username);
                    EditText password = (EditText) findViewById(R.id.edit_password);
                    final String un = username.getText().toString();
                    final String up = password.getText().toString();
                    OkHttpUtils
                            .post()
                            .url(App.DOMAIN + "/login")
                            .addParams("username", un)
                            .addParams("password", up)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onResponse(String response, int id) {
                                    User user = JSON.parseObject(response, User.class);
                                    Toast.makeText(MainActivity.this, "登录成功!", Toast.LENGTH_LONG).show();
                                    //获取editor对象
                                    SharedPreferences.Editor editor = preferences.edit();
                                    //存储数据时选用对应类型的方法
                                    editor.putString("username", un);
                                    editor.putString("password", up);
                                    editor.putString("token", user.getId() + "");
                                    //提交保存数据
                                    editor.apply();
                                    startActivity(intent);//
                                }

                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Toast.makeText(MainActivity.this, "登录失败,用户名或密码错误!", Toast.LENGTH_LONG).show();
                                }
                            });
                }
            });
        }

    }
}
