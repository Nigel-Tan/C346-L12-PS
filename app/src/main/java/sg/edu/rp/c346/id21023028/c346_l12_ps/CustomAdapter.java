package sg.edu.rp.c346.id21023028.c346_l12_ps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Movie> versionList;

    public CustomAdapter(Context context, int resource, ArrayList<Movie> objects){
        super (context, resource, objects);
        parent_context = context;
        layout_id = resource;
        versionList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView tvName = rowView.findViewById(R.id.textViewName);
        ImageView ivRating = rowView.findViewById(R.id.imageRating);
        TextView tvGenre = rowView.findViewById(R.id.textViewGenre);
        TextView tvYear = rowView.findViewById(R.id.textViewYear);

        // Obtain the Android Version information based on the position
        Movie currentVersion = versionList.get(position);

        // Set values to the TextView to display the corresponding information
        tvName.setText(currentVersion.getTitle());
        Picasso.with(parent_context).load(currentVersion.urlRating()).into(ivRating);
        tvYear.setText(String.valueOf(currentVersion.getYear()));
        tvGenre.setText(currentVersion.getGenre());

        return rowView;
    }

}
