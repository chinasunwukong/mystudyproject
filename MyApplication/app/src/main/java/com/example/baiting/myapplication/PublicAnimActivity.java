package com.example.baiting.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.baiting.myapplication.adapter.PublicGridViewAdapter;
import com.example.baiting.myapplication.utils.BitmapBlurUtil;
import com.example.baiting.myapplication.utils.BitmapUtils;

public class PublicAnimActivity extends Activity {

    private ImageView showPublic;

    private static final int duration = 500;

    private GridView gridView;
    private PublicGridViewAdapter adapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);


        showPublic = findViewById(R.id.show_public);
        gridView = findViewById(R.id.gridview);
        adapter = new PublicGridViewAdapter(this);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    showTestDialog();
                } else {
                    startActivity(new Intent(PublicAnimActivity.this, TestActivity.class));
                    finish();
                    overridePendingTransition(0, 0);
                }

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnim();
            }
        }, 10);


        showPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (adapter != null) {
                    adapter.clear();
                }

                RotateAnimation rotate = new RotateAnimation(45f, 0f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);

                rotate.setInterpolator(new AccelerateDecelerateInterpolator());
                rotate.setDuration(duration);//设置动画持续时间
                rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
                rotate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        finish();
                        overridePendingTransition(0,R.anim.alpha_out);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                rotate.setStartOffset(50);//执行前的等待时间
                showPublic.startAnimation(rotate);
            }
        });

    }

    private void showTestDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("test")
                .setMessage("goto TestActivity")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(PublicAnimActivity.this, TestActivity.class));
                        overridePendingTransition(0, 0);

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

    private void startAnim() {
        RotateAnimation rotate = new RotateAnimation(0f, 45f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setInterpolator(new DecelerateInterpolator());
        rotate.setDuration(duration);//设置动画持续时间
        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        // rotate.setStartOffset(10);//执行前的等待时间
        showPublic.startAnimation(rotate);
    }
}
