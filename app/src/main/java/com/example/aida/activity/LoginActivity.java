package com.example.aida.activity;

import android.content.Intent;
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
import com.example.aida.util.TwCog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.leefeng.promptlibrary.PromptDialog;

/**
 * 登录页
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.view)
    View view;

    public PromptDialog promptDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        initImmersionBar(view);

        initView();
    }

    private void initView() {
        promptDialog = new PromptDialog(this);
    }


    public String getName() {
        return et_name.getText().toString().trim();
    }

    public String getPwd() {
        return et_pwd.getText().toString().trim();
    }

    @OnClick({R.id.tv_register,R.id.btn_login})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.tv_register://注册
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_login://登录

                if (!TextUtils.isEmpty(getName()) && !TextUtils.isEmpty(getPwd())) {
                    promptDialog.showLoading("正在登陆");
                    List<User> list = MyApplication.getInstance().getDaoSession().getUserDao().queryBuilder().where(UserDao.Properties.Name.eq(getName())).list();
                    if (list.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "无此账号", Toast.LENGTH_SHORT).show();
                        promptDialog.dismiss();
                        return;
                    }
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getPwd().equals(getPwd())) {
                            TwCog.UserName = list.get(i).getName();
                            TwCog.UserId = list.get(i).getId();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "请输入账号密码", Toast.LENGTH_SHORT).show();
                }
                promptDialog.dismiss();

                break;
        }
    }
}