package com.goatenvmonitor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements View.OnClickListener{
    private EditText mUsername;
    private EditText mPassword;
    private Button mLogin;

    private String zhanghao = "admin", mima = "admin";  // 定义后台用户名与密码
    private String username, password;  // 输入的用户名和密码


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setImmersiveStatusBar(this,true);

        mUsername = (EditText) findViewById(R.id.editTextUserName);
        mPassword = (EditText) findViewById(R.id.editTextLoginPassword);
        mLogin = (Button) findViewById(R.id.btnLogin);

        // 获得SharedPreferences,并创建文件名称为"mrsoft"
        final SharedPreferences sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();  // 获得Editor对象,用于存储用户名与密码信息
        // 判断SharedPreferences文件中，用户名、密码是否存在
        if (sp.getString("username", username) != null && sp.getString("password", password) != null) {

            // 存在就判断用户名、密码与后台是否相同，相同直接登录
            if (sp.getString("username", username).equals(zhanghao) && sp.getString("password", password).equals(mima)) {
                Intent intent = new Intent(LoginActivity.this, HomePage.class);  // 通过Intent跳转登录后界面
                startActivity(intent);  // 启动跳转界面
                finish();
            }
        } else {
            // 实现SharedPreferences文件不存在时，手动登录并存储用户名与密码
            mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    username = mUsername.getText().toString();  // 获得输入的用户名
                    password = mPassword.getText().toString();  // 获得输入的密码
                    // 如果输入用户名、密码与后台相同时，登录并存储
                    if (username.equals(zhanghao) && password.equals(mima)) {  // 判断输入的用户名密码是否正确
						Toast.makeText(LoginActivity.this, "用户名、密码正确", Toast.LENGTH_SHORT).show();
						Intent  intent = new Intent(LoginActivity.this, HomePage.class);  //通过Intent跳转登录后界面
						startActivity(intent);  // 启动跳转界面
						editor.putString("username", username);  // 存储用户名
						editor.putString("password", password);  // 存储密码
						editor.commit();  // 提交信息
						Toast.makeText(LoginActivity.this, "已保存用户名密码", Toast.LENGTH_SHORT).show();
                       finish();
					}else {
                        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    @Override
    public void onClick(View v) {                    
        switch (v.getId()) {

            case R.id.textViewSignUp://注册字样被点击，进行注册
                break;
        }
    }
}
