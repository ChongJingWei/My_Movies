package sg.edu.rp.c346.id22024709.mymovies;

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
    ArrayList<Movie> movieList;

    public CustomAdapter(Context context, int resource, ArrayList<Movie> objects) {
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        movieList = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) parent_context.
            getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    View rowView = inflater.inflate(layout_id, parent, false);

    TextView tvShowTitle = rowView.findViewById(R.id.tvShowTitle);
    TextView tvShowGenre = rowView.findViewById(R.id.tvShowGenre);
    TextView tvShowYear = rowView.findViewById(R.id.tvShowYear);
    ImageView ivRating= rowView.findViewById(R.id.ivRating);

    Movie currentItem = movieList.get(position);
        tvShowTitle.setText(currentItem.getTitle());
        tvShowGenre.setText("+" + currentItem.getGenre());
        tvShowYear .setText(currentItem.getYear() + "");

        String g = "https://imgur.com/I8waFSD.jpg";
        String m18 = "https://imgur.com/xheQ60O.jpg";
        String nc16 = "https://imgur.com/kwgQcyL.jpg";
        String pg = "https://imgur.com/uWQg6as.jpg";
        String pg13 = "https://imgur.com/LRmrSen.jpg";
        String r21 = "https://imgur.com/Le9wkfa.jpg";



        if (currentItem.getRating().equals("G")) {
            Picasso.with(parent_context).load(g).into(ivRating);
        } else if (currentItem.getRating().equals("M18")) {
            Picasso.with(parent_context).load(m18).into(ivRating);
        } else if (currentItem.getRating().equals("NC16")) {
            Picasso.with(parent_context).load(nc16).into(ivRating);
        } else if (currentItem.getRating().equals("PG")) {
            Picasso.with(parent_context).load(pg).into(ivRating);
        } else if (currentItem.getRating().equals("PG13")) {
            Picasso.with(parent_context).load(pg13).into(ivRating);
        } else if (currentItem.getRating().equals("R21")) {
            Picasso.with(parent_context).load(r21).into(ivRating);
        } else {
            Picasso.with(parent_context).load(g).into(ivRating);
        }
            return rowView;

}
}
