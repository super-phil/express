package com.magic.express;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText username = (EditText) findViewById(R.id.edit_username);
                EditText password = (EditText) findViewById(R.id.edit_password);
                OkHttpUtils
                        .get()
                        .url("http://123.56.102.224:17051/express/chart-type")
                        .addParams("username", "hyman")
                        .addParams("password", "123")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onResponse(String response, int id) {
                                Toast.makeText(MainActivity.this, "登录成功!", Toast.LENGTH_LONG).show();
                                System.out.println(response);
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                intent.putExtra("result", response);
                                startActivity(intent);
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
