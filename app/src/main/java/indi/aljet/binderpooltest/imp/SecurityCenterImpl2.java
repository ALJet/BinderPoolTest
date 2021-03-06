package indi.aljet.binderpooltest.imp;

import android.os.RemoteException;

import indi.aljet.binderpooltest.ISecurityCenter;

/**
 * Created by LJL-lenovo on 2017/7/5.
 */

public class SecurityCenterImpl2 extends ISecurityCenter.Stub {

    private static final char SECRET_CODE = '^';

    @Override
    public String encrypt(String content) throws RemoteException {
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] ^= SECRET_CODE;
        }
        return new String(chars);
    }

    @Override
    public String decrypt(String password) throws RemoteException {
        return encrypt(password);
    }
}
