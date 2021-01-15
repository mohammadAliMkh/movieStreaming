package com.example.moviestreaming.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.moviestreaming.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class PlayActivity extends AppCompatActivity {

    PlayerView playerView;
    SimpleExoPlayer simpleExoPlayer;

    AlertDialog alertDialog;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        checkExpiration();

    }

    private void checkExpiration() {

        SharedPreferences sharedPreferences = getSharedPreferences("sharePrefMovieStreamingEntry" , Context.MODE_PRIVATE);
        Long remain = sharedPreferences.getLong("total_credit" , 0);
        remain = remain - System.currentTimeMillis()/1000;


        if(remain<=0) {


            LayoutInflater inflater = getLayoutInflater();

            View view1 = inflater.inflate(R.layout.expire_layout, null);

            Button btn_yes = view1.findViewById(R.id.alert_buy_account_btn_yes);
            Button btn_no = view1.findViewById(R.id.alert_buy_account_btn_no);

            builder = new AlertDialog.Builder(PlayActivity.this);
            alertDialog = builder.setView(view1).create();
            alertDialog.show();


            btn_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.cancel();
                    alertDialog.dismiss();
                    finish();
                }
            });

            btn_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.cancel();
                    Intent intent = new Intent(PlayActivity.this, BuyAccountActivity.class);
                    startActivity(intent);
                }
            });

        }else{

            Bundle bundle = getIntent().getExtras();

            playerView = findViewById(R.id.playerView);
            simpleExoPlayer = new SimpleExoPlayer.Builder(this).build();

            MediaItem mediaItem = MediaItem.fromUri(bundle.getString("video_link"));
            simpleExoPlayer.setMediaItem(mediaItem);

            simpleExoPlayer.setPlayWhenReady(true);
            simpleExoPlayer.seekTo(0 , 0);
            simpleExoPlayer.prepare();

            playerView.setPlayer(simpleExoPlayer);

        }

    }

    @Override
    public void onBackPressed() {

        if(alertDialog!=null){

            alertDialog.dismiss();
            finish();

        }else{

            if (simpleExoPlayer != null) {

                super.onBackPressed();
                simpleExoPlayer.setPlayWhenReady(false);
                simpleExoPlayer.stop();
                simpleExoPlayer.seekTo(0);

            }

        }

    }


}