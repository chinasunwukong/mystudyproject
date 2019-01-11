package com.example.baiting.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.BindView;
import com.example.baiting.myapplication.utils.BitmapUtils;
import com.example.baiting.myapplication.utils.InjectHelper;
import com.example.baiting.myapplication.utils.Magic;


public class MainActivity extends AppCompatActivity {


    @BindView(R.id.test)
    TextView tv;

    @BindView(R.id.test_html)
    TextView test_html;


    private String html="北京西北旺家庭<font color='red'>保洁</font>开荒保洁<font color='blue'>擦玻璃</font>小时工来电优惠北旺家北旺家北旺家北旺家北旺<font color='red'>家北旺</font>家北旺家北旺家北旺家北旺家北旺家北<font color='red'>旺家北旺</font>家北旺家北旺家";

    private Magic magic;
    private MainActivity instance;
    private Handler mHandler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InjectHelper.inject(this);

        instance=this;

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(instance,TestActivity.class));
                overridePendingTransition(R.anim.alpha_in,0);
            }
        });

        test_html.setText(Html.fromHtml(html));


        findViewById(R.id.goto_public).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View decorView = getWindow().getDecorView();
                Bitmap bitmap = BitmapUtils.photo(decorView,
                        BitmapUtils.getStatusBarHeight(instance));
                Log.e("bitmap", "byte count: " + bitmap.getByteCount());
                startActivity(new Intent(instance,PublicAnimActivity.class));
            }
        });

        findViewById(R.id.magic_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(magic==null) {
                    magic=new Magic(instance);
                }

                magic.execTask();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                },1000);
            }
        });

        findViewById(R.id.dynamic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(instance,DynamicLinearLayoutActivity.class));
            }
        });

        findViewById(R.id.publish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(instance,PublishActivity.class));
                overridePendingTransition(0,0);
            }
        });

        findViewById(R.id.mvvm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(instance,MVVMActivity.class));
            }
        });
    }
}
