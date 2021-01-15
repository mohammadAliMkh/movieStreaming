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
import com.example.moviestreaming.model.HomeMovie;
import com.example.moviestreaming.view.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class HomePopularAdapter extends RecyclerView.Adapter<HomePopularAdapter.MyViewHolder> {

    Context context;
    List<HomeMovie> homePopularList = new ArrayList<>();

    public HomePopularAdapter(Context context , List<HomeMovie> homePopularList){
        this.context = context;
        this.homePopularList = homePopularList;
        System.out.println(this.homePopularList.get(1).getName());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_popular_item , parent , false);
        return new HomePopularAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name_tv.setText(homePopularList.get(position).getName());
        holder.time_tv.setText(homePopularList.get(position).getTime());
        holder.rate_tv.setText(homePopularList.get(position).getRate());
        Picasso.get().load(homePopularList.get(position).getImage_link()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context , DetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("key" , "movie");

                Bundle bundle = new Bundle();
                bundle.putString("id" , homePopularList.get(position).getId());
                bundle.putString("name" , homePopularList.get(position).getName());
                bundle.putString("director" , homePopularList.get(position).getDirector());
                bundle.putString("category" , homePopularList.get(position).getCategory());
                bundle.putString("genre" , homePopularList.get(position).getGenre());
                bundle.putString("publish" , homePopularList.get(position).getPublish());
                bundle.putString("time" , homePopularList.get(position).getTime());
                bundle.putString("rate", homePopularList.get(position).getRate());
                bundle.putString("image_link" , homePopularList.get(position).getImage_link());

                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return homePopularList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name_tv , time_tv , rate_tv;
        ImageView image;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name_tv = itemView.findViewById(R.id.homePopularNameTV);
            time_tv = itemView.findViewById(R.id.homePopularTimeTV);
            rate_tv = itemView.findViewById(R.id.homePopularRateTV);
            image = itemView.findViewById(R.id.homePopularIV);
        }
    }
}
