package indi.aljet.binderpooltest.imp;

import android.os.IBinder;
import android.os.RemoteException;

import indi.aljet.binderpooltest.IBinderPool;
import indi.aljet.binderpooltest.pool.BinderPool2;

/**
 * Created by LJL-lenovo on 2017/7/5.
 */

public class BinderPoolImpl extends IBinderPool.Stub {

    @Override
    public IBinder queryBinder(int binerCode) throws RemoteException {
        IBinder binder = null;
        switch (binerCode){

            case BinderPool2.BINDER_COMPUTE:
                binder = new ComputeImpl2();
                break;
            case BinderPool2.BINDER_SECURITY_CENTER:
                binder = new SecurityCenterImpl2();
                break;
        }
        return binder;
    }
}
