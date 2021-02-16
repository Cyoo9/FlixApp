package com.example.flixter.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.flixter.R;
import com.example.flixter.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }


    //Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        RecyclerView.ViewHolder holder = null;
        View movieView; 
        switch(viewType) {
            case 0: 
                movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
                holder = new ViewHolder(movieView);
                break;
            case 1:
                movieView = LayoutInflater.from(context).inflate(R.layout.item_popular_movie, parent, false);
                holder = new ViewHolder2(movieView); 
                break;
            default:
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        //Get the movie at the passed position

        Movie movie = movies.get(position);
        switch(holder.getItemViewType()) {
            case 0:
                ViewHolder movieView = (ViewHolder) holder;
                movieView.bind(movie);
                break;
            case 1:
                ViewHolder2 popularMovieView = (ViewHolder2)  holder;
                popularMovieView.bindBackDropImage(movie);
                break;
            default:
                break;
        }
    }


    //returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }   

    @Override
    public int getItemViewType(int position) {
        if(movies.get(position).getVoteAverage() >= 6) {
            return 1; //1 is popular 
        }
        return 0; //0 is unpopular 
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);

        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            // if phone is in landscape
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
            }
            else {
                imageUrl = movie.getPosterPath();
            }
            // then imageUrl = back drop image
            // else imageUrl = poster image

            Glide.with(context).load(imageUrl).placeholder(R.drawable.hourglass).into(ivPoster);
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {

        ImageView ivPoster;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);

        }

        public void bindBackDropImage(Movie movie) {
            String imageUrl;
            // if phone is in landscape
                imageUrl = movie.getBackdropPath();
            // then imageUrl = back drop image
            // else imageUrl = poster image

            Glide.with(context).load(imageUrl).placeholder(R.drawable.hourglass).into(ivPoster);
        }
    }
}
