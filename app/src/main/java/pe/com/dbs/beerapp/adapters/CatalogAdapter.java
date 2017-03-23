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
                int numberProduct = Integer.parseInt(viewHolder.numberProduct.getText().toString());
                onClickCheckBox(1, numberProduct, viewHolder.checkBox, viewHolder.numberProduct);
            }
        });
        viewHolder.upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberProduct = Integer.parseInt(viewHolder.numberProduct.getText().toString()) + 1;
                onClickCheckBox(0, numberProduct, viewHolder.checkBox, viewHolder.numberProduct);


            }
        });
        viewHolder.downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberProduct = Integer.parseInt(viewHolder.numberProduct.getText().toString()) - 1;
                onClickCheckBox(0, numberProduct, viewHolder.checkBox, viewHolder.numberProduct);

            }
        });
    }

    public void onClickCheckBox(int tipo, int Number, CheckBox checkBox, TextView numberProduct) {
        if (tipo == 0) {
            if (Number == 0) {
                checkBox.setChecked(false);
                numberProduct.setText(Number + "");
            }
            if (Number > 0) {
                checkBox.setChecked(true);
                numberProduct.setText(Number + "");
            }
            if (Number < 0) {
                checkBox.setChecked(false);
                numberProduct.setText("0");
            }
        } else if (tipo == 1) {
            if (checkBox.isChecked()) {
                if (Number == 0) {
                    numberProduct.setText("1");
                }
            } else if (!checkBox.isChecked()) {
                numberProduct.setText("0");
            }
        }
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
