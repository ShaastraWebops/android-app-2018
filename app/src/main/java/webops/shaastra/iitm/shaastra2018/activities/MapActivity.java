package webops.shaastra.iitm.shaastra2018.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import webops.shaastra.iitm.shaastra2018.R;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    public String loc;
    public Double lat1,lng1;
    final static float INITIAL_ZOOM = 17.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent map = getIntent();
        loc = map.getStringExtra("location");
        lat1= Double.parseDouble(map.getStringExtra("lat"));
        lng1=Double.parseDouble(map.getStringExtra("lng"));        // Make use of autoboxing.  It's also easier to read.
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
        GoogleMap mMap = googleMap;

        // Add a marker on the location of the event which was passed as an extra in the intent
        LatLng eventLoc = new LatLng(lat1,lng1);
        mMap.addMarker(new MarkerOptions().position(eventLoc).title(loc));

        // Move the camera and zoom it in
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLoc,INITIAL_ZOOM));

    }
}
