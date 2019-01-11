package com.example.baiting.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;


import com.example.baiting.myapplication.bean.MVVMActivityConfiguration;
import com.example.baiting.myapplication.bean.UserInfo;
import com.experiment.EnableConfiguration;
import com.experiment.SpringMVVM;
import com.experiment.interfaces.Controller;

@EnableConfiguration(config=MVVMActivityConfiguration.class)
public class MVVMActivity extends Activity {

    private Controller mController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvm);

        mController=SpringMVVM.getController(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UserInfo info=new UserInfo();
                info.username="bob";
                info.imgRes=R.drawable.red_package_bg;
                mController.setState(info);
            }
        },1000);




    }


}
