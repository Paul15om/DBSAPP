package pe.com.dbs.beerapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

}