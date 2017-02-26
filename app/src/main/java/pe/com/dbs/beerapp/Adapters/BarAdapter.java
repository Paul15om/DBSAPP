package pe.com.dbs.beerapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pe.com.dbs.beerapp.R;
import pe.com.dbs.beerapp.models.Bar;


/**
 * Created by JeralBenites on 25/02/2017.
 */
public class BarAdapter extends RecyclerView.Adapter<BarAdapter.ViewHolder>{

    private List<Bar> bars;

    public BarAdapter(List<Bar> bars) {
        this.bars = bars;
    }

    public List<Bar> getBars() {
        return bars;
    }

    @Override
    public BarAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card, viewGroup, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(BarAdapter.ViewHolder viewHolder, int i) {
        Bar bar = bars.get(i);
        viewHolder.name.setText(bar.getName());
        viewHolder.address.setText(bar.getAddress());
        viewHolder.phone.setText(bar.getPhone());
    }

    @Override
    public int getItemCount() {
        return bars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView address;
        public TextView phone;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id._Name);
            address = (TextView) v.findViewById(R.id._Address);
            phone = (TextView) v.findViewById(R.id._Phone);
        }

    }

}