package com.experiment;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.experiment.bean.DataInfo;
import com.experiment.bean.PrimaryDataInfo;
import com.experiment.bean.ViewInfos;
import com.experiment.interfaces.Controller;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MVVMController implements Controller {

    private Activity activity;
    private MVVMConfiguration config;
    private long start;
    private static final String TAG="MVVMController";


    private HashMap<String,Object> cacheBean=new HashMap<>();

    public MVVMController(Activity activity) {
        this.activity=activity;
    }

    @Override
    public void setState(Object data) {
        start=System.currentTimeMillis();
        if(data==null || config==null || !config.validate(data.getClass().getCanonicalName())) {
            e("data is null or data is not register.");
            d("use time: "+(System.currentTimeMillis()-start));
            return;
        }

        String classname=data.getClass().getCanonicalName();
        Object oldData=null;
        if(!cacheBean.containsKey(classname)) {
            cacheBean.put(classname,data);
        } else {
            oldData=cacheBean.get(classname);
        }

        try {
            Map<DataInfo,List<ViewInfos>> map= config.getReflectMap();
            for(Map.Entry<DataInfo,List<ViewInfos>> entry:map.entrySet()) {
                DataInfo dataInfo=entry.getKey();
                if(dataInfo==null || dataInfo.fieldName==null || dataInfo.fieldName.length()==0) {
                    continue;
                }
                List<ViewInfos> infos=entry.getValue();
                Field field=data.getClass().getField(dataInfo.fieldName);
                field.setAccessible(true);
                for(ViewInfos info:infos) {
                    if(info.viewType.equals("TextView")) {
                        TextView tv=activity.findViewById(info.resId);
                        if(info.viewMethodType.equals("text")) {
                            String text= (String) field.get(data);
                            tv.setText(text);
                        }
                    } else if(info.viewType.equals("ImageView")) {
                        ImageView img=activity.findViewById(info.resId);
                        if(info.viewMethodType.equals("imageResource")) {
                            int res= field.getInt(data);
                            img.setImageResource(res);
                        }
                    }
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        d("use time: "+(System.currentTimeMillis()-start));
    }

    @Override
    public void setConfig(MVVMConfiguration config) {
        this.config=config;
    }

    @Override
    public void setState(String key, Object value) {
        start=System.currentTimeMillis();
        if(key==null || key.length()==0 || value==null) {
            e("key is null or value is null.");
            d("use time: "+(System.currentTimeMillis()-start));
            return;
        }
        Map<PrimaryDataInfo, List<ViewInfos>> primaryMap=config.getPrimaryReflectMap();
        for(Map.Entry<PrimaryDataInfo,List<ViewInfos>> entry:primaryMap.entrySet()) {
            PrimaryDataInfo pd=entry.getKey();
            if(pd==null || pd.key==null || pd.key.length()==0) {
                continue;
            }
            d("data class:"+value.getClass().getCanonicalName());
            d("register data class: "+pd.clazz.getCanonicalName());
            if(pd.key.equals(key) && value.getClass()==pd.clazz) {
                List<ViewInfos> infos=entry.getValue();
                for(ViewInfos info:infos) {
                    if(info.viewType.equals("TextView")) {
                        TextView tv=activity.findViewById(info.resId);
                        if(info.viewMethodType.equals("visible")) {
                            tv.setVisibility(((Boolean)value)? View.VISIBLE:View.GONE);
                        }
                    } else if(info.viewType.equals("ImageView")) {
                        ImageView img=activity.findViewById(info.resId);
                        if(info.viewMethodType.equals("visible")) {
                            img.setVisibility(((Boolean)value)? View.VISIBLE:View.GONE);
                        }
                    }
                }
            }
        }
        d("use time: "+(System.currentTimeMillis()-start));
    }

    private void d(String msg) {
        if(msg==null || msg.length()==0) {
            return;
        }
        Log.d(TAG,msg);
    }

    private void e(String msg) {
        if(msg==null || msg.length()==0) {
            return;
        }
        Log.e(TAG,msg);
    }
}
