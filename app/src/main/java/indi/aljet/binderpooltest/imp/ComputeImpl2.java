package indi.aljet.binderpooltest.imp;

import android.os.RemoteException;

import indi.aljet.binderpooltest.ICompute;

/**
 * Created by LJL-lenovo on 2017/7/5.
 */

public class ComputeImpl2 extends ICompute.Stub {

    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
