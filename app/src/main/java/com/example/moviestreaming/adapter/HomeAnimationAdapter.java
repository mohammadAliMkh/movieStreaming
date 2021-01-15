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

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class HomeAnimationAdapter extends RecyclerView.Adapter<HomeAnimationAdapter.MyViewHolder> {

    Context context;
    List<HomeMovie> homeAnimationList;

    public HomeAnimationAdapter(Context context , List<HomeMovie> homeAnimationList){
        this.context = context;
        this.homeAnimationList = homeAnimationList;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_animation_item , parent , false);
        return new HomeAnimationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name_tv.setText(homeAnimationList.get(position).getName());
        holder.time_tv.setText(homeAnimationList.get(position).getTime());
        holder.rate_tv.setText(homeAnimationList.get(position).getRate());
        Picasso.get().load(homeAnimationList.get(position).getImage_link()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , DetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("key" , "movie");

                Bundle bundle = new Bundle();
                bundle.putString("id" , homeAnimationList.get(position).getId());
                bundle.putString("name" , homeAnimationList.get(position).getName());
                bundle.putString("director" , homeAnimationList.get(position).getDirector());
                bundle.putString("category" , homeAnimationList.get(position).getCategory());
                bundle.putString("genre" , homeAnimationList.get(position).getGenre());
                bundle.putString("publish" , homeAnimationList.get(position).getPublish());
                bundle.putString("time" , homeAnimationList.get(position).getTime());
                bundle.putString("rate", homeAnimationList.get(position).getRate());
                bundle.putString("image_link" , homeAnimationList.get(position).getImage_link());

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeAnimationList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name_tv , time_tv , rate_tv;
        ImageView image;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name_tv = itemView.findViewById(R.id.homeAnimationNameTV);
            time_tv = itemView.findViewById(R.id.homeAnimationTimeTV);
            rate_tv = itemView.findViewById(R.id.homeAnimationRateTV);
            image = itemView.findViewById(R.id.homeAnimationIV);
        }
    }
}
