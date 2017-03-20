package com.netflix.prototypes.willr.flicks.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netflix.prototypes.willr.flicks.R;
import com.netflix.prototypes.willr.flicks.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.netflix.prototypes.willr.flicks.R.id.tvOverview;
import static com.netflix.prototypes.willr.flicks.R.id.tvTitle;

/**
 * Created by willr on 3/17/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Movie movie = getItem(position);
        ViewHolder viewHolder;

        // Check for a cached view
        if (convertView == null) {

            // Inflate a new view
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);

            // Init ViewHolder, find and retain view instances within.
            viewHolder = new ViewHolder();
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            viewHolder.tvTitle = (TextView) convertView.findViewById(tvTitle);
            viewHolder.tvOverview = (TextView) convertView.findViewById(tvOverview);

            // Cache the viewHolder object inside the view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Remove any existing image from the image view
        viewHolder.ivImage.setImageResource(0);

        // Populate the text views
        viewHolder.tvTitle.setText(movie.getOriginalTitle());
        viewHolder.tvOverview.setText(movie.getOverview());

        // Load the appropriate image for the current orientation
        // Swapping out the image seemed to provide a better transition than using two layouts.
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Picasso.with(getContext()).load(movie.getPosterPath()).into(viewHolder.ivImage);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Picasso.with(getContext()).load(movie.getBackdropPath()).into(viewHolder.ivImage);
        }

        // Return the view
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
        TextView tvOverview;
    }
}
