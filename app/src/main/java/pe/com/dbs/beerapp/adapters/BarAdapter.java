package pe.com.dbs.beerapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pe.com.dbs.beerapp.R;
import pe.com.dbs.beerapp.models.Bar;


public class BarAdapter extends RecyclerView.Adapter<BarAdapter.ViewHolder>{
    private List<Bar> items;

    @Override
    public BarAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card, viewGroup, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(BarAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mName.setText(items.get(i).getmName());
        viewHolder.mAddress.setText(items.get(i).getmAddress());
        viewHolder.mPhone.setText(items.get(i).getmPhone());
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


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mName;
        TextView mAddress;
        TextView mPhone;

        ViewHolder(View v) {
            super(v);
            mName = (TextView) v.findViewById(R.id.mName);
            mAddress = (TextView) v.findViewById(R.id.mAddress);
            mPhone = (TextView) v.findViewById(R.id.mPhone);
        }
    }
}