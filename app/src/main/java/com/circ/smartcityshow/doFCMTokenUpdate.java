package com.circ.smartcityshow;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import cz.msebera.android.httpclient.Header;

public class doFCMTokenUpdate {
    private static final String TAG = "doFCMTokenUpdate";
    private static Context context;

    public void doPost(String... params) {


        JSONObject data = new JSONObject();
        JSONObject json = new JSONObject();
        String content = new String();

        try {
            data.put( "name", "Jerome" );
            data.put( "active", "True" );
            data.put( "user", "Jerome" );
            data.put( "device_id", params[1] );
            data.put( "registration_id", params[2] );
            data.put( "type", "android" );
            json.put( "msgid", UUID.randomUUID().toString() );
            json.put( "data", data );
            content = String.valueOf( json );
            Log.d( TAG, "content:" + content );


        } catch (Exception e) {
            Log.d( TAG, e.toString() );
        }
        URL url = null;
        try {
            url = new URL( params[0] );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d( TAG, "send Toekn to " + url );

        UpdateFCMToken_aynchttpClient.post( context, url, content, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d( TAG, "response: " + response );
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable e) {
                Log.d( TAG, "error code: " + statusCode );
//                String jsonString = "";
//                if ( statusCode == 400 ) {
//                    jsonString = errorResponse;
//                    Log.d( TAG,"onFailure_400: ", jsonString );
//                }else if(statusCode == 500){
//                    jsonString = response;
//                    Log.d( TAG,"onFailure_500: ", jsonString );

//                }
            }


//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
//                // Pull out the first event on the public timeline
//                JSONObject firstEvent = timeline.get(0);
//                String tweetText = firstEvent.getString("text");
//
//                // Do something with the response
//                System.out.println(tweetText);
//            }
        } );
    }
}
