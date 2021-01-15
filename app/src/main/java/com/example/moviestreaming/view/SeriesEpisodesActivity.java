package com.example.moviestreaming.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.moviestreaming.R;
import com.example.moviestreaming.adapter.EpisodeAdapter;
import com.example.moviestreaming.global.GlobalDetailFragment;
import com.example.moviestreaming.model.Episode;

public class SeriesEpisodesActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView episodeRV;
    ImageView back_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_episodes);

        String detail_id = getIntent().getStringExtra("detail_id");
        System.out.println("googooooooli");
        System.out.println(detail_id);

        episodeRV = findViewById(R.id.episodes_RV);
        GlobalDetailFragment globalDetailFragment = new GlobalDetailFragment(getApplicationContext() , episodeRV);
        globalDetailFragment.setEpisodeRV(detail_id);

        back_icon = findViewById(R.id.back_icon_episodes);
        back_icon.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.back_icon_episodes){
            onBackPressed();
            finish();
        }
    }
}