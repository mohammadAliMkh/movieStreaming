package com.example.moviestreaming.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviestreaming.Interface.onItemClick;
import com.example.moviestreaming.R;
import com.example.moviestreaming.model.HomeMovie;
import com.example.moviestreaming.view.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentTopAdapter extends RecyclerView.Adapter<FragmentTopAdapter.MyViewHolder> implements View.OnClickListener {

    Context context;
    List<HomeMovie> homeMovies;

    public FragmentTopAdapter(Context context , List<HomeMovie> homeMovies){
        this.context = context;
        this.homeMovies = homeMovies;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_top_imdb_item , parent , false  );
        return new FragmentTopAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(homeMovies.get(position).getName());
        holder.director.setText(homeMovies.get(position).getDirector());
        holder.time.setText(homeMovies.get(position).getTime());
        holder.publish.setText(homeMovies.get(position).getPublish());
        holder.rank.setText(homeMovies.get(position).getRank());
        Picasso.get().load(homeMovies.get(position).getImage_link()).into(holder.image);
        holder.comment.setOnClickListener(this);

        holder.setListener(new onItemClick() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , DetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("key" , "movie");

                Bundle bundle = new Bundle();
                bundle.putString("id" , homeMovies.get(position).getId());
                bundle.putString("name" , homeMovies.get(position).getName());
                bundle.putString("director" , homeMovies.get(position).getDirector());
                bundle.putString("category" , homeMovies.get(position).getCategory());
                bundle.putString("genre" , homeMovies.get(position).getGenre());
                bundle.putString("publish" , homeMovies.get(position).getPublish());
                bundle.putString("time" , homeMovies.get(position).getTime());
                bundle.putString("rate", homeMovies.get(position).getRate());
                bundle.putString("image_link" , homeMovies.get(position).getImage_link());

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return homeMovies.size();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.top_fragment_comment){

        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image , like , comment;
        TextView name, director , time , publish, rank;

        onItemClick listener;

        public void setListener(onItemClick listener) {
            this.listener = listener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.top_fragment_image);
            comment = itemView.findViewById(R.id.top_fragment_comment);

            name = itemView.findViewById(R.id.top_fragment_name);
            director = itemView.findViewById(R.id.top_fragment_director);
            time = itemView.findViewById(R.id.top_fragment_time);
            publish = itemView.findViewById(R.id.top_fragment_publish);
            rank = itemView.findViewById(R.id.top_fragment_rank);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view);
                }
            });
        }
    }


}
