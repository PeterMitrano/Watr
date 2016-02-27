package watr.hack.watr;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class Main extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback,
        AsyncRequester.RequesterListener {

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.createReportButton);
        fab.setOnClickListener(this);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        AsyncRequester requester = new AsyncRequester();
        requester.addListener(this);
        requester.execute();
    }

    @Override
    public void onCompleted(List<Report> reports) {
        for (Report report : reports) {
            Log.e("lat", "" + report.latitude);
            Log.e("lat", "" + report.longitude);
            LatLng latLng = new LatLng(report.latitude, report.longitude);
            map.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Marker"));
        }
    }
}
