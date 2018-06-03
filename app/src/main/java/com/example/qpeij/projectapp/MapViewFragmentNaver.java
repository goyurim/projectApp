package com.example.qpeij.projectapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

/**
 * Created by SAMSUNG on 2018-06-03.
 */

public class MapViewFragmentNaver extends NMapFragment implements NMapView.OnMapStateChangeListener, NMapPOIdataOverlay.OnStateChangeListener {
    NMapView mapView;
    NMapController mapController;
    NMapViewerResourceProvider mapViewerResourceProvider;
    NMapOverlayManager mapOverlayManager;
    NMapPOIdata poidata;
    int markerId = NMapPOIflagType.PIN;
    SiteActivity siteActivity;
    double longitude, latitude;
    String key;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_mapview, container, false);
        mapView = (NMapView) v.findViewById(R.id.map_view);
        mapView.setClientId("yMxM1_6BoFVYF8jXenYu");
        mapView.setClickable(true);

        return v;

    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.setBuiltInZoomControls(true, null);
        mapView.setOnMapStateChangeListener(this);
        mapView.setScalingFactor(3.0f);
        mapController = mapView.getMapController();
        mapViewerResourceProvider = new NMapViewerResourceProvider(getActivity());
        mapOverlayManager = new NMapOverlayManager(getActivity(), mapView, mapViewerResourceProvider);
        siteActivity = (SiteActivity) getActivity();

        key = siteActivity.key;
        longitude = siteActivity.longitude;
        latitude = siteActivity.latitude;
        moveMapCenter();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {
        if (nMapError == null) {
            moveMapCenter();
        } else {
            Log.e("map init error", nMapError.message);
        }

    }

    private void moveMapCenter() {

        NGeoPoint currentPoint = new NGeoPoint(longitude, latitude);
        mapController.setMapCenter(currentPoint);

        poidata = new NMapPOIdata(1, mapViewerResourceProvider);
        poidata.beginPOIdata(1);

        Log.d("log","받은"+latitude);
        Log.d("log","받은" +longitude);
        poidata.addPOIitem(longitude, latitude, key, markerId, 11);
        poidata.endPOIdata();

        NMapPOIdataOverlay poiDataOverlay = mapOverlayManager.createPOIdataOverlay(poidata, null);

    }

    @Override
    public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {

    }

    @Override
    public void onMapCenterChangeFine(NMapView nMapView) {

    }

    @Override
    public void onZoomLevelChange(NMapView nMapView, int i) {

    }

    @Override
    public void onAnimationStateChange(NMapView nMapView, int i, int i1) {

    }

    @Override
    public void onFocusChanged(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {

    }

    @Override
    public void onCalloutClick(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {

    }
}
