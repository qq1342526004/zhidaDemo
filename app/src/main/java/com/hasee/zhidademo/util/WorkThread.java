package com.hasee.zhidademo.util;

import android.os.Message;

import com.alibaba.fastjson.JSONArray;

import java.util.logging.Handler;

/**
 * Created by fangju on 2019/1/3
 */
public class WorkThread extends Thread {
    private MyHandler handler;
    private String sendMessage;//发送至服务端的数据
    private String receMessage;//接收的数据
    private SocketUtil socketUtil;//套接字工具类

    public WorkThread(MyHandler handler, String sendMessage){
        this.handler = handler;
        this.sendMessage = sendMessage;
    }

    @Override
    public void run() {
        socketUtil = new SocketUtil();//初始化
        socketUtil.buildConnect();//建立连接
        receMessage = socketUtil.sendAndReceiveMsg(sendMessage);
        //将消息发送至处理界面
        Message msg = handler.obtainMessage();
        msg.obj = receMessage;
        handler.sendMessage(msg);
        socketUtil.close();//断开连接
    }
}
