package pe.com.dbs.beerapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import pe.com.dbs.beerapp.Adapters.BarAdapter;
import pe.com.dbs.beerapp.Models.Bar;
import pe.com.dbs.beerapp.R;

public class BarActivities extends AppCompatActivity {
    private RecyclerView _recycler;
    private RecyclerView.Adapter _adapter;
    private RecyclerView.LayoutManager _lManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_activities);
        List items = new ArrayList();

        items.add(new Bar("Cajacho", "Mangomerca", "12345678"));
        items.add(new Bar("Bigote", "28 JULIO", "487"));
        items.add(new Bar("GRINGA", "MERCA", "451"));
        items.add(new Bar("Cajacho", "Mangomerca", "12345678"));
        items.add(new Bar("Bigote", "28 JULIO", "487"));
        items.add(new Bar("GRINGA", "MERCA", "451"));
        items.add(new Bar("Cajacho", "Mangomerca", "12345678"));
        items.add(new Bar("Bigote", "28 JULIO", "487"));
        items.add(new Bar("GRINGA", "MERCA", "451"));
        items.add(new Bar("Cajacho", "Mangomerca", "12345678"));
        items.add(new Bar("Bigote", "28 JULIO", "487"));
        items.add(new Bar("GRINGA", "MERCA", "451"));
        items.add(new Bar("Cajacho", "Mangomerca", "12345678"));
        items.add(new Bar("Bigote", "28 JULIO", "487"));
        items.add(new Bar("GRINGA", "MERCA", "451"));

        _recycler = (RecyclerView) findViewById(R.id.recycler);
        _recycler.setHasFixedSize(true);

        _lManager = new LinearLayoutManager(this);
        _recycler.setLayoutManager(_lManager);

        _adapter = new BarAdapter(items);
        _recycler.setAdapter(_adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar, menu);
        return true;
    }
}
