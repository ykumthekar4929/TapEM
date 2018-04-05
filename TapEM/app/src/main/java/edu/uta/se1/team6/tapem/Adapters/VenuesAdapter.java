package edu.uta.se1.team6.tapem.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import edu.uta.se1.team6.tapem.Models.VenueModel;
import edu.uta.se1.team6.tapem.R;

/**
 * Created by yashodhan on 3/25/18.
 */

public class VenuesAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflter;
    List<VenueModel> venueModels;

    public VenuesAdapter(Context context, List<VenueModel> venueModels) {
        this.context = context;
        this.venueModels = venueModels;
        inflter = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return venueModels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.spinner_row, null);
        TextView title = view.findViewById(R.id.title);
        TextView subtitle = view.findViewById(R.id.subtitle);
        title.setText(venueModels.get(position).getName());
        if (position != 0) {
            subtitle.setText("Capacity: " + venueModels.get(position).getCapacity());
        } else {
            subtitle.setText("");
        }
        return view;
    }
}
