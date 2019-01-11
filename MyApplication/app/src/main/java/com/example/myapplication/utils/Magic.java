package com.example.myapplication.utils;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;


/**
 * Created by maolei on 2018/1/2.
 */

public class Magic  {

    private Activity mActivity;
    private Dialog mDialog;
    private TextView mDesTextView;
    private LinearLayout ll;
    private LinearLayout.LayoutParams lp;


    public Magic(Activity activity) {
        mActivity = activity;
        DisplayMetrics metrics=new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        lp=new LinearLayout.LayoutParams(metrics.widthPixels,
                activity.getResources().getDimensionPixelSize(R.dimen.magic_bg_height));
    }


    public void execTask() {
        handle();
    }

    private void handle() {
        initDialog();
        String title = "招聘名企红包广场";
        if(TextUtils.isEmpty(title)){
            mDesTextView.setText("即将带你直达页面");
        }else{
            int length=title.length();
            mDesTextView.setText(title.substring(0,length/2));
            mDesTextView.append("\n");
            mDesTextView.append(title.substring(length/2));
        }
        if(!mDialog.isShowing()){
            mDialog.show();
        }
    }

    private void initDialog() {
        if (mDialog == null) {
            mDialog = new Dialog(mActivity, R.style.RequestDialog);
            mDialog.setContentView(R.layout.magic_dialog);
            mDialog.setCanceledOnTouchOutside(false);


            mDialog.findViewById(R.id.img_command_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
            mDialog.findViewById(R.id.text_command_forward).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });

            ll=mDialog.findViewById(R.id.ll);
            ll.setLayoutParams(lp);


            mDesTextView = (TextView) mDialog.findViewById(R.id.command_destination);
            mDesTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
        }
    }


}