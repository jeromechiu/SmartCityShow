package com.circ.smartcityshow;


import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URL;

import cz.msebera.android.httpclient.entity.StringEntity;

public class UpdateFCMToken_aynchttpClient {
    private static final String TAG = "UpdateFCMToken_aynchttpClient";
//    private static final String BASE_URL = "https://api.twitter.com/1/";

    private static AsyncHttpClient client = new AsyncHttpClient();


//    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//        client.get(url, params, responseHandler);
//    }

    public static void post(Context context, URL url, String content, AsyncHttpResponseHandler responseHandler) {
//        client.addHeader("Accept", "application/json");
//        client.addHeader("Content-Type", "application/json");
        StringEntity entity = null;
        try {
            entity = new StringEntity( content );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client.post( context, String.valueOf( url ), entity, "application/json", responseHandler );
    }

}