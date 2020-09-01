package com.example.aida.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aida.MyApplication;
import com.example.aida.R;
import com.example.aida.activity.AboutUsActivity;
import com.example.aida.activity.RecycleBinActivity;
import com.example.aida.activity.SettingActivity;
import com.example.aida.been.User;
import com.example.aida.greendao.UserDao;
import com.example.aida.util.TwCog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

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
        initData();
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

    @OnClick({R.id.ly_setting, R.id.ly_recycle_bin, R.id.ly_about_us})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ly_setting://设置
                startActivityForResult(new Intent(getActivity(), SettingActivity.class),111);
                break;
            case R.id.ly_recycle_bin:
                startActivity(new Intent(getActivity(), RecycleBinActivity.class));
                break;
            case R.id.ly_about_us://关于我们
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initData();
    }
}