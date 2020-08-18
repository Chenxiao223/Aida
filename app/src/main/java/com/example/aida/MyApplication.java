package com.example.aida;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.aida.greendao.DaoMaster;
import com.example.aida.greendao.DaoSession;

public class MyApplication extends Application {
    private static MyApplication sInstance;
    private DaoMaster.DevOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        //本类对象
        sInstance = this;
        //创建数据库以及创建数据表
        setDatabase();
    }

    private void setDatabase() {
        //1.创建数据库
        mHelper = new DaoMaster.DevOpenHelper(this, "mydb", null);
        //2.获取读写对象
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.disableWriteAheadLogging();
        //3.获取管理器类
        mDaoMaster = new DaoMaster(db);
        //4.获取表对象
        mDaoSession = mDaoMaster.newSession();
    }

    public static MyApplication getInstance() {
        return sInstance;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
