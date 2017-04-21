package com.wenzhiguo.user;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by dell on 2017/4/21.
 */

public class UserMessage {
    private Bean bean;
    public UserMessage(Bean bean){
        this.bean = bean;
    }
    public TextWatcher mWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String s = editable.toString();
            bean.username = s;
        }
    };
}
