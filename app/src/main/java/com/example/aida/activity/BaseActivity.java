package com.example.aida.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.gyf.immersionbar.ImmersionBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseActivity extends FragmentActivity {
    private Unbinder knife;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        knife = ButterKnife.bind(this);
    }

    public void initImmersionBar(View view) {
        ImmersionBar.with(this)
                .statusBarView(view)
                .autoDarkModeEnable(true)
                .init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        knife.unbind();
    }
}
