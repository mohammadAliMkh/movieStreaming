package com.example.moviestreaming.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
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
import androidx.recyclerview.widget.RecyclerView;

public class FragmentGenreAdapter extends RecyclerView.Adapter<FragmentGenreAdapter.MyViewHolder> {

    Context context;
    List<HomeGenre> homeGenreList;

    public FragmentGenreAdapter(Context context , List<HomeGenre> homeGenreList){
        this.context = context;
        this.homeGenreList = homeGenreList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_genre_item , parent , false  );
        return new FragmentGenreAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text.setText(homeGenreList.get(position).getName());
        Picasso.get().load(homeGenreList.get(position).getImage_link()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , MoreActivity.class);
                intent.putExtra("more_key" , homeGenreList.get(position).getName().trim());
                intent.putExtra("from_key" , "genre_list");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeGenreList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView text;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.genre_fragment_image);
            text = itemView.findViewById(R.id.genre_fragment_name);
        }
    }


}
