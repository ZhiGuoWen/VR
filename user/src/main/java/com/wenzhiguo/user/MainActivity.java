package com.wenzhiguo.user;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mUser;
    private EditText mPass;
    private Handler handler = new Handler();
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找控件
        initView();
    }

    private void initView() {
        mUser = (EditText) findViewById(R.id.user);
        mPass = (EditText) findViewById(R.id.pass);
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void login(View view) {
        //获取EditText
        String user = mUser.getText().toString().trim();
        String pass = mUser.getText().toString().trim();
        //判断用户名和密码和EditText不能为空
        if (user.equals(pass)&& !TextUtils.isEmpty(user)){
            //显示Progress
            mProgress.setVisibility(View.VISIBLE);
            //3秒延迟操作
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mProgress.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_SHORT).show();
                }
            },3000);
        }else {
            Toast.makeText(this, "错误", Toast.LENGTH_SHORT).show();
        }
    }
}