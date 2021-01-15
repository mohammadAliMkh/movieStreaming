package com.example.moviestreaming.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviestreaming.R;
import com.example.moviestreaming.database.database.FavoriteDB;
import com.example.moviestreaming.database.model.FavoriteMovie;
import com.example.moviestreaming.view.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {

    Context context;
    List<FavoriteMovie> favoriteMovies;
    FavoriteDB favoriteDB;

    public FavoriteAdapter(Context context , List<FavoriteMovie> favoriteMovies){
        this.context = context;
        this.favoriteMovies = favoriteMovies;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_favorite_item , parent , false);
        favoriteDB = FavoriteDB.getNewInstance(context);
        return new FavoriteAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(favoriteMovies.get(position).name.trim());
        holder.director.setText("Director: " +favoriteMovies.get(position).director.trim());
        holder.director.setMaxLines(2);
        holder.time.setText(favoriteMovies.get(position).time.trim());
        holder.publish.setText(favoriteMovies.get(position).publish.trim());
        holder.rate.setText(favoriteMovies.get(position).rate.trim());

        Picasso.get().load(favoriteMovies.get(position).image_link).into(holder.image);

        if(favoriteDB.getFavoriteMovieDao().isLiked(favoriteMovies.get(position).id) == 1){
            holder.likeIc.setImageResource(R.drawable.full_like_icon);
        }
        holder.likeIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.likeIc.setImageResource(R.drawable.like_icon);
                favoriteDB.getFavoriteMovieDao().unLikeMovie(favoriteMovies.get(position));
                notifyDataSetChanged();

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , DetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                intent.putExtra("key" , favoriteMovies.get(position).category);
                bundle.putString("id" , favoriteMovies.get(position).id);
                Log.d("fav_id" , favoriteMovies.get(position).id);
                bundle.putString("name" , favoriteMovies.get(position).name);
                bundle.putString("director" , favoriteMovies.get(position).director);
                bundle.putString("category" , favoriteMovies.get(position).category);
                bundle.putString("genre" , favoriteMovies.get(position).genre);
                bundle.putString("publish" , favoriteMovies.get(position).publish);
                bundle.putString("time" , favoriteMovies.get(position).time);
                bundle.putString("rate", favoriteMovies.get(position).rate);
                bundle.putString("image_link" , favoriteMovies.get(position).image_link);
                bundle.putString("rank" , favoriteMovies.get(position).rank);

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return favoriteMovies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name , time, director, publish, rate;
        ImageView likeIc , commentIc , image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.favorite_fragment_name);
            time = itemView.findViewById(R.id.favorite_fragment_time);
            director = itemView.findViewById(R.id.favorite_fragment_director);
            publish = itemView.findViewById(R.id.favorite_fragment_publish);
            rate = itemView.findViewById(R.id.favorite_fragment_rate);

            likeIc = itemView.findViewById(R.id.favorite_fragment_like);
            commentIc = itemView.findViewById(R.id.favorite_fragment_comment);
            image = itemView.findViewById(R.id.favorite_fragment_image);

        }
    }
}
