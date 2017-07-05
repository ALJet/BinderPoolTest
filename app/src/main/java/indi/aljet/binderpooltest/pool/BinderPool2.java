package indi.aljet.binderpooltest.pool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

import indi.aljet.binderpooltest.IBinderPool;
import indi.aljet.binderpooltest.service.BinderPoolService;

/**
 * Created by LJL-lenovo on 2017/7/5.
 */

public class BinderPool2 {

    private static final String TAG = "BinderPool2";

    public static final int BINDER_NOT = -1;
    public static final int BINDER_COMPUTE = 0;
    public static final int BINDER_SECURITY_CENTER = 1;

    private Context mContext;
    private IBinderPool mBinderPool;
    private static volatile BinderPool2 sInstance;
    private CountDownLatch mConnectBinderPoolCountDownLatch;

    private BinderPool2(Context context){
        mContext = context
                .getApplicationContext();
        connectBinderPoolService();
    }

    public static BinderPool2 getsInstance(Context context){
        if(sInstance == null){
            synchronized (BinderPool.class){
                if (sInstance == null){
                    sInstance = new
                            BinderPool2(context);
                }
            }
        }
        return sInstance;
    }


    private synchronized void connectBinderPoolService(){
        mConnectBinderPoolCountDownLatch = new
                CountDownLatch(1);
        Intent servcie = new Intent(mContext
        , BinderPoolService.class);
        mContext.bindService(servcie,
                mBinderPoolConnection,
                Context.BIND_AUTO_CREATE);
        try{
            mConnectBinderPoolCountDownLatch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public IBinder queryBinder(int binderCode){
        IBinder binder = null;
        try{
            if(mBinderPool != null){
                binder = mBinderPool
                        .queryBinder(binderCode);
            }
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return binder;
    }

    private ServiceConnection mBinderPoolConnection
            = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderPool = IBinderPool.Stub
                    .asInterface(service);
            try{
                mBinderPool.asBinder()
                        .linkToDeath(mBinderPoolDeathRecipient,0);
            }catch (RemoteException e){
                e.printStackTrace();
            }
            mConnectBinderPoolCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    private IBinder.DeathRecipient mBinderPoolDeathRecipient =
            new IBinder.DeathRecipient() {
                @Override
                public void binderDied() {
                    Log.w(TAG, "binder died.");
                    mBinderPool.asBinder()
                            .unlinkToDeath(mBinderPoolDeathRecipient
                            ,0);
                    mBinderPool = null;
                    connectBinderPoolService();
                }
            };

}
