package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.example.myapplication.bean.MVVMActivityConfiguration;
import com.example.myapplication.bean.UserInfo;
import com.experiment.EnableConfiguration;
import com.experiment.SpringMVVM;
import com.experiment.interfaces.Controller;

@EnableConfiguration(config = MVVMActivityConfiguration.class)
public class MVVMActivity extends Activity {

    private Controller mController;
    private boolean flag = false;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvm);

        mController = SpringMVVM.getController(this);


        mHandler.post(new Runnable() {
            @Override
            public void run() {
                UserInfo info = new UserInfo();
                info.username = "bob";
                info.imgRes = R.drawable.red_package_bg;
                mController.setState(info);
            }
        });

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mController.setState("flag",flag);
            }
        }, 1000);


    }


}
