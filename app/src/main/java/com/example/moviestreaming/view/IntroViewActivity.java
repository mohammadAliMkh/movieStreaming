package com.example.moviestreaming.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.moviestreaming.R;
import com.example.moviestreaming.adapter.IntroAdapter;
import com.example.moviestreaming.global.GlobalIntroduction;
import com.example.moviestreaming.model.Intro;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroViewActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    Button nextBtn , previousBtn , startBtn;

    IntroAdapter introAdapter;
    List<Intro> introList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_view);

        if(checkState()){

            Intent intent = new Intent(IntroViewActivity.this , EntryActivity.class);
            startActivity(intent);
            finish();
        }

        viewPager = findViewById(R.id.introVP);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position == viewPager.getAdapter().getCount()-1){
                    nextBtn.setVisibility(View.GONE);
                    tabLayout.setVisibility(View.GONE);
                    startBtn.setVisibility(View.VISIBLE);
                    previousBtn.setVisibility(View.GONE);

                } else if(position == 0){

                    nextBtn.setVisibility(View.VISIBLE);
                    tabLayout.setVisibility(View.VISIBLE);
                    startBtn.setVisibility(View.GONE);
                    previousBtn.setVisibility(View.GONE);

                }else{
                    nextBtn.setVisibility(View.VISIBLE);
                    tabLayout.setVisibility(View.VISIBLE);
                    previousBtn.setVisibility(View.VISIBLE);
                    startBtn.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout = findViewById(R.id.intro_tb);

        nextBtn = findViewById(R.id.next_intro_btn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });

        previousBtn = findViewById(R.id.previous_intro_btn);
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });

        startBtn = findViewById(R.id.start_intro_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroViewActivity.this , EntryActivity.class);
                saveState();
                startActivity(intent);
                finish();
            }
        });


        //set adapter
        GlobalIntroduction globalIntroduction = new GlobalIntroduction(viewPager , tabLayout);
        globalIntroduction.getIntroductionList(this);


    }

    public boolean checkState(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("sharePrefMovieStreamingEntry" , MODE_PRIVATE);
        boolean openActivity = sharedPreferences.getBoolean("openActivity" , false);
        return openActivity;
    }

    public void saveState(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("sharePrefMovieStreamingEntry" , MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("openActivity" , true);
        editor.commit();

    }
}