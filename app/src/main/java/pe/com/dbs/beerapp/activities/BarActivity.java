package pe.com.dbs.beerapp.activities;

import android.app.SearchManager;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.orm.SugarRecord;

import java.util.List;

import pe.com.dbs.beerapp.R;
import pe.com.dbs.beerapp.adapters.BarAdapter;
import pe.com.dbs.beerapp.models.Bar;
import pe.com.dbs.beerapp.services.BarService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BarActivity extends AbstractActivity {

    protected LocationManager locationManager;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    private List<Bar> bars;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        setToolbar();
        loadBars();
        searchBar("", 0);
    }


    public String Estado(boolean value) {
        return String.valueOf(value);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void loadBars() {
        BarService barService = retrofit.create(BarService.class);
        Call<List<Bar>> call = barService.findAll();
        call.enqueue(new Callback<List<Bar>>() {
            @Override
            public void onResponse(Call<List<Bar>> call, Response<List<Bar>> response) {
                SugarRecord.deleteAll(Bar.class);
                bars = response.body();
                SugarRecord.saveInTx(bars);
            }
            @Override
            public void onFailure(Call<List<Bar>> call, Throwable t) {

            }
        });
    }

    private void searchBar(String Text, int state) {
        List<Bar> Query = null;
        if (state == 1) {
            Log.d("LogsAndroid", "Mensaje de depuración1");
            Query = SugarRecord.findWithQuery(Bar.class, "Select * from Bar where name like '%" + Text + "%'");
        } else if (state != 1) {
            Log.d("LogsAndroid", "Mensaje de depuración2");
            Query = SugarRecord.listAll(Bar.class);
        }
        RecyclerView recycler = (RecyclerView) findViewById(R.id.barRecycler);
        recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BarActivity.this);
        recycler.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new BarAdapter(Query);
        recycler.setAdapter(adapter);
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
                searchBar(newText, 1);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchBar(query, 1);
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Location:
                showSnackBar("Tu Posicion es 777");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
          /*  List<Bar> asdasd1 = SugarRecord.listAll(Bar.class);

            List<Bar> asdasd2 = Bar.listAll(Bar.class);

            List<Bar> SASDD = Bar.find(Bar.class, "id = ?", "232");

            Bar asdasd = Bar.find(Bar.class, "id = ?", "232").get(0);*/


}
