package com.hasee.zhidademo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.hasee.zhidademo.R;
import com.hasee.zhidademo.util.MyHandler;

/**
 * Created by fangju on 2019/1/3
 */
public class WelComeActivity extends BaseActivity {
    private Context mContext;//上下文
    private final int WHAT = 0;//
    private final int delayTime = 2000;//欢迎界面停留时间

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏以及状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
////                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        mContext = WelComeActivity.this;
        handler.sendEmptyMessageDelayed(WHAT,delayTime);
    }

    /**
     * 处理UI
     */
    private MyHandler handler = new MyHandler(mContext){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case WHAT:{//跳转至登陆页面
                    Intent intent = new Intent(mContext,LoginActivity.class);
                    startActivity(intent);
                    break;
                }
            }
        }
    };
}
