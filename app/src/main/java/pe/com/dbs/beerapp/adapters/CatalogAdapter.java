package pe.com.dbs.beerapp.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;

import java.util.List;

import pe.com.dbs.beerapp.R;
import pe.com.dbs.beerapp.models.Catalog;

/**
 * Created by jalvarea on 05/03/2017.
 */

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> {

    private List<Catalog> catalogs;

    public CatalogAdapter(List<Catalog> catalogs) {
        this.catalogs = catalogs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_catalog, viewGroup, false);

        return new CatalogAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int index) {
        Catalog catalog = catalogs.get(index);
        viewHolder.productName.setText(catalog.getProduct().getProductName());
        viewHolder.productPrice.setText(catalog.getUnitPrice().toString());
        viewHolder.productImageView.setImageUrl(catalog.getProduct().getImage());
    }

    @Override
    public int getItemCount() {
        return catalogs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productName;
        private TextView productPrice;
        private CardView catalogCardView;
        private ANImageView productImageView;

        public ViewHolder(View v) {
            super(v);
            productName = (TextView) v.findViewById(R.id.productName);
            productPrice = (TextView) v.findViewById(R.id.productPrice);
            catalogCardView = (CardView) v.findViewById(R.id.catalogCardView);
            productImageView = (ANImageView) v.findViewById(R.id.productImageView);
        }

    }

}
