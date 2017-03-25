package pe.com.dbs.beerapp.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import pe.com.dbs.beerapp.DirectionsJSONParser;
import pe.com.dbs.beerapp.R;
import pe.com.dbs.beerapp.TrackGPS;
import pe.com.dbs.beerapp.constants.Constant;

public class GpsActivity extends FragmentActivity
        implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TrackGPS gps;
    private double mlatitude, mlongitude;
    private double mMylatitude, mMylongitude;
    private String mName;
    private ImageView skyImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        skyImage = (ImageView) findViewById(R.id.skyTheme);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bundle b = getIntent().getExtras();
        mlatitude = b.getDouble(Constant.LATITUDE);
        mlongitude = b.getDouble(Constant.LONGITUDE);
        mName = b.getString(Constant.BARNAME);
        getTimeFromAndroid();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        checkLocation();
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.setBuildingsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng mPosition = new LatLng(mlatitude, mlongitude);
        LatLng mMyPosition = new LatLng(mMylatitude, mMylongitude);
        mMap.addMarker(
                new MarkerOptions()
                        .position(mPosition)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_beer))
                        .title(mName));
        mMap.addMarker(
                new MarkerOptions()
                        .position(mMyPosition)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_person)));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mMyPosition)
                .zoom(15.6f)
                .bearing(90)
                .tilt(60)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        String url = getDirectionsUrl(mMyPosition, mPosition);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
    }

    private void getTimeFromAndroid() {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        int hours = c.get(Calendar.HOUR_OF_DAY);
        if (hours >= 5 && hours <= 12) {
            Bitmap bImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.skymor);
            skyImage.setImageBitmap(bImage);
        } else if (hours >= 12 && hours <= 17) {
            Bitmap bImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.skyaft);
            skyImage.setImageBitmap(bImage);
        } else if (hours >= 17 && hours <= 24) {
            Bitmap bImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.skynight);
            skyImage.setImageBitmap(bImage);
        } else if (hours >= 0 && hours <= 5) {
            Bitmap bImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.skynight);
            skyImage.setImageBitmap(bImage);
        }
    }

    private void checkLocation() {
        gps = new TrackGPS(GpsActivity.this);
        if (gps.canGetLocation()) {
            mMylongitude = gps.getLongitude();
            mMylatitude = gps.getLatitude();
        } else {
            gps.showSettingsAlert();
        }
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gps.stopUsingGPS();
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String parameters = str_origin + "&" + str_dest + "&" + Constant.SENSOR;
        return "https://maps.googleapis.com/maps/api/directions/" + Constant.OUTPUT + "?" + parameters;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        HttpURLConnection urlConnection;
        URL url = new URL(strUrl);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.connect();
        try (InputStream iStream = urlConnection.getInputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        } finally {
            urlConnection.disconnect();
        }
        return data;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = result.get(i);
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                lineOptions.addAll(points);
                lineOptions.width(7);
                lineOptions.color(Color.RED);
            }
            mMap.addPolyline(lineOptions);
        }
    }
}
