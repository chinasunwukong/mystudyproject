package com.experiment;

import android.app.Activity;
import com.experiment.interfaces.Controller;

public class SpringMVVM {

    public static Controller getController(Activity activity) {
        if(activity==null || activity.isFinishing()) {
            return null;
        }

        Controller controller=new MVVMController(activity);
        EnableConfiguration enable=activity.getClass().getAnnotation(EnableConfiguration.class);
        if(enable==null) {
            return null;
        }
        MVVMConfiguration config=new MVVMConfiguration(enable.config());

        controller.setConfig(config);

        return controller;

    }
}
