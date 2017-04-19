package com.wenzhiguo.user.net;

import android.text.TextUtils;

import com.wenzhiguo.user.Bean;

/**
 * Created by dell on 2017/4/19.
 */

public class MessageMvp {
    public static boolean ThreadMessage(Bean bean) {
        if (bean.password.equals(bean.username)&& !TextUtils.isEmpty(bean.username)){
            return true;
        }
        return false;
    }
}
