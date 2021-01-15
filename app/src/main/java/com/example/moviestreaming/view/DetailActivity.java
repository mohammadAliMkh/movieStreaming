package com.example.moviestreaming.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.moviestreaming.R;
import com.example.moviestreaming.fragment.MovieDetailFragment;
import com.example.moviestreaming.fragment.SeriesDetailFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String key = getIntent().getStringExtra("key").trim();
        System.out.println(key);
        Bundle bundle = getIntent().getExtras();
        System.out.println(bundle.getString("id"));

        if(key.equals("series")){

            SeriesDetailFragment seriesDetailFragment = new SeriesDetailFragment();
            seriesDetailFragment.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detailFL , seriesDetailFragment)
                    .commit();

        }else{

            MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
            movieDetailFragment.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detailFL , movieDetailFragment)
                    .commit();


        }

    }
}