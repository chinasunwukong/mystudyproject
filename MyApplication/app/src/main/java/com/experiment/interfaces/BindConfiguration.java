package com.experiment.interfaces;

import android.app.Activity;

public interface BindConfiguration<T> {

    void bindViewByObject(Activity activity, T data);

    void bindViewByPrimary(Activity activity,Object object);
}
