package com.example.aida.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.aida.MyApplication;
import com.example.aida.R;
import com.example.aida.adapter.AddClothesAdapter;
import com.example.aida.been.ImageList;
import com.example.aida.greendao.ImageListDao;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.vondear.rxtool.RxFileTool;
import com.vondear.rxtool.RxPhotoTool;
import com.vondear.rxtool.view.RxToast;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.vondear.rxtool.RxPhotoTool.GET_IMAGE_BY_CAMERA;
import static com.vondear.rxtool.RxPhotoTool.GET_IMAGE_FROM_PHONE;

public class AddClothesActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_text_right)
    TextView tv_text_right;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.view)
    View view;

    private int mFlag;
    private String photo_path;
    private AddClothesAdapter adapter;
    private RxPermissions rxPermissions;
    private ImageListDao imageListDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_addclothes);
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    private void initView() {
        initImmersionBar(view);
        mFlag = getIntent().getExtras().getInt("flag");
        imageListDao = MyApplication.getInstance().getDaoSession().getImageListDao();
        rxPermissions = new RxPermissions(this);
        tv_title.setText(getIntent().getExtras().getString("title"));
        tv_text_right.setVisibility(View.VISIBLE);
        tv_text_right.setText("添加");

        StaggeredGridLayoutManager recyclerViewLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        adapter = new AddClothesAdapter(this, R.layout.item_add_clothes);
        recyclerView.setAdapter(adapter);
    }

    public void initData() {
        showAll();
    }

    @OnClick({R.id.tv_text_right, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_text_right:
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(aBoolean -> {
                    if (aBoolean) {
                        RxPhotoTool.openLocalImage(AddClothesActivity.this);
                    }
                });
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case GET_IMAGE_FROM_PHONE://图库

                    if (null != data.getData()) {

                        File files = new File(RxPhotoTool.getImageAbsolutePath(this, data.getData()));

                        Luban.with(this).load(files).setTargetDir(RxFileTool.getSDCardPath()).filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"))).setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                            }

                            @Override
                            public void onSuccess(File file) {

                                if (file.exists()) {
                                    photo_path = file.getPath();
                                    ImageList imageList = new ImageList(null, photo_path, mFlag);
                                    imageListDao.insert(imageList);
                                    showAll();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                RxToast.error(e.getMessage());
                            }
                        }).launch();
                    }
                    break;
            }
        }
    }

    public void showAll() {
        List<ImageList> list = imageListDao.queryBuilder().where(ImageListDao.Properties.Flag.eq(mFlag)).list();
        adapter.setNewData(list);
        adapter.notifyDataSetChanged();
    }
}