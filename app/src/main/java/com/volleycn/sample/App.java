package com.volleycn.sample;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * @Describe
 * @Date : 2020/4/9
 * @Email : zhangmeng@newstylegroup.com
 * @Author : MENG
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
