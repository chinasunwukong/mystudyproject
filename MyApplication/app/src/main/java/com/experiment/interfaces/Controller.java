package com.experiment.interfaces;


import com.experiment.MVVMConfiguration;

public interface Controller {

    void setState(Object obj);

    void setConfig(MVVMConfiguration config);

    void setState(String key,Object value);
}
