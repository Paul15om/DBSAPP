package pe.com.dbs.beerapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pe.com.dbs.beerapp.Models.Bar;
import pe.com.dbs.beerapp.R;


/**
 * Created by JeralBenites on 25/02/2017.
 */

public class BarAdapter extends RecyclerView.Adapter<BarAdapter.ViewHolder>{
    private List<Bar> items;

    @Override
    public BarAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(BarAdapter.ViewHolder viewHolder, int i) {
        viewHolder._Name.setText(items.get(i).get_Name());
        viewHolder._Address.setText(items.get(i).get_Address());
        viewHolder._Phone.setText(items.get(i).get_Phone());
    }



    public BarAdapter(List<Bar> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<Bar> getProducts() {
        return items;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView _Name;
        public TextView _Address;
        public TextView _Phone;

        public ViewHolder(View v) {
            super(v);
            _Name = (TextView) v.findViewById(R.id._Name);
            _Address = (TextView) v.findViewById(R.id._Address);
            _Phone = (TextView) v.findViewById(R.id._Phone);
        }
    }
}