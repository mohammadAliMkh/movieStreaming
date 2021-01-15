package com.example.moviestreaming.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviestreaming.R;
import com.example.moviestreaming.model.SeriesSeason;
import com.example.moviestreaming.view.SeriesEpisodesActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.MyViewHolder> {

    Context context;
    List<SeriesSeason> seriesSeasons;

    public SeasonAdapter(Context context , List<SeriesSeason> seriesSeasons){
        this.context = context;
        this.seriesSeasons = seriesSeasons;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.season_item , parent , false  );
        return new SeasonAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.seasonNumber.setText("season " + seriesSeasons.get(position).getNumber().trim());
        holder.seasonEpisodes.setText(seriesSeasons.get(position).getEpisodes().trim() + " Eps");
        Picasso.get().load(seriesSeasons.get(position).getImg_link().trim()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , SeriesEpisodesActivity.class);
                System.out.println(seriesSeasons.get(position).getId());
                intent.putExtra("detail_id" , seriesSeasons.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return seriesSeasons.size() ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView seasonNumber , seasonEpisodes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.season_img);
            seasonNumber = itemView.findViewById(R.id.season_number);
            seasonEpisodes = itemView.findViewById(R.id.season_episodes);
        }
    }
}
