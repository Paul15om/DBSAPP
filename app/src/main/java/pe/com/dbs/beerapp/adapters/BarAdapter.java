package pe.com.dbs.beerapp.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;

import java.util.List;

import pe.com.dbs.beerapp.R;
import pe.com.dbs.beerapp.activities.CatalogActivity;
import pe.com.dbs.beerapp.activities.GpsActivity;
import pe.com.dbs.beerapp.constants.Constant;
import pe.com.dbs.beerapp.models.Bar;

public class BarAdapter extends RecyclerView.Adapter<BarAdapter.ViewHolder> {

    private List<Bar> bars;

    public BarAdapter(List<Bar> bars) {
        this.setBars(bars);
    }

    private List<Bar> getBars() {
        return bars;
    }

    private void setBars(List<Bar> bars) {
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
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.BAR_ID, bar.getBarId());
                bundle.putString(Constant.NUMBER, bar.getPhone());
                Intent intent = new Intent(v.getContext(), CatalogActivity.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }

        });
        viewHolder.gpsBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putDouble(Constant.LATITUDE, Double.parseDouble(bar.getLatitude().toString()));
                bundle.putDouble(Constant.LONGITUDE, Double.parseDouble(bar.getLongitude().toString()));
                bundle.putString(Constant.BARNAME, bar.getName());
                Intent intent = new Intent(v.getContext(), GpsActivity.class);
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

        private final TextView name;
        private final TextView address;
        private final TextView phone;
        private final ANImageView imageView;
        private final ImageView gpsBar;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.barName);
            address = (TextView) v.findViewById(R.id.barAddress);
            phone = (TextView) v.findViewById(R.id.barPhone);
            imageView = (ANImageView) v.findViewById(R.id.barImageView);
            gpsBar = (ImageView) v.findViewById(R.id.gpsFromBar);
        }

    }

}