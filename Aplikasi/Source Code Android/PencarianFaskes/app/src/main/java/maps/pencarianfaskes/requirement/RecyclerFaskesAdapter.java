package maps.pencarianfaskes.requirement;

/**
 * Created by RedLyst on 03/05/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maps.pencarianfaskes.R;
import maps.pencarianfaskes.main.jalur;

public class RecyclerFaskesAdapter extends RecyclerView.Adapter<RecyclerFaskesAdapter.ViewHolder> {

    private Context context;
    private List<AllFaskes> allLaundries;

    public RecyclerFaskesAdapter(Context context, List<AllFaskes> allLaundries) {
        this.context = context;
        this.allLaundries = allLaundries;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_faskes, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AllFaskes allFaskes = allLaundries.get(position);
        holder.textViewId.setText(allFaskes.getId());
        holder.textViewNama.setText(allFaskes.getNama());
        holder.textViewAlamat.setText(allFaskes.getAlamat());
        holder.textViewLat.setText(allFaskes.getLatitude());
        holder.textViewLongi.setText(allFaskes.getLongitude());
    }

    @Override
    public int getItemCount() {
        return allLaundries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.textId) TextView textViewId;
        @BindView(R.id.textNama) TextView textViewNama;
        @BindView(R.id.textAlamat) TextView textViewAlamat;
        @BindView(R.id.textLat) TextView textViewLat;
        @BindView(R.id.textLongi) TextView textViewLongi;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String id = textViewId.getText().toString();
            String nama = textViewNama.getText().toString();
            String alamat = textViewAlamat.getText().toString();
            String lat = textViewLat.getText().toString();
            String longi = textViewLongi.getText().toString();

            Intent i = new Intent(context, jalur.class);

            Bundle b = new Bundle();
            b.putString("id", id);

            b.putString("nama", nama);
            b.putString("alamat", alamat);


            b.putString("lat", lat);
            b.putString("lng", longi);

            i.putExtra("parse", b);

            context.startActivity(i);
        }
    }
}
