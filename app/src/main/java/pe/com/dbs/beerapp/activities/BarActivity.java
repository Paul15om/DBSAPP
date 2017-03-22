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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pe.com.dbs.beerapp.R;
import pe.com.dbs.beerapp.adapters.BarAdapter;
import pe.com.dbs.beerapp.models.Bar;


public class BarActivity extends AbstractActivity {

    private List<Bar> bars;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        setToolbar();
        List<Bar> barList = Bar.listAll(Bar.class);

                RecyclerView recycler = (RecyclerView) findViewById(R.id.barRecycler);
                recycler.setHasFixedSize(true);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BarActivity.this);
                recycler.setLayoutManager(layoutManager);

        adapter = new BarAdapter(barList);
                recycler.setAdapter(adapter);


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

            @Override
            public boolean onQueryTextChange(String newText) {
                filterBarsAndChangeDataset(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                filterBarsAndChangeDataset(query);
                return true;
            }

            private void filterBarsAndChangeDataset(String text) {
                List<Bar> barsFiltered = new ArrayList<>();
                for (Bar bar : bars) {
                    if (bar.getName().toLowerCase().contains(text.toLowerCase())) {
                        barsFiltered.add(bar);
                    }
                }

                ((BarAdapter) adapter).setBars(barsFiltered);
                adapter.notifyDataSetChanged();

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

    private void setLocation(Location location) {
        if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(
                        location.getLatitude(), location.getLongitude(), 1);
                if (!addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    showSnackBar("Mi direccion es: \n" + address.getAddressLine(0));
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
        public void onLocationChanged(Location location) {
            location.getLatitude();
            location.getLongitude();
            String text = "Mi ubicacion actual es: " + "\n Lat = "
                    + location.getLatitude() + "\n Long = " + location.getLongitude();
            showSnackBar(text);
            this.mainActivity.setLocation(location);
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


          /*  List<Bar> asdasd1 = SugarRecord.listAll(Bar.class);

            List<Bar> asdasd2 = Bar.listAll(Bar.class);

            List<Bar> SASDD = Bar.find(Bar.class, "id = ?", "232");

            Bar asdasd = Bar.find(Bar.class, "id = ?", "232").get(0);*/
        }

    }

}
