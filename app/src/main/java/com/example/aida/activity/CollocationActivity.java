package com.example.aida.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.aida.MyApplication;
import com.example.aida.R;
import com.example.aida.adapter.CollocationAdapter;
import com.example.aida.been.Collocation;
import com.example.aida.been.ImageList;
import com.example.aida.greendao.CollocationDao;
import com.example.aida.greendao.ImageListDao;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搭配
 */
public class CollocationActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_text_right)
    TextView tv_text_right;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.iv_clothes)
    ImageView iv_clothes;
    @BindView(R.id.iv_accessories)
    ImageView iv_accessories;
    @BindView(R.id.iv_bottoms)
    ImageView iv_bottoms;
    @BindView(R.id.iv_shoes)
    ImageView iv_shoes;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_type)
    TextView tv_type;

    private CollocationAdapter adapter;
    private ImageListDao imageListDao;
    private CollocationDao collocationDao;
    private int mFlag;
    private String imagePath1;
    private String imagePath2;
    private String imagePath3;
    private String imagePath4;
    private Long id = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_collocation);
        super.onCreate(savedInstanceState);

        initView();
        initListener();
    }

    private void initView() {
        initImmersionBar(view);
        imageListDao = MyApplication.getInstance().getDaoSession().getImageListDao();
        collocationDao = MyApplication.getInstance().getDaoSession().getCollocationDao();
        tv_title.setText("搭配");
        tv_text_right.setVisibility(View.VISIBLE);
        tv_text_right.setText("保存");
        id = getIntent().getExtras().getLong("id");
        if (id != 0) {
            List<Collocation> list = collocationDao.queryBuilder().where(CollocationDao.Properties.Id.eq(id)).list();
            if (!list.isEmpty()) {
                imagePath1 = list.get(0).getClothes();
                Glide.with(CollocationActivity.this).asBitmap().load(imagePath1).thumbnail(0.5f).into(iv_clothes);
                imagePath2 = list.get(0).getBottoms();
                Glide.with(CollocationActivity.this).asBitmap().load(imagePath2).thumbnail(0.5f).into(iv_bottoms);
                imagePath3 = list.get(0).getShoes();
                Glide.with(CollocationActivity.this).asBitmap().load(imagePath3).thumbnail(0.5f).into(iv_shoes);
                imagePath4 = list.get(0).getAccessories();
                Glide.with(CollocationActivity.this).asBitmap().load(imagePath4).thumbnail(0.5f).into(iv_accessories);
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CollocationAdapter(this, R.layout.item_collocation);
        recyclerView.setAdapter(adapter);
    }

    public void initListener() {
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ImageList imageList = (ImageList) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.image:
                        switch (mFlag) {
                            case 1://上衣
                                imagePath1 = imageList.getImagePath();
                                Glide.with(CollocationActivity.this).asBitmap().load(imagePath1).thumbnail(0.5f).into(iv_clothes);
                                break;
                            case 2://下装
                                imagePath2 = imageList.getImagePath();
                                Glide.with(CollocationActivity.this).asBitmap().load(imagePath2).thumbnail(0.5f).into(iv_bottoms);
                                break;
                            case 3://鞋子
                                imagePath3 = imageList.getImagePath();
                                Glide.with(CollocationActivity.this).asBitmap().load(imagePath3).thumbnail(0.5f).into(iv_shoes);
                                break;
                            case 4://配饰
                                imagePath4 = imageList.getImagePath();
                                Glide.with(CollocationActivity.this).asBitmap().load(imagePath4).thumbnail(0.5f).into(iv_accessories);
                                break;
                        }
                        break;
                }
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_text_right, R.id.iv_clothes, R.id.iv_accessories, R.id.iv_bottoms, R.id.iv_shoes})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_text_right://点保存
                if (TextUtils.isEmpty(imagePath1)) {
                    Toast.makeText(this, "请选择上衣", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(imagePath2)) {
                    Toast.makeText(this, "请选择下装", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(imagePath3)) {
                    Toast.makeText(this, "请选择鞋子", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(imagePath4)) {
                    Toast.makeText(this, "请选择配饰", Toast.LENGTH_SHORT).show();
                    return;
                }

                View v = getLayoutInflater().inflate(R.layout.view_edittext_dialog, null);
                final EditText et = (EditText) v.findViewById(R.id.edittext_ownername_dialog);
                new AlertDialog.Builder(this).setTitle("")
                        .setIcon(null)
                        .setView(v)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String str = et.getText().toString().trim();
                                if (TextUtils.isEmpty(str)) {
                                    Toast.makeText(CollocationActivity.this, "请输入名称", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (id != 0) {//修改
                                        collocationDao.deleteByKey(id);
                                    }
                                    Collocation collocation = new Collocation(null, str, imagePath1, imagePath2, imagePath4, imagePath3);
                                    collocationDao.insert(collocation);
                                    finish();
                                }
                            }
                        }).setNegativeButton("取消", null).show();
                break;
            case R.id.iv_clothes://上衣
                tv_type.setText("上衣");
                mFlag = 1;
                getData(mFlag);
                break;
            case R.id.iv_accessories://配饰
                tv_type.setText("配饰");
                mFlag = 4;
                getData(mFlag);
                break;
            case R.id.iv_bottoms://下装
                tv_type.setText("下装");
                mFlag = 2;
                getData(mFlag);
                break;
            case R.id.iv_shoes://鞋子
                tv_type.setText("鞋子");
                mFlag = 3;
                getData(mFlag);
                break;
        }
    }

    public void getData(int flag) {
        List<ImageList> list = imageListDao.queryBuilder().where(ImageListDao.Properties.Flag.eq(mFlag)).list();
        adapter.setNewData(list);
        adapter.notifyDataSetChanged();
    }
}