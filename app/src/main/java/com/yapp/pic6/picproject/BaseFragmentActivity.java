package com.yapp.pic6.picproject;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by mac004 on 2016. 6. 30..
 */
public class BaseFragmentActivity extends FragmentActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}