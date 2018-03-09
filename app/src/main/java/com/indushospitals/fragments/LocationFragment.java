package com.indushospitals.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;
import com.indushospitals.databinding.FragmentLocationBinding;
import com.indushospitals.model.LocationIndus;
import com.indushospitals.utils.ConnectivityReceiver;
import com.sdsmdg.tastytoast.TastyToast;



/**
 * Created by think360 on 20/04/17.
 */

public class LocationFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener {

    private FragmentLocationBinding binding;
    private Location mLastLocation;
    private GoogleMap googleMap = null;
    private boolean isMapReady = false;
    private GoogleApiClient mGoogleApiClient;
    private int id = 0;
    protected LocationRequest locationRequest;

    public LocationFragment() {
        // Required empty public constructor
    }

    public static LocationFragment newInstance() {
        return  new LocationFragment();
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_location, container, false);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(MoreActivity.self)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            locationRequest = LocationRequest.create();
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            // **************************
            builder.setAlwaysShow(true); // this is the key ingredient
            // **************************

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
           result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result
                            .getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can
                            // initialize location
                            // requests here.

                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be
                            // fixed by showing the user
                            // a dialog.
                            try {
                                // Show the dialog by calling
                                // startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(MoreActivity.self, 1000);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have
                            // no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            });
            binding.mapview.onCreate(savedInstanceState);
            binding.mapview.getMapAsync(this);
        }

        binding.ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             binding.llAddress.setVisibility(View.GONE);
            }
        });

        return binding.getRoot();
    }


    @Override
    public void onMapReady(GoogleMap map) {
        this.googleMap = map;
        this.isMapReady = true;

    }

    @Override
    public void onResume() {
        super.onResume();

        binding.mapview.onResume();
    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
        binding.mapview.onStart();
    }

    @Override
    public void onStop() {

        binding.mapview.onStop();
        super.onStop();

    }

    @Override
    public void onPause() {
        super.onPause();
        binding.mapview.onPause();
    }

    @Override
    public void onDestroy() {
        binding.mapview.onDestroy();

        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapview.onLowMemory();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {


        if (ActivityCompat.checkSelfPermission(MoreActivity.self, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


        if (mLastLocation != null) {
            if (isMapReady && googleMap != null) {
                googleMap.setMyLocationEnabled(true);
                cameraZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), "");

                                for (int i = 0; i < ContactUsFragment. mPackagesListl.size(); i++) {
                                    LocationIndus location = ContactUsFragment.mPackagesListl.get(i);
                                    googleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLat(), location.getLang())).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(MoreActivity.self.getResources(),
                                            R.mipmap.location)))).setTag(i);//.title("Marker")
                                }

                                googleMap.setOnMarkerClickListener(LocationFragment.this);
                            }


                        binding.tvName.setText(ContactUsFragment.mPackagesListl.get(0).getTitle());
                        binding.tvAddress.setText(ContactUsFragment.mPackagesListl.get(0).getAddress());
                        binding.btnGetDirections.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(ConnectivityReceiver.isConnected()){
                                    Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr=" + mLastLocation.getLatitude() + "," + mLastLocation.getLongitude() + "&daddr=" + ContactUsFragment. mPackagesListl.get(id).getLat() + "," + ContactUsFragment.mPackagesListl.get(id).getLang() + "&mode=driving");
                                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                    mapIntent.setPackage("com.google.android.apps.maps");
                                    mapIntent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                                    startActivity(mapIntent);
                                }else{
                                    TastyToast.makeText(MoreActivity.self, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                }
                            }
                        });

        }

    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


    }


    void cameraZoom(LatLng latLng, String placeName) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 8);
        googleMap.animateCamera(cameraUpdate);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (!("" + marker.getTag()).equals("Current location")) {

            LocationIndus location = ContactUsFragment. mPackagesListl.get(Integer.parseInt(marker.getTag() + ""));//here Tag represents id
            id = (int) marker.getTag();
            binding.llAddress.setVisibility(View.VISIBLE);
            Animation bottom_up = AnimationUtils.loadAnimation(MoreActivity.self, R.anim.bottom_up);
            binding.llAddress.startAnimation(bottom_up);
            binding.tvName.setText(location.getTitle());
            binding.tvAddress.setText(location.getAddress());

            return false;
        } else {
            TastyToast.makeText(MoreActivity.self, "" + marker.getTag(), TastyToast.LENGTH_SHORT, TastyToast.INFO);
            return false;
        }

    }


}



