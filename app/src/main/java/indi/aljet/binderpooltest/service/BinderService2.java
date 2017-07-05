package indi.aljet.binderpooltest.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import indi.aljet.binderpooltest.IBinderPool;
import indi.aljet.binderpooltest.imp.BinderPoolImpl;
import indi.aljet.binderpooltest.pool.BinderPool;

/**
 * Created by LJL-lenovo on 2017/7/5.
 */

public class BinderService2 extends Service {
    private static final String TAG = "BinderPoolService2";

    private Binder mBinderPool = new
            BinderPoolImpl();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinderPool;
    }
}
