package pe.com.dbs.beerapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import pe.com.dbs.beerapp.R;
import pe.com.dbs.beerapp.adapters.CatalogAdapter;
import pe.com.dbs.beerapp.constants.Constant;
import pe.com.dbs.beerapp.models.Catalog;
import pe.com.dbs.beerapp.services.CatalogService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CatalogActivity extends AbstractActivity {

    private CatalogAdapter catalogAdapter;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        number = bundle.getString(Constant.NUMBER);
        CatalogService catalogService = getRetrofit().create(CatalogService.class);
        Call<List<Catalog>> call = catalogService.findByBar(bundle.getInt(Constant.BAR_ID));
        call.enqueue(new Callback<List<Catalog>>() {

            @Override
            public void onResponse(Call<List<Catalog>> call, Response<List<Catalog>> response) {
                RecyclerView recycler = (RecyclerView) findViewById(R.id.catalogRecycler);
                recycler.setHasFixedSize(true);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CatalogActivity.this);
                recycler.setLayoutManager(layoutManager);

                catalogAdapter = new CatalogAdapter(response.body());
                recycler.setAdapter(catalogAdapter);
            }

            @Override
            public void onFailure(Call<List<Catalog>> call, Throwable t) {
                Toast.makeText(CatalogActivity.this, "Catalog error", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.order:
                if (Objects.equals(total(), "0.0")) {
                    showSnackBar(getString(R.string.title_alert_product_Negative));
                } else {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle(getString(R.string.title_alert_product))
                            .setMessage(getString(R.string.text_product) + total())
                            .setPositiveButton(getString(R.string.opcion1), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    dialContactPhone(number);
                                }
                            })
                            .setNegativeButton(getString(R.string.opcion2), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    paramDialogInterface.dismiss();
                                }
                            });
                    dialog.show();
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    private String total() {
        double total = 0.0;

        for (int i = 0; i < catalogAdapter.catalogs.size(); i++) {
            if (catalogAdapter.catalogs.get(i).getCantidad() > 0) {
                total += catalogAdapter.catalogs.get(i).getUnitPrice();
            }
        }
        return String.valueOf(total);
    }

    private void showSnackBar(String msg) {
        Snackbar.make(findViewById(R.id.coordinator), msg, Snackbar.LENGTH_LONG)
                .show();
    }
}