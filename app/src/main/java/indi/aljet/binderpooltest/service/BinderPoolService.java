package indi.aljet.binderpooltest.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import indi.aljet.binderpooltest.pool.BinderPool;

/**
 * Created by LJL-lenovo on 2017/7/5.
 */

public class BinderPoolService extends Service {

    private static final String TAG = "BinderPoolService";
    private Binder mBinderPool = new
            BinderPool.BinderPoolImp();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind");
        return mBinderPool;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
