package com.example.moviestreaming.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.moviestreaming.R;
import com.example.moviestreaming.adapter.FavoriteAdapter;
import com.example.moviestreaming.database.database.FavoriteDB;
import com.example.moviestreaming.database.model.FavoriteMovie;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    RecyclerView favoriteRv;
    ImageView backIcon;
    FavoriteDB favoriteDB;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        favoriteDB = FavoriteDB.getNewInstance(getApplicationContext());
        compositeDisposable = new CompositeDisposable();

        favoriteRv = findViewById(R.id.favoriteRV);
        setFavoriteRecyclerView();

        backIcon = findViewById(R.id.back_icon_favorite);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setFavoriteRecyclerView() {

        compositeDisposable.add(favoriteDB.getFavoriteMovieDao().getFavoriteMovies().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<FavoriteMovie>>() {
            @Override
            public void accept(List<FavoriteMovie> favoriteMovies) throws Exception {
                FavoriteAdapter adapter = new FavoriteAdapter(getApplicationContext() , favoriteMovies);
                favoriteRv.setHasFixedSize(true);
                favoriteRv.setLayoutManager(new LinearLayoutManager(getApplicationContext() , RecyclerView.VERTICAL , false));
                favoriteRv.setAdapter(adapter);
            }
        }));
    }
}