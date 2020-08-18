package com.example.aida.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aida.MyApplication;
import com.example.aida.R;
import com.example.aida.been.User;
import com.example.aida.greendao.UserDao;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录页
 */
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.et_pwd2)
    EditText et_pwd2;
    @BindView(R.id.view)
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        initImmersionBar(view);
        tv_title.setText("注册");
    }

    public String getName() {
        return et_name.getText().toString().trim();
    }

    public String getPwd() {
        return et_pwd.getText().toString().trim();
    }

    public String getPwd2() {
        return et_pwd2.getText().toString().trim();
    }

    @OnClick({R.id.iv_back,R.id.btn_register})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_register://点击注册
                List<User> list = MyApplication.getInstance().getDaoSession().getUserDao().queryBuilder().where(UserDao.Properties.Name.eq(getName())).list();
                if (!list.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "此账号已存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(getName())) {
                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(getPwd())) {
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(getPwd2())) {
                    Toast.makeText(RegisterActivity.this, "请确认密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (getPwd().equals(getPwd2())) {
                    User user = new User(null, getName(), getPwd(), null);
                    MyApplication.getInstance().getDaoSession().getUserDao().insert(user);
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "密码要一致", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}