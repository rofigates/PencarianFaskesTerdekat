package maps.pencarianfaskes.control;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import maps.pencarianfaskes.R;


/**
 * Created by badawi on 7/27/2016.
 */
public class AutoAdapter extends ArrayAdapter<AutoItems> {
    private final List<AutoItems> Autolist;
    private List<AutoItems> filteredAuto = new ArrayList<>();

    public AutoAdapter(Context context, List<AutoItems> AutoList) {
        super(context, 0, AutoList);
        this.Autolist = AutoList;
    }

    @Override
    public int getCount() {
        return filteredAuto.size();
    }

    @Override
    public Filter getFilter() {
        return new filter(this, Autolist);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item from filtered list.

        AutoItems items = filteredAuto.get(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.auto_content, parent, false);

        TextView id = (TextView) convertView.findViewById(R.id.id_auto);
        TextView name = (TextView) convertView.findViewById(R.id.name_auto);
        id.setText(items.getId());
        name.setText(items.getName());

        return convertView;
    }

    class filter extends Filter {

        AutoAdapter adapter;
        List<AutoItems> originalList;
        List<AutoItems> filteredList;

        public filter(AutoAdapter adapter, List<AutoItems> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = originalList;
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                // Your filtering logic goes in here
                for (final AutoItems items : originalList) {
                    if (items.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(items);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.filteredAuto.clear();
            adapter.filteredAuto.addAll((List) results.values);
            adapter.notifyDataSetChanged();
        }
    }


}
