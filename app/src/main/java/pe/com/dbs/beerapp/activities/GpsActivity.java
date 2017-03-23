package pe.com.dbs.beerapp.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;
import java.util.Date;

import pe.com.dbs.beerapp.R;
import pe.com.dbs.beerapp.constants.Constant;

public class GpsActivity extends FragmentActivity
        implements OnMapReadyCallback
        //, GoogleApiClient.ConnectionCallbacks
        //, GoogleApiClient.OnConnectionFailedListener
        //, LocationListener
        //
{

    private Location mLocation;
    // private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    private GoogleApiClient mGoogleApiClient;
    final String TAG = "RegisterActivity";
    private GoogleMap mMap;
    private double mlatitude, mlongitude;
    private double mMylatitude, mMylongitude;
    private String mName;
    private ImageView skyImage;
    private LocationManager locationManager;

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
        //  mGoogleApiClient = new GoogleApiClient.Builder(this)
        //      .addConnectionCallbacks(this)
        //     .addOnConnectionFailedListener(this)
        //     .addApi(LocationServices.API)
        //    .build();

        // checkLocation();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.setBuildingsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng mPosition = new LatLng(mlatitude, mlongitude);
        //   LatLng mMyPosition = new LatLng(mMylatitude, mMylongitude);
        mMap.addMarker(
                new MarkerOptions()
                        .position(mPosition)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_beer))
                        .title(mName));
        // mMap.addMarker(
        //       new MarkerOptions()
        //             .position(mMyPosition)
        //            .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_beer))
        //           .title(mName));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mPosition)
                .zoom(15.5f)
                .bearing(300)
                .tilt(50)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void getTimeFromAndroid() {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        if (hours >= 1 && hours <= 12) {
            Bitmap bImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.skymor);
            skyImage.setImageBitmap(bImage);
        } else if (hours >= 12 && hours <= 16) {
            Bitmap bImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.skyaft);
            skyImage.setImageBitmap(bImage);
        } else if (hours >= 16 && hours <= 21) {
            Bitmap bImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.skynight);
            skyImage.setImageBitmap(bImage);
        } else if (hours >= 21 && hours <= 24) {
            Bitmap bImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.skynight);
            skyImage.setImageBitmap(bImage);
        }
    }

    //  @Override
    // public void onConnected(Bundle bundle) {
    //    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
    // return;
    //}

    //startLocationUpdates();

    // mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

    // if (mLocation == null) {
    //     startLocationUpdates();
    // }
    // if (mLocation != null) {

    //   mMylatitude = mLocation.getLatitude();
    //   mMylongitude = mLocation.getLongitude();
    // } else {
    //     Toast.makeText(this, "Ubicacio no Detectada", Toast.LENGTH_SHORT).show();
    // }
    // }

    // @Override
    // public void onConnectionSuspended(int i) {
    //     Log.i(TAG, "Conecion Suspendida");
    //     mGoogleApiClient.connect();
    // }

    // @Override
    // public void onConnectionFailed(ConnectionResult connectionResult) {
    //     Log.i(TAG, "Fallo la Coneccion Error:" + connectionResult.getErrorCode());
    // }

    // @Override
    // protected void onStart() {
    //      super.onStart();
    //     if (mGoogleApiClient != null) {
    //         mGoogleApiClient.connect();
    //     }
    // }

    // @Override
    // protected void onStop() {
    //   super.onStop();
    //   if (mGoogleApiClient.isConnected()) {
    //      mGoogleApiClient.disconnect();
    //  }
    // }

    // protected void startLocationUpdates() {
        // Create the location request
    //    mLocationRequest = LocationRequest.create()
    //         .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
    //        .setInterval(UPDATE_INTERVAL)
    //       .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
    // if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
    //  return;
    // }
    // LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
    //          mLocationRequest, this);
    //  Log.d("reque", "--->>>>");
    //}

    // @Override
    //  public void onLocationChanged(Location location) {
    //    String msg = "Updated Location: " +
    //            Double.toString(location.getLatitude()) + "," +
    //           Double.toString(location.getLongitude());
    //   mMylatitude = location.getLatitude();
    //   mMylongitude = location.getLongitude();
    // Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
    //  LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    //   }

    //private boolean checkLocation() {
    //     if (!isLocationEnabled())
    //        showAlert();
    //     return isLocationEnabled();
    // }

    // private void showAlert() {
    // final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    // dialog.setTitle("habilitar Gps")
    //        .setMessage(" Tu Ubicacion esta Inactiva")
    //     .setPositiveButton("Ir a Ajustes Gps", new DialogInterface.OnClickListener() {
    //@Override
    //   public void onClick(DialogInterface paramDialogInterface, int paramInt) {

    //            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    //            startActivity(myIntent);
    //        }
    //  })
    //  .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
    //     @Override
    //     public void onClick(DialogInterface paramDialogInterface, int paramInt) {

    //     }
    //   });
    // dialog.show();
    //  }

    // private boolean isLocationEnabled() {
    //     locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    //    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
    //            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    //}
}
