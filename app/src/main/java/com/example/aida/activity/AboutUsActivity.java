package com.example.aida.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aida.R;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.view)
    View view;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.text)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about_us);
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        initImmersionBar(view);
        tv_title.setText("关于我们");
        text.setText("本APP具有以下几个功能：1、我的衣橱，可以上传上装、下装、鞋子、配饰等四类进入，随意添加自己喜欢的搭配，并且具有删除和修改的功能。2、查看时尚杂志，提升自己的审美的同时还可以参考设计师们的搭配。3、只能搜索，搜索自己添加的搭配。4、我的，有设置头像的功能，回收站功能还有APP的简介。");
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

}