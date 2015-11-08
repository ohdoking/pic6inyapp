package com.yapp.pic6.picproject.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class DeviceBootReceiver extends BroadcastReceiver {


    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            /* Setting the alarm here */
            String value  = "OFF";
            if(getPreferences().isEmpty() || getPreferences().equals("ON")){
                value = "ON";
                context.startService(new Intent(context, FifthService.class));
            }
            Toast.makeText(context,"pic on state : " + value,Toast.LENGTH_LONG).show();

        }
    }

    // 값 불러오기
    private String getPreferences() {
        SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        return pref.getString("popup", "");
    }
}