package com.circ.smartcityshow;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyLMSInstanceService extends FirebaseMessagingService{
    private static final String TAG = "MyLMSInstanceService";
    public MyLMSInstanceService() {
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("onMessageReceived", "onMessageReceived = " + remoteMessage.getNotification().getBody());
    }


}
