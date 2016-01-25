package com.yapp.pic6.picproject.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class DeviceBootReceiver extends BroadcastReceiver {


    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            /* Setting the alarm here */
            if(getPreferences().isEmpty() || getPreferences().equals("ON")){
                context.startService(new Intent(context, FifthService.class));
            }

        }
    }

    //      값 불러오기
    private String getPreferences() {
        SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        return pref.getString("popup", "");
    }
}