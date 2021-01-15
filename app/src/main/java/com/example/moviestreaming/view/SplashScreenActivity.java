 package com.example.moviestreaming.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.moviestreaming.R;
import com.example.moviestreaming.global.ConnectionChecker;
import com.example.moviestreaming.global.GetURLs;

 public class SplashScreenActivity extends AppCompatActivity {

     LottieAnimationView splashLottieFlies;
     Button retry_button;
     Handler handler;
     AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // set splash screen view with lottie
        splashLottieFlies = findViewById(R.id.splashLottieFlies);
        splashLottieFlies.setAnimationFromUrl(GetURLs.SplashAnimationURL);
        splashLottieFlies.playAnimation();

        if(ConnectionChecker.checkInternetConnection(SplashScreenActivity.this)){

            StartApp();

        }else{
            checkInternet();
        }



        // control Internet connection
        



        }

     private void StartApp() {

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreenActivity.this , IntroViewActivity.class);
                startActivity(intent);
                finish();

            }
        }, 5000);

     }

     private void checkInternet() {


        handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                if(!ConnectionChecker.checkInternetConnection(SplashScreenActivity.this)){


                    AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreenActivity.this);

                    LayoutInflater inflater = LayoutInflater.from(SplashScreenActivity.this);
                    View view = inflater.inflate(R.layout.alert_dialog_splash , null);
                    retry_button = view.findViewById(R.id.retry_button_splash);
                    retry_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            checkInternet();
                        }
                    });

                    builder.setView(view);

                    alertDialog = builder.create();
                    alertDialog.show();

                }else{

                    splashLottieFlies.playAnimation();
                    Intent intent = new Intent(SplashScreenActivity.this , IntroViewActivity.class);
                    startActivity(intent);
                    finish();

                }

            }
        } , 5000);


     }

 }