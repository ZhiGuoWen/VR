package com.wenzhiguo.user.presenter;

import android.os.Handler;
import android.text.TextUtils;

import com.wenzhiguo.user.Bean;
import com.wenzhiguo.user.InrerfaceInfo;

/**
 * Created by dell on 2017/4/19.
 */

public class MeassagePresenter {

    private static InrerfaceInfo inrerfaceInfo;
    private static Handler handler = new Handler();

    public MeassagePresenter(InrerfaceInfo inrerfaceInfo) {
        this.inrerfaceInfo = inrerfaceInfo;
    }

    public static boolean MvpMessage(Bean bean) {
        if (bean.password.equals(bean.username)) {
            //3秒延迟操作
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    inrerfaceInfo.Successful();
                }
            },3000);
            return true;
        } else {
            inrerfaceInfo.Error();
            return false;
        }
    }
    //判断是否输入为空
    public static boolean isEmpty(Bean bean) {
        if (!TextUtils.isEmpty(bean.username)) {
            return true;
        }
        return false;
    }
}