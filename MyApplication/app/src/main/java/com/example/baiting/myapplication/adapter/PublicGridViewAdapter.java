package com.example.baiting.myapplication.adapter;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.baiting.myapplication.R;


public class PublicGridViewAdapter extends BaseAdapter {

    private Context context;

    private int[] res={R.mipmap.car,R.mipmap.goods,R.mipmap.location};

    private boolean isDelete=false;

    public PublicGridViewAdapter(Context context) {
        this.context=context;

    }

    public void clear() {
        isDelete=true;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 15;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.item_public,null);

        ImageView iv=convertView.findViewById(R.id.img);

        iv.setImageResource(res[position%3]);
        convertView.clearAnimation();
        convertView.startAnimation(AnimationUtils.loadAnimation(context,R.anim.item_list_anim));
        if(isDelete) {
            anim(convertView);
        }

        return convertView;
    }

    private void anim(View target) {
        target.clearAnimation();

        PropertyValuesHolder rotateX=PropertyValuesHolder.ofFloat("scaleX",1.0f,0.9f);
        PropertyValuesHolder rotateY=PropertyValuesHolder.ofFloat("scaleY",1.0f,0.9f);
        PropertyValuesHolder alpha=PropertyValuesHolder.ofFloat("alpha",1.0f,0);

        ObjectAnimator animator=ObjectAnimator.ofPropertyValuesHolder(target,rotateX,rotateY,alpha);

        animator.setDuration(200);

        animator.setInterpolator(new LinearInterpolator());
        animator.start();

    }
}
