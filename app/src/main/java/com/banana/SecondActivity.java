package com.banana;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        findViewById(R.id.back_to_main_activity_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int rid=v.getId();
        if(rid==R.id.back_to_main_activity_button){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}