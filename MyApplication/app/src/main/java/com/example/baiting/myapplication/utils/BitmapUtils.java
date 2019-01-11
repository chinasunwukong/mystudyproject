package com.example.baiting.myapplication.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

public class BitmapUtils {
    public static Bitmap cacheBitmap;

    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        return bitmap;
    }


    public static Bitmap photo(View view,int statusBar) {
        view.buildDrawingCache();
        Bitmap tmp = view.getDrawingCache();
        if(tmp==null) {
            tmp=convertViewToBitmap(view);
        }

        if(tmp==null) {
            return null;
        }
        int width=tmp.getWidth();
        int height=tmp.getHeight();
        Log.e("bitmap","width: "+width+", height: "+height);

        Bitmap bitmap=Bitmap.createBitmap(tmp,0,statusBar,width,height-statusBar,null,false);

        cacheBitmap=bitmap;
        return cacheBitmap;
    }

    public static Bitmap getCacheBitmap() {
        return cacheBitmap;
    }

    public static int getStatusBarHeight(Context context) {
        if(context==null) {
            return 0;
        }
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
