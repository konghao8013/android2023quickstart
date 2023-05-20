package com.banana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.go_to_second_activity).setOnClickListener(this);
        findViewById(R.id.get_blogs).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int rid = v.getId();
        if (rid == R.id.go_to_second_activity) {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        }
        if (rid == R.id.get_blogs) {
            GetBlogs();
        }
    }

    public void GetBlogs() {
        OkHttpClient client = new OkHttpClient.Builder()
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(),SSLSocketClient.getX509TrustManager())
                .build()
                ;
        String url = "https://siwei.me/interface/blogs/show?id=2347";
        final Activity that = this;
        Request request = new Request.Builder()

                .url(url)
                .get()

                .build();
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        final String result = response.body().string();
                        Gson gson=new Gson();
                        BlogResult blogResult=gson.fromJson(result,BlogResult.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("MainActivity", "======= reponse:" + result);


                                Bundle bundle=new Bundle();
                                bundle.putString("blog_text",blogResult.result.body);
                                Intent intent = new Intent(that, ShowBlogActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                });
    }



}