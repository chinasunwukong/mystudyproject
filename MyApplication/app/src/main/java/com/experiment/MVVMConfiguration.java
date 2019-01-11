package com.experiment;


import com.experiment.bean.DataInfo;
import com.experiment.bean.ViewInfos;
import com.experiment.interfaces.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * method:TextView,R.id.hello,text;
 *
 *
 */
public class MVVMConfiguration {


    private Configuration configuration;
    public Class[] beanClass;
    public Map<DataInfo,List<ViewInfos>> functions=new HashMap<>();

    public MVVMConfiguration(Class<? extends Configuration> config) {
        try {
            configuration=config.getConstructor().newInstance();
            beanClass=configuration.getBeanClass();
            functions=configuration.getRelectMap();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public List<ViewInfos> getViewInfos(DataInfo info) {
        if(info==null || !functions.containsKey(info))  {
            return null;
        }
        return functions.get(info);
    }

    public List<ViewInfos> getViewInfosByName(String name) {
        for(Map.Entry<DataInfo,List<ViewInfos>> item:functions.entrySet()) {
            DataInfo info=item.getKey();
            if(info.name.equals(name)) {
                return item.getValue();
            }
        }
        return null;
    }

    public boolean validate(String canonicalName) {
        if(beanClass==null || beanClass.length==0) {
            return false;
        }

        for(Class clazz:beanClass) {
            if(clazz.getCanonicalName().equals(canonicalName)) {
                return true;
            }
        }
        return true;
    }

}
