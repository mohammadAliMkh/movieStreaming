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

import com.example.moviestreaming.Interface.onItemClick;
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

public class HomeTopAdapter extends RecyclerView.Adapter<HomeTopAdapter.MyViewHolder> {

    Context context;
    List<HomeMovie> homeTopList;

    public HomeTopAdapter(Context context , List<HomeMovie> homeTopList){
        this.context = context;
        this.homeTopList = homeTopList;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_top_item , parent , false);
        return new HomeTopAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name_tv.setText(homeTopList.get(position).getName());
        holder.time_tv.setText(homeTopList.get(position).getTime());
        Picasso.get().load(homeTopList.get(position).getImage_link()).into(holder.image);
        holder.setListener(new onItemClick() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context , DetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (!homeTopList.get(position).getCategory().equals("series")) {
                    intent.putExtra("key", "movie");
                } else {
                    intent.putExtra("key", "series");
                }

                Bundle bundle = new Bundle();
                bundle.putString("id" , homeTopList.get(position).getId());
                bundle.putString("name" , homeTopList.get(position).getName());
                bundle.putString("director" , homeTopList.get(position).getDirector());
                bundle.putString("category" , homeTopList.get(position).getCategory());
                bundle.putString("genre" , homeTopList.get(position).getGenre());
                bundle.putString("publish" , homeTopList.get(position).getPublish());
                bundle.putString("time" , homeTopList.get(position).getTime());
                bundle.putString("rate", homeTopList.get(position).getRate());
                bundle.putString("image_link" , homeTopList.get(position).getImage_link());

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeTopList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name_tv , time_tv;
        ImageView image;
        BlurView blurView;
        RelativeLayout rootView;
        onItemClick listener;

        public void setListener(onItemClick listener) {
            this.listener = listener;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.root_home_top_view);

            name_tv = itemView.findViewById(R.id.homeTopItemNameTV);
            time_tv = itemView.findViewById(R.id.homeTopItemTimeTV);
            image = itemView.findViewById(R.id.homeTopIV);

            blurView = itemView.findViewById(R.id.homeTopItemBV);
            blurView.setupWith(rootView)
                    .setBlurAlgorithm(new RenderScriptBlur(context))
                    .setBlurRadius(20f)
                    .setBlurAutoUpdate(true)
                    .setHasFixedTransformationMatrix(true);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener.onClick(view);
        }
    }
}
