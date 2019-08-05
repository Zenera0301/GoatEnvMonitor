package com.goatenvmonitor;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebviewActivity extends AppCompatActivity {

    private WebView web;
    private String urlMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);


        /**
         * web
         */
        Intent intent = this.getIntent();  // 获取已有的intent对象
        Bundle bundle = intent.getExtras();  // 获取intent里面的bundle对象
        urlMessage = bundle.getString("URLMessage");  // 获取Bundle里面的字符串
        web = findViewById(R.id.webView);
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient());
        web.loadUrl(urlMessage);
    }


}
