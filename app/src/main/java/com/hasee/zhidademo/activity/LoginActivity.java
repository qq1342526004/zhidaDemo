package com.hasee.zhidademo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hasee.zhidademo.R;
import com.hasee.zhidademo.dialog.CustomProgress;
import com.hasee.zhidademo.util.MyHandler;
import com.hasee.zhidademo.util.ToastUtil;
import com.hasee.zhidademo.util.WorkThread;

import static com.hasee.zhidademo.bean.MsgType.MSG_LOGIN;
import static com.hasee.zhidademo.bean.MsgType.MSG_LOGIN_FAILED;
import static com.hasee.zhidademo.bean.MsgType.MSG_LOGIN_SUCCESS;

/**
 * Created by fangju on 2019/1/3
 */
public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private Context mContext;//上下文
    private EditText usernameEt, passwordEt;//用户名、密码输入框
    private Button loginBtn;//登陆按钮
    private CustomProgress progress;//


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = LoginActivity.this;
        usernameEt = (EditText)findViewById(R.id.username_et);
        passwordEt = (EditText)findViewById(R.id.password_et);
        loginBtn = (Button)findViewById(R.id.login_Btn);
        loginBtn.setOnClickListener(onClickListener);
        usernameEt.setText("1");
        passwordEt.setText("1");
    }

    /**
     * 点击事件监听
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.login_Btn:{//登陆
                    String userName = usernameEt.getText().toString().trim();
                    String passWord = passwordEt.getText().toString().trim();
                    if(!etIsEmpty(userName,passWord)){//输入框不为空
                        progress = new CustomProgress(mContext);
                        progress.show(mContext,getResources().getString(R.string.login_loading),
                                true,null);//显示登陆框
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.add(MSG_LOGIN);//登陆
                        jsonArray.add(userName);//用户名
                        jsonArray.add(passWord);//密码
                        new WorkThread(handler,jsonArray.toJSONString()).start();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * 判断输入框是否为空(默认为空)
     * @return
     */
    private boolean etIsEmpty(String userName,String passWord){
        if(TextUtils.isEmpty(userName)||TextUtils.isEmpty(passWord)){//输入框有为空
            return true;
        }
        return false;
    };

    /**
     * 处理UI
     */
    private MyHandler handler = new MyHandler(mContext){
        @Override
        public void handleMessage(Message msg) {
            JSONArray jsonArray = JSON.parseArray((String) msg.obj);
            Log.d(TAG, "handleMessage: "+jsonArray.toJSONString());
            int id = Integer.parseInt(jsonArray.getString(0));//命令号
            switch (id){
                case MSG_LOGIN_FAILED:{//登陆失败
                    ToastUtil.getInstance(mContext).showShortToast(
                            getResources().getString(R.string.login_failed));
                    break;
                }
                case MSG_LOGIN_SUCCESS:{//登陆成功
                    ToastUtil.getInstance(mContext).showShortToast(
                            getResources().getString(R.string.login_success));
                    Intent intent = new Intent(mContext,MainActivity.class);
                    startActivity(intent);
                    break;
                }
            }
        }
    };
}
