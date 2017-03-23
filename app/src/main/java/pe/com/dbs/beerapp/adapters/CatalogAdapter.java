package pe.com.dbs.beerapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;

import java.util.ArrayList;
import java.util.List;

import pe.com.dbs.beerapp.R;
import pe.com.dbs.beerapp.models.Catalog;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> {

    private List<Catalog> catalogs;
    public static ArrayList<String> objCabecera = new ArrayList<>();
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
    public void onBindViewHolder(final ViewHolder viewHolder, int index) {
        final Catalog catalog = catalogs.get(index);
        viewHolder.productName.setText(catalog.getProduct().getProductName());
        viewHolder.productPrice.setText("S/." + catalog.getUnitPrice().toString());
        viewHolder.productImageView.setImageUrl(catalog.getProduct().getImage());
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objCabecera.add(catalog.getProductId().toString());
                objCabecera.add(catalog.getUnitPrice().toString());
                objCabecera.add(viewHolder.numberProduct.getText().toString());
            }
        });
        viewHolder.upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberProduct = Integer.parseInt(viewHolder.numberProduct.getText().toString()) + 1;
                if (numberProduct == 0) {
                    viewHolder.checkBox.setChecked(false);
                    viewHolder.numberProduct.setText(numberProduct + "");
                }
                if (numberProduct < 0) {
                    viewHolder.checkBox.setChecked(false);
                    viewHolder.numberProduct.setText("0");
                }
                if (numberProduct > 0) {
                    viewHolder.checkBox.setChecked(true);
                    viewHolder.numberProduct.setText(numberProduct + "");
                    objCabecera.add(catalog.getProductId().toString());
                    objCabecera.add(catalog.getUnitPrice().toString());
                    objCabecera.add(viewHolder.numberProduct.getText().toString());
                }
            }
        });
        viewHolder.downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberProduct = Integer.parseInt(viewHolder.numberProduct.getText().toString()) - 1;
                if (numberProduct == 0) {
                    viewHolder.checkBox.setChecked(false);
                    viewHolder.numberProduct.setText(numberProduct + "");
                }
                if (numberProduct > 0) {
                    viewHolder.checkBox.setChecked(true);
                    viewHolder.numberProduct.setText(numberProduct + "");
                }
                if (numberProduct < 0) {
                    viewHolder.checkBox.setChecked(false);
                    viewHolder.numberProduct.setText("0");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return catalogs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productName;
        private TextView productPrice;
        private ANImageView productImageView;
        private CheckBox checkBox;
        private ImageButton upButton;
        private ImageButton downButton;
        private TextView numberProduct;

        public ViewHolder(View v) {
            super(v);
            productName = (TextView) v.findViewById(R.id.productName);
            productPrice = (TextView) v.findViewById(R.id.productPrice);
            productImageView = (ANImageView) v.findViewById(R.id.productImageView);
            checkBox = (CheckBox) v.findViewById(R.id.itemCheckBox);
            upButton = (ImageButton) v.findViewById(R.id.upButton);
            downButton = (ImageButton) v.findViewById(R.id.downButton);
            numberProduct = (TextView) v.findViewById(R.id.numberProduct);
        }

    }

}
