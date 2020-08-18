package com.example.aida.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.app.Activity.RESULT_OK;
import static com.vondear.rxtool.RxPhotoTool.GET_IMAGE_BY_CAMERA;
import static com.vondear.rxtool.RxPhotoTool.GET_IMAGE_FROM_PHONE;

/**
 * 我的
 */
public class MyFragment extends BaseFragment {
    @BindView(R.id.view)
    View mView;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.iv_head)
    CircleImageView iv_head;
    private User mUser;
    private UserDao userDao;
    private RxPermissions rxPermissions;
    private String photo_path;
    private CustomDialog photo_dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        initImmersionBar(mView);
        userDao = MyApplication.getInstance().getDaoSession().getUserDao();
        initView();
        initData();
    }

    private void initView() {
        rxPermissions = new RxPermissions(this);
    }

    private void initData() {
        List<User> list = userDao.queryBuilder().where(UserDao.Properties.Name.eq(TwCog.UserName)).list();
        mUser = list.get(0);
        String psth = mUser.getImagePath();
        if (!TextUtils.isEmpty(psth)) {
            Glide.with(this).asBitmap().load(psth)
                    .thumbnail(0.5f)
                    .into(iv_head);
        }

        tv_user_name.setText(mUser.getName());
    }

    @OnClick({R.id.iv_head, R.id.ly_setting, R.id.ly_recycle_bin, R.id.ly_about_us})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_head:
                showPhotoDialog();
                break;
            case R.id.ly_setting:
                break;
            case R.id.ly_recycle_bin:
                break;
            case R.id.ly_about_us:
                break;
        }
    }

    public void showPhotoDialog() {
        if (null == photo_dialog) {
            photo_dialog = new CustomDialog(getActivity(), R.layout.photo_dialog, new int[]{R.id.tv_camera, R.id.tv_photo, R.id.tv_cancel}, false, Gravity.BOTTOM);
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
                                    RxPhotoTool.openCameraImage(getActivity());
                                    photo_dialog.dismiss();
                                }
                            });

                            break;

                        case R.id.tv_photo:
                            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(aBoolean -> {
                                if (aBoolean) {
                                    RxPhotoTool.openLocalImage(getActivity());
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

                    String dz = RxPhotoTool.getRealFilePath(getActivity(), RxPhotoTool.imageUriFromCamera);

                    Luban.with(getActivity()).load(dz).ignoreBy(100).setTargetDir(RxFileTool.getSDCardPath()).filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"))).setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onSuccess(File file) {

                            if (file.exists()) {
                                if (file.exists()) {
                                    photo_path = file.getPath();
                                    Glide.with(getActivity()).load(photo_path).thumbnail(0.5f).into(iv_head);
                                    alterUser();
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

                        File files = new File(RxPhotoTool.getImageAbsolutePath(getActivity(), data.getData()));

                        Luban.with(getActivity()).load(files).setTargetDir(RxFileTool.getSDCardPath()).filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"))).setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                            }

                            @Override
                            public void onSuccess(File file) {

                                if (file.exists()) {
                                    photo_path = file.getPath();
                                    Glide.with(getActivity()).load(photo_path).thumbnail(0.5f).into(iv_head);
                                    alterUser();
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

    public void alterUser() {
        User user = new User(mUser.getId(), mUser.getName(), mUser.getPwd(), photo_path);
        userDao.update(user);
    }

}