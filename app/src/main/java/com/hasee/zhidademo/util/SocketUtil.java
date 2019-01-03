package com.hasee.zhidademo.util;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketUtil {
    private int port = 5018;//端口号
    private String ip = "192.168.49.250";//ip地址
    private Socket socket = null;//套接字
    private DataOutputStream dataOutputStream = null;//输出流
    private DataInputStream dataInputStream = null;//输入流
    private ByteArrayOutputStream content = null;//存取输入流中的东西

    public SocketUtil(){
        Log.d("SocketUtil","已建立");
    }

    //建立客户端socket连接,输入输出流连接
    public void buildConnect(){
        try {
            socket = new Socket(ip, port);
            //输出流
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            //输入流
            dataInputStream = new DataInputStream(socket.getInputStream());
            Log.d("Socket","已连接");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //发送并接收数据
    public String sendAndReceiveMsg(String message){
        try {
            //对socket进行写操作,将数据按照指定格式发送给服务器端
            byte[] send_message = message.getBytes("UTF-8");//发给服务器的数据
            dataOutputStream.write(send_message);
            dataOutputStream.flush();//缓冲输出流
            socket.shutdownOutput();
            //对socket进行读取操作
            String receive_message = "";
            content = new ByteArrayOutputStream();
            byte[] bytes = new byte[2048];
            int n;//字节的个数
            do {//先把数据写入一个字节数组
                n = dataInputStream.read(bytes);
                content.write(bytes,0,n);//readline将会把json格式破坏掉
            }while(dataInputStream.available()!=0);
            bytes = null;
            receive_message = new String(content.toByteArray(),"UTF-8");
            return receive_message;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //断开连接
    public void close(){
        try {
            if(dataOutputStream != null){//关闭输出流
                dataOutputStream.close();
            }
            if(content != null){
                content.close();
            }
            if(dataInputStream != null){
                dataInputStream.close();
            }
            if(socket != null){
                socket.close();
            }
            Log.d("SocketUtil","断开连接");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //是否关闭（本地）
    public Boolean isClosed(){
        return socket.isClosed();
    }

    //判断是否连接（本地内存）
    public Boolean isConnected(){
        return socket.isConnected();
    }

    //判断输出流是否关闭
    public Boolean isOutputShutdown(){
        return socket.isOutputShutdown();
    }

    //判断输入流是否关闭
    public Boolean isInputShutdown(){
        return socket.isInputShutdown();
    }
}
