package com.example.aida.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.aida.R;

import butterknife.BindView;

public class StartActivity extends BaseActivity {
    @BindView(R.id.view)
    View view;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_start);
        super.onCreate(savedInstanceState);
        initImmersionBar(view);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                finish();
            }
        }, 3000);
    }
}