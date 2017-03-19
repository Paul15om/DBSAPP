package pe.com.dbs.beerapp.activities;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import pe.com.dbs.beerapp.R;
import pe.com.dbs.beerapp.adapters.BarAdapter;
import pe.com.dbs.beerapp.models.Bar;
import pe.com.dbs.beerapp.service.BarService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BarActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        setToolbar();
        BarService barService = retrofit.create(BarService.class);
        Call<List<Bar>> call = barService.findAll();
        call.enqueue(new Callback<List<Bar>>() {

            @Override
            public void onResponse(Call<List<Bar>> call, Response<List<Bar>> response) {
                RecyclerView recycler = (RecyclerView) findViewById(R.id.barRecycler);
                recycler.setHasFixedSize(true);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BarActivity.this);
                recycler.setLayoutManager(layoutManager);

                RecyclerView.Adapter adapter = new BarAdapter(response.body());
                recycler.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Bar>> call, Throwable t) {
                Toast.makeText(BarActivity.this, "Bar error", Toast.LENGTH_LONG).show();
            }
        });
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion localization = new Localizacion();
        localization.setMainActivity(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
                localization);

        showSnackBar("Localizacion agregada");
        showSnackBar("");

    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                showSnackBar(newText + " Text Change");
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                showSnackBar(query + " Clic Envio");
                return true;
            }
        };
        assert searchView != null;
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onCreateOptionsMenu(menu);
    }

    private void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id.coordinator), msg, Snackbar.LENGTH_LONG)
                .show();
    }

    private void setLocation(Location loc) {
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    showSnackBar("Mi direccion es: \n"
                            + DirCalle.getAddressLine(0));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class Localizacion implements LocationListener {
        BarActivity mainActivity;

        public BarActivity getMainActivity() {
            return mainActivity;
        }

        void setMainActivity(BarActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            loc.getLatitude();
            loc.getLongitude();
            String Text = "Mi ubicacion actual es: " + "\n Lat = "
                    + loc.getLatitude() + "\n Long = " + loc.getLongitude();
            showSnackBar(Text);
            this.mainActivity.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            showSnackBar("GPS Desactivado");
        }

        @Override
        public void onProviderEnabled(String provider) {
            showSnackBar("GPS Activado");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }

}
