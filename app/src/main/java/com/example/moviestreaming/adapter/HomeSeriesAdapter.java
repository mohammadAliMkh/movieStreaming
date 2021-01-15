package com.example.moviestreaming.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.moviestreaming.R;
import com.example.moviestreaming.model.HomeMovie;
import com.example.moviestreaming.view.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class HomeSeriesAdapter extends RecyclerView.Adapter<HomeSeriesAdapter.MyViewHolder> {

    Context context;
    List<HomeMovie> homeSeriesList;

    public HomeSeriesAdapter(Context context , List<HomeMovie> homeSeriesList){
        this.context = context;
        this.homeSeriesList = homeSeriesList;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_series_item , parent , false);
        return new HomeSeriesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name_tv.setText(homeSeriesList.get(position).getName().trim());
        holder.time_tv.setText(homeSeriesList.get(position).getTime().trim());
        Picasso.get().load(homeSeriesList.get(position).getImage_link()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context , DetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("key" , "series");

                Bundle bundle = new Bundle();
                bundle.putString("id" , homeSeriesList.get(position).getId());
                Log.d("fav_id" , homeSeriesList.get(position).getId() + "googooooli");
                bundle.putString("name" , homeSeriesList.get(position).getName());
                bundle.putString("director" , homeSeriesList.get(position).getDirector());
                bundle.putString("category" , homeSeriesList.get(position).getCategory());
                bundle.putString("genre" , homeSeriesList.get(position).getGenre());
                bundle.putString("publish" , homeSeriesList.get(position).getPublish());
                bundle.putString("time" , homeSeriesList.get(position).getTime());
                bundle.putString("rate", homeSeriesList.get(position).getRate());
                bundle.putString("image_link" , homeSeriesList.get(position).getImage_link());

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeSeriesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name_tv , time_tv;
        ImageView image;
        BlurView blurView;
        RelativeLayout rootView;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.root_home_series_view);

            name_tv = itemView.findViewById(R.id.homeSeriesItemNameTV);
            time_tv = itemView.findViewById(R.id.homeSeriesItemTimeTV);
            image = itemView.findViewById(R.id.homeSeriesIV);

            blurView = itemView.findViewById(R.id.homeSeriesItemBV);
            blurView.setupWith(rootView)
                    .setBlurAlgorithm(new RenderScriptBlur(context))
                    .setBlurRadius(8f)
                    .setBlurAutoUpdate(true)
                    .setHasFixedTransformationMatrix(true);
        }
    }
}
