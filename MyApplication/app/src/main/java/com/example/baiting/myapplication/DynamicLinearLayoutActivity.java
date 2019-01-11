package com.example.baiting.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class DynamicLinearLayoutActivity extends Activity {

    private LinearLayout ll;
    private int[] value = {2, 3, 4};
    private int index = -1;

    private LinearLayout.LayoutParams lp1;
    private LinearLayout.LayoutParams lp2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_ll);

        lp1=new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT);
        lp1.weight=1;
        lp2=new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT);
        lp2.weight=2;


        ll = findViewById(R.id.ll);
        findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
            }
        });

        loadData();
    }

    private void change() {
        loadData();
    }

    private void resizeWeight(int flag) {
        ImageView img1 = (ImageView) ll.getChildAt(0);
        ImageView img2 = (ImageView) ll.getChildAt(1);
        switch (flag) {
            case 2: {
                img1.setLayoutParams(lp1);
                img1.setImageResource(R.mipmap.little_ad);
                img2.setImageResource(R.mipmap.little_ad);
                break;
            }
            case 3: {
                img1.setLayoutParams(lp2);
                img1.setImageResource(R.mipmap.little_ad);

                img2.setImageResource(R.mipmap.little_ad);
                ImageView img3 = (ImageView) ll.getChildAt(2);
                img3.setImageResource(R.mipmap.little_ad);
                break;
            }
            case 4: {
                img1.setLayoutParams(lp1);
                img1.setImageResource(R.mipmap.ic_launcher);
                img2.setImageResource(R.mipmap.ic_launcher);

                ImageView img3 = (ImageView) ll.getChildAt(2);
                ImageView img4 = (ImageView) ll.getChildAt(3);
                img3.setImageResource(R.mipmap.ic_launcher);
                img4.setImageResource(R.mipmap.ic_launcher);
                break;
            }
        }

    }



    private void loadData() {
        index = (index + 1) % 3;
        int len = value[index];
        int count = ll.getChildCount();
        resizeWeight(len);
        ImageView img;
        for (int i = 0; i < count; i++) {
            img = (ImageView) ll.getChildAt(i);
            if (i < len) {
                if (img.getVisibility() != View.VISIBLE) {
                    img.setVisibility(View.VISIBLE);
                }
            } else {
                if (img.getVisibility() != View.GONE) {
                    img.setVisibility(View.GONE);
                }
            }
        }

    }
}
