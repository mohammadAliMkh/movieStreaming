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

public class SeriesDetailFragment extends Fragment implements View.OnClickListener {

    TextView name, director, seasons, publish, description, rate, genre, download;
    ImageView like_IC, comment_IC , back_ic;
    RecyclerView cast_RV, similar_RV, season_RV;
    KenBurnsView kenBurnsView;
    Bundle bundle;
    FavoriteDB favoriteDB;

    SharedPreferences sharedPreferences;

    public SeriesDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bundle = getArguments();

        favoriteDB = FavoriteDB.getNewInstance(getContext());

        View view =  inflater.inflate(R.layout.fragment_series_detail, container, false);

        setViewsById(view);
        setDataToViews(bundle);

        return  view;
    }


    private void setDataToViews(Bundle bundle) {
        name.setText(bundle.getString("name").trim());
        director.setText("DIRECTOR:" + bundle.getString("director").trim());
        seasons.setText(bundle.getString("time").trim());
        publish.setText("PUBLISHED:" + bundle.getString("publish").trim());
        rate.setText("IMDb: " + bundle.getString("rate").trim() + "/10");
        genre.setText(bundle.getString("genre").trim());
        GlobalDetailFragment globalDetailFragment = new GlobalDetailFragment(getContext(), kenBurnsView, description , cast_RV , season_RV);
        Log.d("fav_id" , bundle.getString("id"));
        globalDetailFragment.setSeriesDetail(bundle.getString("id"));
        globalDetailFragment.setCastRV(bundle.getString("id"));
        globalDetailFragment.setSeasonRV(bundle.getString("id"));
        GlobalDetailFragment globalDetailFragment1 = new GlobalDetailFragment(getContext() , similar_RV);
        globalDetailFragment1.setSimilarRV(bundle.getString("category"));
    }

    private void setViewsById(View view) {

        name = view.findViewById(R.id.detail_series_name);
        director = view.findViewById(R.id.detail_series_director);
        seasons = view.findViewById(R.id.detail_series_seasons);
        publish = view.findViewById(R.id.detail_series_publish);
        description = view.findViewById(R.id.detail_series_description);
        rate = view.findViewById(R.id.detail_series_rate);
        genre = view.findViewById(R.id.detail_series_genre);

        download = view.findViewById(R.id.detail_series_download);
        download.setOnClickListener(this);

        like_IC = view.findViewById(R.id.detail_series_like_icon);
        Log.d("fav_id" , bundle.getString("id"));
        if(favoriteDB.getFavoriteMovieDao().isLiked(bundle.getString("id")) == 1){
            like_IC.setImageResource(R.drawable.full_like_icon);
        }else{
            like_IC.setImageResource(R.drawable.like_icon);
        }
        like_IC.setOnClickListener(this);

        comment_IC = view.findViewById(R.id.detail_series_comment_icon);
        comment_IC.setOnClickListener(this);

        back_ic = view.findViewById(R.id.detail_series_back_icon);
        back_ic.setOnClickListener(this);

        cast_RV = view.findViewById(R.id.detail_Cast_RV);
        similar_RV = view.findViewById(R.id.detail_similar_seriesRV);
        season_RV = view.findViewById(R.id.season_RV);
        kenBurnsView = view.findViewById(R.id.detail_series_image);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.detail_series_back_icon){
            super.getActivity().onBackPressed();
        }else if(view.getId() == R.id.detail_series_like_icon){

            FavoriteMovie favoriteMovie = new FavoriteMovie();
            favoriteMovie.id = bundle.getString("id");
            Log.d("fav_id" , favoriteMovie.id);
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
        }else if(view.getId() == R.id.detail_series_comment_icon){

            sharedPreferences = getContext().getSharedPreferences("sharePrefMovieStreamingEntry" , Context.MODE_PRIVATE);

            Bundle bundle1 = new Bundle();
            bundle1.putString("id" , bundle.getString("id"));
            bundle1.putString("username" ,sharedPreferences.getString("username" , "") );

            Intent intent = new Intent(getContext() , CommentActivity.class);
            intent.putExtras(bundle1);


            getContext().startActivity(intent);


        }
    }
}