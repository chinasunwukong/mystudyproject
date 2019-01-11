package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView tv=new TextView(this);
        tv.setText("hello test");
        setContentView(tv);

        overridePendingTransition(R.anim.activity_open_enter,R.anim.activity_open_exit);
    }
}
