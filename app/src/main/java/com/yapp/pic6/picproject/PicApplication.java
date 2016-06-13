package com.yapp.pic6.picproject;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by Administrator on 2016-01-23.
 */
public class PicApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NotoSansCJKkr-Thin.otf"));
    }


}
