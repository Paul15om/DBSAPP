package pe.com.dbs.beerapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pe.com.dbs.beerapp.adapters.BarAdapter;
import pe.com.dbs.beerapp.R;
import pe.com.dbs.beerapp.models.Bar;

public class BarActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_activities);
        List items = new ArrayList();
        for (int i = 0; i < 30; i++) {
            Bar bar = new Bar();
            bar.setName("BAR " + i);
            bar.setAddress("Distrito " + i);
            bar.setPhone("Telefono " + i);
            items.add(bar);
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new BarAdapter(items);
        recyclerView.setAdapter(adapter);
    }

}
