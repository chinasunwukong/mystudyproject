package com.example.baiting.myapplication.adapter;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baiting.myapplication.R;
import com.example.baiting.myapplication.bean.HomePublishBean;
import com.example.baiting.myapplication.bean.PublishBean;
import com.example.baiting.myapplication.utils.WubaPersistentUtils;

import java.util.ArrayList;


public class HomePublishAdapter extends BaseAdapter {
    ArrayList<PublishBean> publishBeans = new ArrayList<PublishBean>();
    private LayoutInflater mInflater;
    private Context mContext;
    private View mButtonCall;
    private TextView mTextTitle;
    private TextView mTextPhone;
    private static final int ITEM_NORMAL = 0;
    private static final int ITEM_PHONE = 1;
    private static final int ITEM_CUSTOMER_BAR = 2;
    private static final int ITEM_TOTAL_SIZE = 3;
    //TODO-lxm 添加两项的位置
    private int mItemCustomerPos;
    private int mItemPopPos;
    private boolean isClose = false;
    private SparseBooleanArray spa;
    private int maxSize = 0;

    private int limit = 0;


    public HomePublishAdapter(Context context) {
        super();
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
        spa = new SparseBooleanArray();
    }

    public void setLimitSize(int size) {
        limit = size;
    }

    public void setPublishBeans(ArrayList<PublishBean> publishBeans) {
        if (publishBeans == null || publishBeans.size() == 0) {
            return;
        }
        this.publishBeans = publishBeans;

        int size = publishBeans.size();
        int location = -1;
        for (int i = 0; i < size; i++) {
            PublishBean publishBean = publishBeans.get(i);
            ArrayList<HomePublishBean> bean = publishBean.getBeans();
            int childSize = bean.size();
            for (int j = 0; j < childSize; j++) {
                location++;
                spa.put(location, false);
            }
        }

        notifyDataSetChanged();
    }

    public void close() {
        isClose = true;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        int count = publishBeans.size();
        if (WubaPersistentUtils.getShowMyPopu()) {
            mItemPopPos = count;
            count = count + 1;
        }
        if (WubaPersistentUtils.getShowCustomerBar()) {
            mItemCustomerPos = count;
            count = count + 1;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < publishBeans.size()) {
            return ITEM_NORMAL;
        }
        //TODO-lxm 添加客服栏
        else if ((position == mItemPopPos) && WubaPersistentUtils.getShowMyPopu()) {
            return ITEM_PHONE;
        } else if ((position == mItemCustomerPos) && WubaPersistentUtils.getShowCustomerBar()) {
            return ITEM_CUSTOMER_BAR;
        }
        return ITEM_NORMAL;
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_TOTAL_SIZE;
    }

    @Override
    public Object getItem(int position) {
        return publishBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        //日志测试用，正式删除
        switch (type) {
            case ITEM_NORMAL:
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.home_publish_ltem_layout, parent,
                            false);
                }
                initItemLayout(convertView, position);
                break;
            case ITEM_PHONE:
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.home_publish_popularization_layout, parent,
                            false);
                }
                initPopularizationLayout(convertView);
                break;
            //TODO-lxm
            case ITEM_CUSTOMER_BAR:
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.home_publish_customer_layout, parent, false);
                }
                initCustomerLayout(convertView);
                break;
        }

        return convertView;
    }

    private void initPopularizationLayout(View convertView) {
        convertView.findViewById(R.id.popu_view).setVisibility(View.VISIBLE);
        mButtonCall = convertView.findViewById(R.id.popu_phone_call);

        mTextTitle = (TextView) convertView.findViewById(R.id.popu_title);
        mTextPhone = (TextView) convertView.findViewById(R.id.popu_phone);

    }

    private void initCustomerLayout(View convertView) {

    }

    private void initItemLayout(View convertView, int position) {
        PublishBean publishBean = publishBeans.get(position);
        ArrayList<HomePublishBean> publishBeans = publishBean.getBeans();
        int size = publishBeans.size();
        if (maxSize < size) {
            maxSize = size;
        }
        for (int i = 0; i < size; i++) {
            if (size != maxSize) {
                bindView(convertView, publishBeans.get(i), i, position , maxSize);
            } else {
                bindView(convertView, publishBeans.get(i), i, position , size);
            }
        }

        if (size == 1) {//隐藏item中后两个view
            convertView.findViewById(R.id.publish_item_two).setVisibility(View.INVISIBLE);
            convertView.findViewById(R.id.publish_item_three).setVisibility(View.INVISIBLE);
        } else if (size == 2) {//隐藏item中最后一个view
            convertView.findViewById(R.id.publish_item_three).setVisibility(View.INVISIBLE);
        }
    }


    void bindView(View convertView, final HomePublishBean publishBean, int pos, int position,int size) {
        ImageView imageView = null;
        TextView textView = null;
        View childView = null;
        switch (pos) {
            case 0:
                childView = convertView.findViewById(R.id.publish_item_one);
                if (childView != null) {
                    imageView = childView.findViewById(R.id.publish_item_one_image);
                    textView = childView.findViewById(R.id.publish_item_one_text);
                }
                break;
            case 1:
                childView = convertView.findViewById(R.id.publish_item_two);
                if (childView != null) {
                    imageView = childView.findViewById(R.id.publish_item_two_image);
                    textView = childView.findViewById(R.id.publish_item_two_text);
                }
                break;
            case 2:
                childView = convertView.findViewById(R.id.publish_item_three);
                if (childView != null) {
                    imageView = childView.findViewById(R.id.publish_item_three_image);
                    textView = childView.findViewById(R.id.publish_item_three_text);
                }
                break;
        }
        if (childView == null) {
            return;
        }
        childView.setVisibility(View.VISIBLE);
        showImage(publishBean, imageView);

        textView.setText(publishBean.getName());
        // add anim
        if (!spa.get(pos + position*size) && position<limit) {
            spa.put(pos + position*size, true);
            childView.clearAnimation();
            childView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.item_list_anim));
        }

        // close anim
        if (isClose) {
            closeAnim(childView);
        }

    }


    private void closeAnim(View target) {
        target.clearAnimation();

        PropertyValuesHolder rotateX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.9f);
        PropertyValuesHolder rotateY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.9f);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(target, alpha, rotateX, rotateY);

        animator.setDuration(200);

        animator.setInterpolator(new LinearInterpolator());
        animator.start();

    }

    private void showImage(HomePublishBean publishBean, ImageView imageView) {
        // 从内置资源读取
        int resId = publishBean.getListName();
        if (resId > 0) {
            imageView.setImageResource(resId);
        }

    }


}
