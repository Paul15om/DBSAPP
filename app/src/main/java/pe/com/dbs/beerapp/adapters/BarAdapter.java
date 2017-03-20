package pe.com.dbs.beerapp.adapters;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import pe.com.dbs.beerapp.R;
import pe.com.dbs.beerapp.activities.CatalogActivity;
import pe.com.dbs.beerapp.constants.Constant;
import pe.com.dbs.beerapp.models.Bar;

/**
 * Created by JeralBenites on 25/02/2017.
 */
public class BarAdapter extends RecyclerView.Adapter<BarAdapter.ViewHolder> {

    private List<Bar> bars;

    public BarAdapter(List<Bar> bars) {
        this.setBars(bars);
    }

    public List<Bar> getBars() {
        return bars;
    }

    public void setBars(List<Bar> bars) {
        this.bars = bars;
    }

    @Override
    public BarAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_bar, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BarAdapter.ViewHolder viewHolder, int index) {
        final Bar bar = getBars().get(index);
        viewHolder.name.setText(bar.getName());
        viewHolder.address.setText(bar.getAddress());
        viewHolder.phone.setText(bar.getPhone());
        viewHolder.imageView.setImageUrl(bar.getImage());

        viewHolder.barCardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.BAR_ID, bar.getBarId());
                Intent intent = new Intent(v.getContext(), CatalogActivity.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return getBars().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView address;
        private TextView phone;
        private CardView barCardView;
        private ANImageView imageView;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.barName);
            address = (TextView) v.findViewById(R.id.barAddress);
            phone = (TextView) v.findViewById(R.id.barPhone);
            imageView = (ANImageView) v.findViewById(R.id.barImageView);
            barCardView = (CardView) v.findViewById(R.id.barCardView);
        }

    }

}