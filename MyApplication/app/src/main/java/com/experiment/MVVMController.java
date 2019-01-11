package com.experiment;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.experiment.bean.DataInfo;
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


    private HashMap<String,Object> cacheBean=new HashMap<>();

    public MVVMController(Activity activity) {
        this.activity=activity;
    }

    @Override
    public void setState(Object data) {
        start=System.currentTimeMillis();
        if(data==null || config==null || !config.validate(data.getClass().getCanonicalName())) {
            Log.e("mvvm","use time: "+(System.currentTimeMillis()-start));
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
            Map<DataInfo,List<ViewInfos>> map= config.functions;
            for(Map.Entry<DataInfo,List<ViewInfos>> entry:map.entrySet()) {
                DataInfo dataInfo=entry.getKey();
                if(dataInfo==null || dataInfo.name==null || dataInfo.name.length()==0) {
                    continue;
                }
                List<ViewInfos> infos=entry.getValue();
                Field field=data.getClass().getField(dataInfo.name);
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
        Log.e("mvvm","use time: "+(System.currentTimeMillis()-start));
    }

    @Override
    public void setConfig(MVVMConfiguration config) {
        this.config=config;
    }
}
