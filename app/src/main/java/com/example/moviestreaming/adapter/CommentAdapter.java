package com.example.moviestreaming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviestreaming.R;
import com.example.moviestreaming.model.Comment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    List<Comment> comments;
    Context context;

    public CommentAdapter(Context context , List<Comment> comments){


        this.comments = comments;
        this.context = context;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.comment_item , viewGroup , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


        myViewHolder.txtDate.setText(comments.get(i).getDate().trim());
        myViewHolder.txtUser.setText(comments.get(i).getUsername().trim());
        myViewHolder.txtComment.setText(comments.get(i).getText().trim());

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }




    public class   MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtComment , txtUser , txtDate;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtComment = itemView.findViewById(R.id.user_comment_txt    );
            txtUser = itemView.findViewById(R.id.username_comment);
            txtDate = itemView.findViewById(R.id.date_comment);


        }
    }
}
