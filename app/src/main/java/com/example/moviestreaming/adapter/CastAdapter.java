package com.example.moviestreaming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviestreaming.R;
import com.example.moviestreaming.model.Cast;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.MyViewHOlder> {

    Context context;
    List<Cast> casts;

    public CastAdapter(Context context , List<Cast> casts){
        this.casts = casts;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cast_item , parent , false);
        return new CastAdapter.MyViewHOlder(view)   ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHOlder holder, int position) {
        holder.name.setText(casts.get(position).getName());
        Picasso.get().load(casts.get(position).getImage_link()).into(holder.profile_img);
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    public class   MyViewHOlder extends RecyclerView.ViewHolder {

        TextView name;
        CircleImageView profile_img;

        public MyViewHOlder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_cast);
            profile_img = itemView.findViewById(R.id.profile_img_cast);
        }
    }
}
