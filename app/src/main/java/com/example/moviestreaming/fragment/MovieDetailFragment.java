package com.example.moviestreaming.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviestreaming.R;
import com.example.moviestreaming.database.database.FavoriteDB;
import com.example.moviestreaming.database.model.FavoriteMovie;
import com.example.moviestreaming.global.GlobalDetailFragment;
import com.example.moviestreaming.view.CommentActivity;
import com.flaviofaria.kenburnsview.KenBurnsView;


public class MovieDetailFragment extends Fragment implements View.OnClickListener {

    TextView name, director, time, publish, description, rate, genre, download;
    ImageView like_IC, comment_IC, play_IC , back_ic;
    RecyclerView cast_RV, similar_RV;
    KenBurnsView kenBurnsView;
    Bundle bundle;
    FavoriteDB favoriteDB;
    String id_movie;
    SharedPreferences sharedPreferences;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bundle = getArguments();

        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        favoriteDB = FavoriteDB.getNewInstance(getContext());
        setViewsById(view);
        setDataToViews(bundle);

        return view;
    }

    private void setDataToViews(Bundle bundle) {
        id_movie = bundle.getString("id");
        name.setText(bundle.getString("name").trim());
        director.setText("Director:" + bundle.getString("director").trim());
        time.setText(bundle.getString("time").trim());
        publish.setText("Published:" + bundle.getString("publish").trim());
        rate.setText("IMDb: " + bundle.getString("rate").trim() + "/10");
        genre.setText(bundle.getString("genre").trim());
        GlobalDetailFragment globalDetailFragment = new GlobalDetailFragment(getContext(), kenBurnsView, description);
        globalDetailFragment.setMovieDetail(bundle.getString("id") , play_IC, download);
        
        setCastRV();
        setSimilarRV();
    }

    private void setSimilarRV() {
        GlobalDetailFragment globalDetailFragment = new GlobalDetailFragment(getContext() , similar_RV);
        globalDetailFragment.setSimilarRV(bundle.getString("category").trim());
    }

    private void setCastRV() {
        GlobalDetailFragment globalDetailFragment = new GlobalDetailFragment(getContext() , cast_RV);
        globalDetailFragment.setCastRV(bundle.getString("id"));
    }

    private void setViewsById(View view) {

        name = view.findViewById(R.id.detail_movie_name);
        director = view.findViewById(R.id.detail_movie_director);
        time = view.findViewById(R.id.detail_movie_time);
        publish = view.findViewById(R.id.detail_movie_publish);
        description = view.findViewById(R.id.detail_movie_description);
        rate = view.findViewById(R.id.detail_movie_rate);
        genre = view.findViewById(R.id.detail_movie_genre);


        download = view.findViewById(R.id.detail_movie_download);
        download.setOnClickListener(this);

        like_IC = view.findViewById(R.id.detail_movie_like_icon);
        if(favoriteDB.getFavoriteMovieDao().isLiked(bundle.getString("id")) == 1){

            Log.d("like_id" , bundle.getString("id"));

            like_IC.setImageResource(R.drawable.full_like_icon);
        }else {
            like_IC.setImageResource(R.drawable.like_icon);
        }
        like_IC.setOnClickListener(this);


        comment_IC = view.findViewById(R.id.detail_movie_comment_icon);
        comment_IC.setOnClickListener(this);


        play_IC = view.findViewById(R.id.detail_play_movie);
        play_IC.setOnClickListener(this);


        back_ic = view.findViewById(R.id.detail_movie_back_icon);
        back_ic.setOnClickListener(this);

        cast_RV = view.findViewById(R.id.detail_Cast_RV);
        similar_RV = view.findViewById(R.id.detail_similar_movieRV);
        kenBurnsView = view.findViewById(R.id.detail_movie_image);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.detail_movie_back_icon){

            super.getActivity().onBackPressed();

        }else if(view.getId() == R.id.detail_movie_like_icon){

            FavoriteMovie favoriteMovie = new FavoriteMovie();
            favoriteMovie.id = bundle.getString("id");
            Log.d("fav_id" , favoriteMovie.id);
            favoriteMovie.name = bundle.getString("name");
            favoriteMovie.director = bundle.getString("director");
            favoriteMovie.time = bundle.getString("time");
            favoriteMovie.rank = bundle.getString("rank");
            favoriteMovie.publish   = bundle.getString("publish");
            favoriteMovie.genre = bundle.getString("genre");
            favoriteMovie.image_link = bundle.getString("image_link");
            favoriteMovie.category = bundle.getString("category");
            favoriteMovie.rate = bundle.getString("rate");

            if(favoriteDB.getFavoriteMovieDao().isLiked(bundle.getString("id")) == 1){
                    favoriteDB.getFavoriteMovieDao().unLikeMovie(favoriteMovie);
                    like_IC.setImageResource(R.drawable.like_icon);
            }else{
                favoriteDB.getFavoriteMovieDao().likeMovie(favoriteMovie);
                like_IC.setImageResource(R.drawable.full_like_icon);
            }

        } else if(view.getId() == R.id.detail_movie_comment_icon){

            Bundle bundle = new Bundle();
            bundle.putString("id" , id_movie);
            sharedPreferences = getContext().getSharedPreferences("sharePrefMovieStreamingEntry" , Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("username" , "");
            bundle.putString("username" , username);
            Intent intent  = new Intent(getContext() , CommentActivity.class);
            intent.putExtras(bundle);
            getContext().startActivity(intent);

        }
    }
}