package com.hasee.zhidademo.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * Created by fangju on 2019/1/3
 */
public class MyHandler extends Handler {
    private Context mContext;//上下文

    public MyHandler(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public void handleMessage(Message msg) {

    }
}
