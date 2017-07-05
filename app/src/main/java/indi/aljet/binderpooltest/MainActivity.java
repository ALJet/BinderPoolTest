package indi.aljet.binderpooltest;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import indi.aljet.binderpooltest.imp.ComputeImpl;
import indi.aljet.binderpooltest.imp.SecurityCenterImpl;
import indi.aljet.binderpooltest.pool.BinderPool;
import indi.aljet.binderpooltest.pool.BinderPool2;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ISecurityCenter mSecurityCenter;
    private ICompute mCompute;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        }).start();
    }


    private void doWork(){
        BinderPool binderPool = BinderPool
                .getsInstance(MainActivity.this);
        IBinder securityBinder = binderPool
                .queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        mSecurityCenter = (ISecurityCenter)
                SecurityCenterImpl.asInterface(securityBinder);
        Log.d(TAG, "visit ISecurityCenter");
        String msg = "helloworld--安卓";
        System.out.println("content:"+msg);
        try{
            String password = mSecurityCenter.encrypt(msg);
            System.out.println("encrypt:" + password);
            System.out.println("decrypt:"+mSecurityCenter.decrypt(password));
        }catch (RemoteException e){
            e.printStackTrace();
        }

        Log.d(TAG, "visit ICompute");
        IBinder computeBinder = binderPool
                .queryBinder(BinderPool.BINDER_COMPUTE);
        mCompute = ComputeImpl.asInterface(computeBinder);
        try{
            System.out.println("3 + 5 ="
            + mCompute.add(3,5));
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }


    private void doWork2(){
        BinderPool2 binderPool =
                BinderPool2.getsInstance(MainActivity.this);
        IBinder securityBindrer =
                binderPool.queryBinder(BinderPool2.BINDER_COMPUTE);
        mSecurityCenter = (ISecurityCenter)
                SecurityCenterImpl
                .asInterface(securityBindrer);
        Log.d(TAG, "visit ISecurityCenter");
        String msg = "helloworld-安卓";
        System.out.println("content:" + msg);
        try {
            String password = mSecurityCenter.encrypt(msg);
            System.out.println("encrypt:" + password);
            System.out.println("decrypt:" + mSecurityCenter.decrypt(password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "visit ICompute");
        IBinder computeBinder = binderPool
                .queryBinder(BinderPool2.BINDER_COMPUTE);

        mCompute = ComputeImpl.asInterface(computeBinder);
        try {
            System.out.println("3+5=" + mCompute.add(3, 5));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

