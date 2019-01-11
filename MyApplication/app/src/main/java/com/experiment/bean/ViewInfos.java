package com.experiment.bean;

public  class ViewInfos {
    public String viewType;
    public int resId;
    public String viewMethodType;

    public ViewInfos(String viewType,int resId,String viewMethodType) {
        this.viewMethodType=viewMethodType;
        this.resId=resId;
        this.viewType=viewType;
    }
}