package com.example.baiting.myapplication.bean;


import com.example.baiting.myapplication.R;
import com.experiment.bean.DataInfo;
import com.experiment.bean.PrimaryDataInfo;
import com.experiment.bean.ViewInfos;
import com.experiment.interfaces.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MVVMActivityConfiguration implements Configuration {

    private Map<DataInfo, List<ViewInfos>> map;
    private Map<PrimaryDataInfo,List<ViewInfos>> primaryMap;

    public MVVMActivityConfiguration() {
        map=new HashMap<>();
        primaryMap=new HashMap<>();
    }


    @Override
    public Class[] getBeanClass() {
        return new Class[]{UserInfo.class};
    }

    @Override
    public Map<DataInfo, List<ViewInfos>> getRelectMap() {

        List<ViewInfos> usernameInfos=new ArrayList<>();
        usernameInfos.add(new ViewInfos("TextView",
                R.id.txt_showname,"text"));
        map.put(new DataInfo("username","string"),
                usernameInfos);

        List<ViewInfos> imgResInfos=new ArrayList<>();
        imgResInfos.add(new ViewInfos("ImageView",
                R.id.img_show,"imageResource"));
        map.put(new  DataInfo("imgRes","int"),
                imgResInfos);

        return map;
    }

    @Override
    public Map<PrimaryDataInfo, List<ViewInfos>> getPrimaryRelectMap() {
        PrimaryDataInfo visibleData=new PrimaryDataInfo("flag",Boolean.class);
        List<ViewInfos> textInfoLists=new ArrayList<>();
        textInfoLists.add(new ViewInfos("TextView",
                R.id.txt_showname,"visible"));
        textInfoLists.add(new ViewInfos("ImageView",
                R.id.img_show,"visible"));

        primaryMap.put(visibleData,textInfoLists);
        return primaryMap;
    }


}
