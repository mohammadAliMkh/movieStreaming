package com.example.moviestreaming.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.moviestreaming.R;
import com.example.moviestreaming.fragment.CategorySearchFragment;
import com.example.moviestreaming.fragment.FullSearchFragment;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    FrameLayout searchFL;
    Button search_by_category_btn;
    ImageView backIC;
    TextView searchTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchFL = findViewById(R.id.searchFL);

        searchTV = findViewById(R.id.searchTV_toolbar);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.searchFL , new FullSearchFragment(""))
                .commit();

        search_by_category_btn = findViewById(R.id.search_by_category_btn);
        searchTV.setText("Full Search Panel");
        search_by_category_btn.setText("->   Click To Search By Category   <-");
        search_by_category_btn.setOnClickListener(this);

        backIC = findViewById(R.id.back_icon_search);
        backIC.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.search_by_category_btn){

            if(search_by_category_btn.getText().equals("->   Click To Search By Category   <-")){
                search_by_category_btn.setText("->   Click To Full Search   <-");
                searchTV.setText("Genre Search Panel");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.searchFL , new CategorySearchFragment())
                        .commit();
            }else if(search_by_category_btn.getText().equals("->   Click To Full Search   <-")){
                search_by_category_btn.setText("->   Click To Search By Category   <-");
                searchTV.setText("Full Search Panel");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.searchFL , new FullSearchFragment(""))
                        .commit();
            }
        }else if(view.getId() == R.id.back_icon_search){
            onBackPressed();
        }
    }
}