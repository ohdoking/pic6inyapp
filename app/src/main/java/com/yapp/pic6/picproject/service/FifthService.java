package com.yapp.pic6.picproject.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class FifthService extends Service {

    private static final String TAG = "MyService";

    private boolean isRunning  = false;
    FifthThread mThread;

    @Override
    public IBinder onBind(Intent intent){
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onCreate(){
//        Toast.makeText(this, "���� ����", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Service onCreate");

        isRunning = true;
    }

    @Override
    public void onDestroy() {
//        Toast.makeText(this, "���� ����", Toast.LENGTH_SHORT).show();

        isRunning = false;

        Log.i(TAG, "Service onDestroy");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Toast.makeText(this, "���� ����", Toast.LENGTH_SHORT).show();

        Log.i(TAG, "Service onStartCommand");

        isRunning = true;

        mThread = new FifthThread();
        mThread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    class FifthThread extends Thread {
        public void run() {
            while (isRunning == true) {
                try {
                    Log.i(TAG, "5sec");
                    Intent serviceIntent = new Intent().setAction("com.yapp.pic6.5sec");
                    // �ֱ������� ��ε�ĳ��Ʈ �۽�
                    sendBroadcast(serviceIntent);
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}