package com.experiment.interfaces;


import com.experiment.bean.DataInfo;
import com.experiment.bean.PrimaryDataInfo;
import com.experiment.bean.ViewInfos;

import java.util.List;
import java.util.Map;

public interface Configuration {

    Class[] getBeanClass();

    Map<DataInfo,List<ViewInfos>> getRelectMap();

    Map<PrimaryDataInfo,List<ViewInfos>> getPrimaryRelectMap();

    void bindClick(ViewInfos infos);

}
