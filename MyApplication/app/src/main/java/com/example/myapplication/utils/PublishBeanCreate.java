package com.example.myapplication.utils;



import com.example.myapplication.R;
import com.example.myapplication.bean.HomePublishBean;
import com.example.myapplication.bean.PublishBean;

import java.util.ArrayList;

public class PublishBeanCreate {

    private static int size = 0;

    private static final int[] icons = {
            R.mipmap.publish_icon_bendifuwu,
            R.mipmap.publish_icon_car,
            R.mipmap.publish_icon_house,
            R.mipmap.publish_icon_jianli,
            R.mipmap.publish_icon_jiaoyu,
            R.mipmap.publish_icon_pets,
            R.mipmap.publish_icon_piaowu,
            R.mipmap.publish_icon_pinche,
            R.mipmap.publish_icon_qichefw,
            R.mipmap.publish_icon_qiuzhuzixun,
            R.mipmap.publish_icon_qiuzuqiugouqiufuwu,
            R.mipmap.publish_icon_sale,
            R.mipmap.publish_icon_shangwu,
            R.mipmap.publish_icon_shenghuo,
            R.mipmap.publish_icon_xiangqinjiaoyou,
            R.mipmap.publish_icon_xunrenxunwu,
            R.mipmap.publish_icon_xunrenxunwu,
            R.mipmap.publish_icon_xunrenxunwu,
            R.mipmap.publish_icon_xunrenxunwu,
            R.mipmap.publish_icon_zhaopin
    };

    public static ArrayList<PublishBean> create() {
        ArrayList<PublishBean> publishBeans = new ArrayList<PublishBean>();
        size++;
        PublishBean publishBean;
        ArrayList<HomePublishBean> beans = null;
        for (int i = 0; i < icons.length; i++) {
            HomePublishBean homePublishBean = new HomePublishBean();
            homePublishBean.setListName(icons[i]);
            homePublishBean.setName("test" + i);

            if (i % 3 == 0) {
                beans = new ArrayList<>();
                publishBean = new PublishBean();
                publishBean.setBeans(beans);
                publishBeans.add(publishBean);
            }
            beans.add(homePublishBean);
        }

        size = size% icons.length;
        return publishBeans;
    }
}
