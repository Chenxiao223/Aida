package com.example.aida.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.aida.MyApplication;
import com.example.aida.R;
import com.example.aida.been.User;
import com.example.aida.greendao.UserDao;
import com.example.aida.util.TwCog;
import com.example.aida.view.CustomDialog;
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

/**
 * 设置页
 */
public class SettingActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.view)
    View view;

    private RxPermissions rxPermissions;
    private CustomDialog photo_dialog;
    private User mUser;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        initImmersionBar(view);
        tv_title.setText("设置");
        userDao = MyApplication.getInstance().getDaoSession().getUserDao();
        List<User> list = userDao.queryBuilder().where(UserDao.Properties.Name.eq(TwCog.UserName)).list();
        mUser = list.get(0);
        rxPermissions = new RxPermissions(this);
    }

    @OnClick({R.id.iv_back,R.id.ly_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ly_setting:
                showPhotoDialog();
                break;
        }
    }

    public void showPhotoDialog() {
        if (null == photo_dialog) {
            photo_dialog = new CustomDialog(this, R.layout.photo_dialog, new int[]{R.id.tv_camera, R.id.tv_photo, R.id.tv_cancel}, false, Gravity.BOTTOM);
            photo_dialog.setOnDialogItemClickListener(new CustomDialog.OnCustomDialogItemClickListener() {
                @Override
                public void OnCustomDialogItemClick(CustomDialog dialog, View view) {
                    switch (view.getId()) {
                        case R.id.tv_cancel:
                            photo_dialog.dismiss();
                            break;
                        case R.id.tv_camera:
                            rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(aBoolean -> {
                                if (aBoolean) {
                                    RxPhotoTool.openCameraImage(SettingActivity.this);
                                    photo_dialog.dismiss();
                                }
                            });

                            break;

                        case R.id.tv_photo:
                            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(aBoolean -> {
                                if (aBoolean) {
                                    RxPhotoTool.openLocalImage(SettingActivity.this);
                                    photo_dialog.dismiss();
                                }
                            });
                            break;
                    }
                }
            });
        }
        photo_dialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GET_IMAGE_BY_CAMERA:

                    String dz = RxPhotoTool.getRealFilePath(this, RxPhotoTool.imageUriFromCamera);

                    Luban.with(SettingActivity.this).load(dz).ignoreBy(100).setTargetDir(RxFileTool.getSDCardPath()).filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"))).setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onSuccess(File file) {

                            if (file.exists()) {
                                if (file.exists()) {
                                    String photo_path = file.getPath();
                                    alterUser(photo_path);
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            RxToast.error(e.getMessage());
                        }
                    }).launch();

                    break;

                case GET_IMAGE_FROM_PHONE://图库

                    if (null != data.getData()) {

                        File files = new File(RxPhotoTool.getImageAbsolutePath(this, data.getData()));

                        Luban.with(SettingActivity.this).load(files).setTargetDir(RxFileTool.getSDCardPath()).filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"))).setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                            }

                            @Override
                            public void onSuccess(File file) {

                                if (file.exists()) {
                                    String photo_path = file.getPath();
                                    alterUser(photo_path);
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

    public void alterUser(String photo_path) {
        User user = new User(mUser.getId(), mUser.getName(), mUser.getPwd(), photo_path);
        userDao.update(user);
        finish();
    }
}