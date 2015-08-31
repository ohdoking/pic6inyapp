package com.yapp.pic6.picproject.service;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.yapp.pic6.picproject.DialogMainActivity;

import java.util.List;

public class MyBroadcastReceiver extends BroadcastReceiver {

    final String TAG = "LOGTAG";
    GalleryHelper gh;
    //Camera camera;

    @Override
    public void onReceive (Context context, Intent intent) { // 10초 내에 수행되어야 함
        gh = new GalleryHelper(context);
        boolean temp = isRunningProcess(context, "");
        boolean isEmptyNewPic = gh.newPicture().isEmpty();
//        isActivity()
        Log.i(TAG, "ohdoking" + temp+"//"+String.valueOf(isEmptyNewPic));
        if (!temp
                && !isEmptyNewPic
                && !isActivity(context,DialogMainActivity.class)) {
            Intent dialogIntent = new Intent(context, DialogMainActivity.class);
            dialogIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pi = PendingIntent.getActivity(context, 0, dialogIntent, PendingIntent.FLAG_ONE_SHOT);
            try {
                pi.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
//        Toast.makeText(context, "" + temp, Toast.LENGTH_LONG).show();
        /*try {
        // 수신되면 호출
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pi = PendingIntent.getActivity(context, 0, cameraIntent, PendingIntent.FLAG_ONE_SHOT);
            Toast.makeText(context, "카메라 사용 아님", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "카메라 사용 중", Toast.LENGTH_SHORT).show();
        }*//*
        try {
            camera.open();
            Log.i(TAG, "카메라 오픈 시도");
            Toast.makeText(context, "카메라 사용 아님", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.i(TAG, "카메라 오픈 시도 실패");
            Toast.makeText(context, "카메라 사용 중", Toast.LENGTH_SHORT).show();
        }
        */
    }

    public static boolean isRunningProcess(Context context, String packageName) {

        boolean isRunning = false;

        ActivityManager actMng = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> list = actMng.getRunningAppProcesses();

        for(ActivityManager.RunningAppProcessInfo rap : list)
        {
            if(rap.processName.contains("camera"))
            {
                isRunning = true;
                break;
            } else {
                isRunning = false;
                break;
            }
        }

        return isRunning;
    }

    public static boolean isActivity(Context context, Class<?> cls){
        if(cls == null) return false;

        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> info = activityManager.getRunningTasks(1);
        ActivityManager.RunningTaskInfo running = info.get(0);
        ComponentName componentName = running.topActivity;

        return cls.getName().equals(componentName.getClassName());
    }
}