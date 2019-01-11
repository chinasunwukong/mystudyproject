package com.experiment;


import com.experiment.bean.DataInfo;
import com.experiment.bean.PrimaryDataInfo;
import com.experiment.bean.ViewInfos;
import com.experiment.interfaces.Configuration;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * method:TextView,R.id.hello,text;
 */
public class MVVMConfiguration {


    private Configuration configuration;
    private Class[] beanClass;
    private Map<DataInfo, List<ViewInfos>> reflectMap = new HashMap<>();
    private Map<PrimaryDataInfo, List<ViewInfos>> primaryReflectMap = new HashMap<>();


    public MVVMConfiguration(Class<? extends Configuration> config) {
        try {
            configuration = config.getConstructor().newInstance();
            beanClass = configuration.getBeanClass();
            reflectMap = configuration.getRelectMap();
            primaryReflectMap = configuration.getPrimaryRelectMap();
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
        if (info == null || !reflectMap.containsKey(info)) {
            return null;
        }
        return reflectMap.get(info);
    }

    public List<ViewInfos> getViewInfosByFieldName(String fieldName) {
        for (Map.Entry<DataInfo, List<ViewInfos>> item : reflectMap.entrySet()) {
            DataInfo info = item.getKey();
            if (info.fieldName.equals(fieldName)) {
                return item.getValue();
            }
        }
        return null;
    }

    public Map<DataInfo, List<ViewInfos>> getReflectMap() {
        return reflectMap;
    }

    public Map<PrimaryDataInfo, List<ViewInfos>> getPrimaryReflectMap() {
        return primaryReflectMap;
    }

    public boolean validate(String canonicalName) {
        if (beanClass == null || beanClass.length == 0) {
            return false;
        }

        for (Class clazz : beanClass) {
            if (clazz.getCanonicalName().equals(canonicalName)) {
                return true;
            }
        }
        return false;
    }

}
