package com.example.aida.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.aida.MyApplication;
import com.example.aida.R;
import com.example.aida.adapter.RecycleBinAdapter;
import com.example.aida.adapter.WardrobeAdapter;
import com.example.aida.been.Collocation;
import com.example.aida.greendao.CollocationDao;
import com.example.aida.view.SlideRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 回收站
 */
public class RecycleBinActivity extends BaseActivity {
    private final int FLAG = 111;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.recyclerView)
    SlideRecyclerView recyclerView;

    private CollocationDao collocationDao;
    private RecycleBinAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recycle_bin);
        super.onCreate(savedInstanceState);

        initView();
        initData();
        initListener();
    }

    private void initView() {
        initImmersionBar(view);
        tv_title.setText("回收站");

        collocationDao = MyApplication.getInstance().getDaoSession().getCollocationDao();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecycleBinAdapter(this, R.layout.item_recycle_bin);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        List<Collocation> list = collocationDao.queryBuilder().where(CollocationDao.Properties.Isdelete.eq(1)).list();
        adapter.setNewData(list);
        adapter.notifyDataSetChanged();
    }

    private void initListener() {
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Collocation collocation = (Collocation) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.tv_recovery://恢复
                        collocation.setIsdelete(0);
                        collocationDao.update(collocation);
                        initData();
                        // 广播通知
                        Intent intent = new Intent();
                        intent.setAction("action.refreshFriend");
                        sendBroadcast(intent);
                        break;
                    case R.id.tv_delete://删除
                        collocationDao.deleteByKey(collocation.getId());
                        initData();
                        break;
                }
            }
        });
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