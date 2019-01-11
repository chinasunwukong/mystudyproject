package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.myapplication.adapter.HomePublishAdapter;
import com.example.myapplication.bean.PublishBean;
import com.example.myapplication.utils.PublishBeanCreate;

import java.util.ArrayList;


public class PublishActivity extends Activity {

    private ListView listView;
    private HomePublishAdapter homePublishAdapter;
    private ImageView control;
    private ArrayList<PublishBean> publishBeans;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_publish_layout);

        publishBeans=PublishBeanCreate.create();

        DisplayMetrics metrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight=metrics.heightPixels;
        Log.e("getheight","screen height: "+screenHeight);

        int useHeight=getResources().getDimensionPixelSize(R.dimen.publish_control_height);
        Log.e("getheight","use height: "+useHeight);

        int dipHeight=getResources().getDimensionPixelSize(R.dimen.publish_dip_height);
        Log.e("getheight","dip height: "+dipHeight);

        int itemHeight=getResources().getDimensionPixelSize(R.dimen.publish_item_height);;
        Log.e("getheight","item height: "+itemHeight);

        int left=screenHeight-useHeight;
        int limitSize=left/itemHeight
                +(left%itemHeight==0?0:1);
        Log.e("getheight","item size: "+limitSize);


        listView = findViewById(R.id.publish_listview);
        homePublishAdapter = new HomePublishAdapter(this);
        homePublishAdapter.setLimitSize(limitSize);

        if (publishBeans != null) {
            homePublishAdapter.setPublishBeans(publishBeans);
        }
        listView.setAdapter(homePublishAdapter);


        control = findViewById(R.id.img_control);
        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAinm();
            }
        });
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnim();
            }
        }, 10);

    }



    private void startAnim() {
        RotateAnimation rotate = new RotateAnimation(0f, 45f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setInterpolator(new DecelerateInterpolator());
        rotate.setDuration(200);//设置动画持续时间
        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        // rotate.setStartOffset(10);//执行前的等待时间
        control.startAnimation(rotate);
    }

    private void finishWithAinm() {
        RotateAnimation rotate = new RotateAnimation(45f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotate.setInterpolator(new AccelerateDecelerateInterpolator());
        rotate.setDuration(200);//设置动画持续时间
        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finishActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        control.startAnimation(rotate);
        if (homePublishAdapter != null) {
            homePublishAdapter.close();
        }
    }


    private void finishActivity() {
        finish();
        overridePendingTransition(0, R.anim.publish_alpha_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
