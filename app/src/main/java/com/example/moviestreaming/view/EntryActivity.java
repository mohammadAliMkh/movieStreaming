package com.example.moviestreaming.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.moviestreaming.R;
import com.example.moviestreaming.fragment.RegisterFragment;

public class EntryActivity extends AppCompatActivity {

    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        if(checkState()){
            Intent intent = new Intent(EntryActivity.this , HomeActivity.class);
            startActivity(intent);
            finish();
        }

        frameLayout = findViewById(R.id.entryFL);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.entryFL , new RegisterFragment())
                .commit();




    }

    private boolean checkState() {

        SharedPreferences sharedPreferences = getSharedPreferences("sharePrefMovieStreamingEntry" , Context.MODE_PRIVATE);
        boolean openActivity = sharedPreferences.getBoolean("openActivityEntry" , false);
        return openActivity;
    }
}