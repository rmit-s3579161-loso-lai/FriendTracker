package com.example.loso.friendtracker.View;

/**
 * Created by Loso on 2017/8/19.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.loso.friendtracker.Controller.FriendController;
import com.example.loso.friendtracker.Model.Friend;
import com.example.loso.friendtracker.Model.Location;
import com.example.loso.friendtracker.R;
import com.example.loso.friendtracker.Service.LocationService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class Tab_Map extends Fragment implements OnMapReadyCallback {
    MapView mMapView;
    View mView;
    private GoogleMap mMap;
    private LocationService locationService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        locationService = new LocationService(getActivity());
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.tab_map, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.map);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        MapsInitializer.initialize((getContext()));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        Location current = locationService.getCurrentLocation();
        LatLng here = new LatLng(current.getLatitude(), current.getLongitude());

        // Add a marker in Sydney and move the camera
        LatLng rmit = new LatLng(-37.809427, 144.963727);
        mMap.addMarker(new MarkerOptions().position(here).title("Current Location"));

        FriendController fc = new FriendController();
        ArrayList<Friend> friendList = fc.getFriendsList();

        for (Friend friend : friendList) {
            Location floc = friend.getLocation();
            if (floc != null) {
                LatLng friendloc = new LatLng(floc.getLatitude(), floc.getLongitude());
                mMap.addMarker(new MarkerOptions().position(friendloc)
                        .title(friend.getName())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(here));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));

    }
}
