package com.rainstorm.whoa.base;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.rainstorm.whoa.utlis.ThreadManager;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by liys on 2019-01-25.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initOkGo();
    }
    
    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(10000, TimeUnit.MILLISECONDS);
        builder.writeTimeout(10000, TimeUnit.MILLISECONDS);
        builder.connectTimeout(10000, TimeUnit.MILLISECONDS);

        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                .setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE);
    }

    public static ThreadPoolExecutor getThreadPool() {
        return ThreadManager.get();
    }
}
