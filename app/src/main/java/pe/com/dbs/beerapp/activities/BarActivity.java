package pe.com.dbs.beerapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import pe.com.dbs.beerapp.R;
import pe.com.dbs.beerapp.models.Bar;

public class BarActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_activities);
        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        */
        List<Bar> bars = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            bars.add(new Bar("BAR " + i, "Distrito " + i, "Telefono " + i));

        }
        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);

        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        adapter = new pe.com.dbs.beerapp.adapters.BarAdapter(bars);
        recycler.setAdapter(adapter);
    }

}



