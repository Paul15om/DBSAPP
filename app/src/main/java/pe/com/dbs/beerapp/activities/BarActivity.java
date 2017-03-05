package pe.com.dbs.beerapp.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

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
                Toast.makeText(BarActivity.this, "Login error", Toast.LENGTH_LONG).show();
            }
        });
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
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id.coordinator), msg, Snackbar.LENGTH_LONG)
                .show();
    }



}