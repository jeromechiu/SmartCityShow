package com.circ.smartcityshow;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyLMSInstanceService extends FirebaseMessagingService{
    private static final String TAG = "MyLMSInstanceService";
    public MyLMSInstanceService() {
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "onMessageReceived = " + remoteMessage.getNotification().getBody());

        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title").toString();
        String message = data.get("body").toString();
        Log.d(TAG, "onMessageReceived = " + title+message);

    }


}
