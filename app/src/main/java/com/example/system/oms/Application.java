package com.example.system.oms;

import com.example.system.oms.greendao.gen.DaoManager;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化数据库管理类
        DaoManager.getInstance().init(this);
    }

}
