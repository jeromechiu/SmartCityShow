package com.circ.smartcityshow;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class UpdateFCMToken extends AsyncTask<String, Integer, String> {
    private static final String TAG = "UpdateFCMToken";

    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        int code = 0;
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL( params[0] );
            Log.d( TAG, "send Toekn to " + url );
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty( "Content-Type", "application/json" );
//            connection.setRequestProperty( "Accept", "application/json" );
            connection.setRequestMethod( "POST" );
//            connection.connect();


            connection.setDoOutput( true );
            connection.setUseCaches( false );
            connection.setConnectTimeout( 5000 );
            connection.setReadTimeout( 5000 );
            OutputStream os = connection.getOutputStream();
            DataOutputStream writer = new DataOutputStream( os );
            JSONObject data = new JSONObject();
            JSONObject json = new JSONObject();
            try {
                data.put( "name", "Jerome" );
                data.put( "active", "True" );
                data.put( "user", "Jerome" );
                data.put( "device_id", params[1] );
                data.put( "registration_id", params[2] );
                data.put( "type", "android" );
                json.put( "msgid", UUID.randomUUID().toString() );
                json.put( "data", data );
                String content = String.valueOf( json );
                Log.d( TAG, "content:" + content );


            } catch (Exception e) {
                Log.d( TAG, e.toString() );
            }

            writer.writeBytes( String.valueOf( json ) );
            writer.flush();
            writer.close();
            os.close();

            code = connection.getResponseCode();
            InputStream is = connection.getInputStream();

            BufferedReader reader = new BufferedReader( new InputStreamReader( is ) );
            String line;

            while ((line = reader.readLine()) != null) {
                response.append( line );
                response.append( '\r' );
            }
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        Log.d( TAG, "response code: " + code );
        return Integer.toString( code );
    }


    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(String result) {
        // this is executed on the main thread after the process is over
        // update your UI here
//        displayMessage(result);
    }
}

