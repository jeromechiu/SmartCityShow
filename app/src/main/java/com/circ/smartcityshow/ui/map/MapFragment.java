package com.circ.smartcityshow.ui.map;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.circ.smartcityshow.MainActivity;
import com.circ.smartcityshow.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public  class MapFragment extends Fragment implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = MainActivity.class.getName();

    MapView mapView;
    GoogleMap map;
    public static final int REQUEST_LOCATION = 99;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    LatLng latLng;
    SupportMapFragment mFragment;
    Marker currLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = root.findViewById(R.id.map_view);

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

    @Override
    @SuppressLint("MissingPermission")
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (map == null) {
            Log.d(TAG, "map is Null");
            return;
        } else {
            Log.i(TAG, String.format("map: %s", map));

            Log.d(TAG, "fefefe");
            if (ActivityCompat.checkSelfPermission(getContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(),
                            android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Detect No Permission, to grant");
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION);
            } else {
                Log.d(TAG, "PERMISSION GRANTED");
            }

            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
            map.getUiSettings().setAllGesturesEnabled(true);
            configLocationRequest();

        }

    }

    private void configLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    private void refreshMapPosition(LatLng pos, float angle) {
        CameraPosition.Builder positionBuilder = new CameraPosition.Builder();
        positionBuilder.target( pos );
        positionBuilder.zoom( 15f );
        positionBuilder.bearing( angle );
        positionBuilder.tilt( 60 );
        map.animateCamera( CameraUpdateFactory.newCameraPosition( positionBuilder.build() ) );
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    public void onStart() {
        super.onStart();
        // Initiating the connection
        googleApiClient.connect();
    }
    public void onStop() {
        super.onStop();
        // Disconnecting the connection
        googleApiClient.disconnect();
    }
    //Callback invoked once the GoogleApiClient is connected successfully
    @Override
    public void onConnected(Bundle bundle) {
        //Fetching the last known location using the FusedLocationProviderApi

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient );
        Log.d(TAG, String.format("Get Current Position: %.5f %.5f", mLastLocation.getLatitude(), mLastLocation.getLongitude()));
        if (mLastLocation != null) {
            //place marker at current position
            //mGoogleMap.clear();
            latLng = new LatLng( mLastLocation.getLatitude(), mLastLocation.getLongitude() );
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position( latLng );
            markerOptions.title( "Current Position" );
            markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_MAGENTA ) );
            currLocationMarker = map.addMarker( markerOptions );
            refreshMapPosition(latLng,0);

        }
    }
    @Override
    public void onConnectionSuspended(int i) {
    }
    //Callback invoked if the GoogleApiClient connection fails
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

}
