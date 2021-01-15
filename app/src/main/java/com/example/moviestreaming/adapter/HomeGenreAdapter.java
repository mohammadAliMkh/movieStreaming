package com.example.moviestreaming.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviestreaming.R;
import com.example.moviestreaming.model.HomeGenre;
import com.example.moviestreaming.view.MoreActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class HomeGenreAdapter extends RecyclerView.Adapter<HomeGenreAdapter.MyViewHolder> {

    Context context;
    List<HomeGenre> homeGenres;

    public HomeGenreAdapter(Context context , List<HomeGenre> homeGenres){
        this.context = context;
        this.homeGenres = homeGenres;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_genre_item, parent , false);
        return new HomeGenreAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name_tv.setText(homeGenres.get(position).getName());
        Picasso.get().load(homeGenres.get(position).getImage_link()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context , MoreActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Bundle bundle = new Bundle();
                bundle.putString("more_key" , homeGenres.get(position).getName().trim());
                bundle.putString("from_key" , "genre_list");

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeGenres.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name_tv;
        ImageView image;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name_tv = itemView.findViewById(R.id.homeGenreNameTV);
            image = itemView.findViewById(R.id.homeGenreIV);

        }
    }
}
