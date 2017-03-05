package pe.com.dbs.beerapp.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import pe.com.dbs.beerapp.R;
import pe.com.dbs.beerapp.adapters.CatalogAdapter;
import pe.com.dbs.beerapp.constants.Constant;
import pe.com.dbs.beerapp.models.Catalog;
import pe.com.dbs.beerapp.service.CatalogService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatalogActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        Bundle bundle = getIntent().getExtras();
        CatalogService catalogService = retrofit.create(CatalogService.class);
        Call<List<Catalog>> call = catalogService.findByBar(bundle.getInt(Constant.BAR_ID));

        call.enqueue(new Callback<List<Catalog>>() {

            @Override
            public void onResponse(Call<List<Catalog>> call, Response<List<Catalog>> response) {
                RecyclerView recycler = (RecyclerView) findViewById(R.id.catalogRecycler);
                recycler.setHasFixedSize(true);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CatalogActivity.this);
                recycler.setLayoutManager(layoutManager);

                RecyclerView.Adapter adapter = new CatalogAdapter(response.body());
                recycler.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Catalog>> call, Throwable t) {
                Toast.makeText(CatalogActivity.this, "Login error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
