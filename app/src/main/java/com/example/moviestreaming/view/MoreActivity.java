package com.example.moviestreaming.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviestreaming.R;
import com.example.moviestreaming.fragment.AnimationFragment;
import com.example.moviestreaming.fragment.GenreFragment;
import com.example.moviestreaming.fragment.ListGenreFragment;
import com.example.moviestreaming.fragment.PopularFragment;
import com.example.moviestreaming.fragment.SeriesFragment;
import com.example.moviestreaming.fragment.TopIMDbFragment;

public class MoreActivity extends AppCompatActivity {

    String keyMore , keyFrom;
    TextView toolbarName_txt;
    ImageView back_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        Bundle bundle;
        bundle = getIntent().getExtras();

        keyFrom = bundle.getString("from_key");
        keyMore = bundle.getString("more_key");


        toolbarName_txt = findViewById(R.id.more_toolbar_name);

        back_icon = findViewById(R.id.back_icon_more);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        if(keyFrom.equals("genre_list")){

            toolbarName_txt.setTextColor(getResources().getColor(R.color.colorAccent));
            toolbarName_txt.setText(keyMore);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.moreFL , new ListGenreFragment(keyMore))
                    .commit();
        }else {

            switch (keyMore){
                case "Genre":
                    toolbarName_txt.setText("Movie Genre");
                    setFrameLayout("Genre");
                    break;
                case "IMDb Tops":
                    toolbarName_txt.setText("IMDb Tops");
                    setFrameLayout("Top IMDb");
                    break;
                case "Popular":
                    toolbarName_txt.setText("Popular Movies");
                    setFrameLayout("Popular");
                    break;
                case "Series":
                    toolbarName_txt.setText("Series");
                    setFrameLayout("Series");
                    break;
                case "Animation":
                    toolbarName_txt.setText("Animations");
                    setFrameLayout("Animation");
                    break;
            }
        }

    }

    private void setFrameLayout(String key) {

        if(key == "Genre"){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.moreFL , new GenreFragment())
                    .commit();
        }else if(key == "Top IMDb"){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.moreFL , new TopIMDbFragment())
                    .commit();
        }else if(key == "Popular"){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.moreFL , new PopularFragment())
                    .commit();
        }else if(key == "Series"){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.moreFL , new SeriesFragment())
                    .commit();
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.moreFL , new AnimationFragment())
                    .commit();
        }

    }

}