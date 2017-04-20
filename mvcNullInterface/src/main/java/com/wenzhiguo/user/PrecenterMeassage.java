package com.wenzhiguo.user;

import android.os.Handler;
import android.text.TextUtils;

/**
 * Created by dell on 2017/4/20.
 */

public class PrecenterMeassage {
    private final MainActivity mainActivity;
    private Handler handler = new Handler();
    public PrecenterMeassage(MainActivity mainActivity) {
        this.mainActivity=mainActivity;
    }

    public boolean isMeassage(Bean bean){
        if (bean.password.equals(bean.username)){
            //3秒延迟操作
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mainActivity.Success();
                }
            }, 3000);
            return true;
        }
        return false;
    }
    public boolean isNull(Bean bean){
        if (!TextUtils.isEmpty(bean.username)){
            return true;
        }
        return false;
    }
}
