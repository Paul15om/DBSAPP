package pe.com.dbs.beerapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
        for (int i = 0; i < 30; i++) {
            items.add(new Bar("BAR " + i, "Distrito " + i, "Telefono " + i));

        }
        _recycler = (RecyclerView) findViewById(R.id.recycler);
        _recycler.setHasFixedSize(true);

        _lManager = new LinearLayoutManager(this);
        _recycler.setLayoutManager(_lManager);

        _adapter = new BarAdapter(items);
        _recycler.setAdapter(_adapter);
    }

}
