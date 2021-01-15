package com.example.moviestreaming.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestreaming.R;
import com.example.moviestreaming.global.GetURLs;
import com.example.moviestreaming.global.GlobalHomeActivity;
import com.example.moviestreaming.model.HomeSlider;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    ImageView menuIcon;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RelativeLayout rootParent;

    RequestQueue requestQueue;

    TextView usernameTV , phoneNumberTv, moreGenre , moreIMDb, morePopular, moreSeries, moreAnimation;

    RecyclerView homeGenreRV, homeTopRV, homePopularRV , homeSeriesRV , homeAnimationRV;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    ViewPager homeSliderViewPager;
    TabLayout homeSliderTabLayout;

    List<HomeSlider> homeSliders = new ArrayList<>();

    public static final int SET_IMAGE = 1;
    public static final int CHANGE_IMAGE = 2;


    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //check credit
        checkCredit();

        // menu icon
        menuIcon = findViewById(R.id.menu_icon_home);
        menuIcon.setOnClickListener(this);

        // drawerLayout and navigation
        drawerLayout = findViewById(R.id.home_drawerLayout);
        rootParent = findViewById(R.id.x012);
        navigationView = findViewById(R.id.home_navigation);
        setDrawerLayoutFunction();

        // more - part
        moreGenre = findViewById(R.id.home_more_genre);
        moreGenre.setOnClickListener(this);
        moreIMDb = findViewById(R.id.home_more_IMDb);
        moreIMDb.setOnClickListener(this);
        morePopular = findViewById(R.id.home_more_popular);
        morePopular.setOnClickListener(this);
        moreSeries = findViewById(R.id.home_more_series);
        moreSeries.setOnClickListener(this);
        moreAnimation = findViewById(R.id.home_more_animation);
        moreAnimation.setOnClickListener(this);

        // genre - part
        homeGenreRV = findViewById(R.id.homeGenreRV);
        setHomeGenreRV();

        // slider - part
        homeSliderViewPager = findViewById(R.id.homeSliderVP);
        homeSliderTabLayout = findViewById(R.id.homeSliderTL);
        setHomeSliderViewPager();
        
        // top movies - part
        homeTopRV = findViewById(R.id.homeTopRV);
        setHomeTopRV();
        
        // popular movies - part
        homePopularRV = findViewById(R.id.homePopularRV);
        setHomePopularRV();

        // series - part
        homeSeriesRV = findViewById(R.id.homeSeriesRV);
        setHomeSeriesRV();

        // animations - part
        homeAnimationRV = findViewById(R.id.homeAnimationRV);
        setHomeAnimationRV();

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkCredit();
    }

    private void checkCredit() {

        requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GetURLs.getCreditFromUserURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                    Long credit = Long.parseLong(response.trim());
                    editor = sharedPreferences.edit();
                    editor.putLong("total_credit" , credit);
                    editor.commit();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(HomeActivity.this, error.getMessage()+"", Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String , String> creditMap = new HashMap<>();
                System.out.println(sharedPreferences.getString("email" , ""));
                creditMap.put("email" , sharedPreferences.getString("email" , ""));

                return creditMap;
            }
        };

        requestQueue.add(stringRequest);

    }


    private void setDrawerLayoutFunction() {

        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@androidx.annotation.NonNull View drawerView, float slideOffset) {

                float offset_change = slideOffset * (1 - 0.8f);
                float offset = 1 - offset_change;

                rootParent.setScaleX(offset);
                rootParent.setScaleY(offset);

                float off_set = drawerView.getWidth() * slideOffset;
                float change_offset_x= rootParent.getWidth() * offset_change /2;
                float translation = off_set - change_offset_x;
                rootParent.setTranslationX(translation);
            }

            @Override
            public void onDrawerOpened(@androidx.annotation.NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@androidx.annotation.NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getHeaderView(0).findViewById(R.id.profile_image_home_navigation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickUpProfilePicture(SET_IMAGE);
            }
        });
        navigationView.getHeaderView(0).findViewById(R.id.profile_image_home_navigation1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickUpProfilePicture(CHANGE_IMAGE);
            }
        });

        setNavigationHeaderInformation();

    }

    private void setNavigationHeaderInformation() {

        usernameTV =  navigationView.getHeaderView(0).findViewById(R.id.home_username_TV);
        phoneNumberTv =  navigationView.getHeaderView(0).findViewById(R.id.home_phoneNumber_TV);

        sharedPreferences = getSharedPreferences("sharePrefMovieStreamingEntry" , MODE_PRIVATE);

        usernameTV.setText(sharedPreferences.getString("username" , "").trim());
        phoneNumberTv.setText("+989" + sharedPreferences.getString("phoneNumber" , "").trim());

    }

    private void pickUpProfilePicture(int code) {
        Intent intent = new Intent();

        if(code == SET_IMAGE){

            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SET_IMAGE);

        }else{

            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), CHANGE_IMAGE);

        }

    }

    private void setHomeGenreRV() {
        GlobalHomeActivity globalHomeActivity = new GlobalHomeActivity(getApplicationContext() , homeGenreRV);
        globalHomeActivity.setHomeGenreRecyclerView();
    }

    private void setHomeAnimationRV() {
        GlobalHomeActivity globalHomeActivity = new GlobalHomeActivity(getApplicationContext() , homeAnimationRV);
        globalHomeActivity.setHomeAnimationRecyclerView();
    }

    private void setHomeSeriesRV() {
        GlobalHomeActivity globalHomeActivity = new GlobalHomeActivity(getApplicationContext() , homeSeriesRV);
        globalHomeActivity.setHomeSeriesRecyclerView();
    }

    private void setHomePopularRV() {
        GlobalHomeActivity globalHomeActivity = new GlobalHomeActivity(getApplicationContext() , homePopularRV);
        globalHomeActivity.setHomePopularRecyclerView();
    }

    private void setHomeTopRV() {
        GlobalHomeActivity globalHomeActivity = new GlobalHomeActivity(getApplicationContext() , homeTopRV);
        globalHomeActivity.setHomeTopRecyclerView();
    }

    private void setHomeSliderViewPager() {

        GlobalHomeActivity globalHomeActivity = new GlobalHomeActivity(getApplicationContext(), homeSliders, homeSliderViewPager , homeSliderTabLayout);
        globalHomeActivity.setHomeSliderViewPager();

        Observable<Long> observable = Observable.interval(3 , 4 , TimeUnit.SECONDS);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {

                        if(homeSliderViewPager.getCurrentItem() < homeSliders.size()-1){
                            homeSliderViewPager.setCurrentItem(homeSliderViewPager.getCurrentItem()+1);
                        }else if(homeSliderViewPager.getCurrentItem() == homeSliders.size()-1){
                            homeSliderViewPager.setCurrentItem(0);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("onError:" , e.getMessage()+"");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("onComplete:" , "task completed!");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == SET_IMAGE) {

                Uri selectedImageURI = data.getData();

                findViewById(R.id.profile_image_home_navigation1).setVisibility(View.VISIBLE);
                findViewById(R.id.profile_image_home_navigation).setVisibility(View.INVISIBLE);

                Picasso.get().load(selectedImageURI).noPlaceholder().centerCrop().fit()
                        .into((ImageView) findViewById(R.id.profile_image_home_navigation1));

            }else{

                Uri selectedImageURI = data.getData();

                Picasso.get().load(selectedImageURI).noPlaceholder().centerCrop().fit()
                        .into((ImageView) findViewById(R.id.profile_image_home_navigation1));
            }

        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){

            drawerLayout.closeDrawer(GravityCompat.START);

        }else{

            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == menuIcon.getId()){
            if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.START);
            }else{
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }else if(view.getId() == moreGenre.getId()){
            intent = new Intent(HomeActivity.this , MoreActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("more_key" , "Genre");
            bundle.putString("from_key" , "home");
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(view.getId() == moreIMDb.getId()){
            intent = new Intent(HomeActivity.this , MoreActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("more_key" , "IMDb Tops");
            bundle.putString("from_key" , "home");
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(view.getId() == morePopular.getId()){
            intent = new Intent(HomeActivity.this , MoreActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("more_key" , "Popular");
            bundle.putString("from_key" , "home");
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(view.getId() == moreSeries.getId()){
            intent = new Intent(HomeActivity.this , MoreActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("more_key" , "Series");
            bundle.putString("from_key" , "home");
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            intent = new Intent(HomeActivity.this , MoreActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("more_key" , "Animation");
            bundle.putString("from_key" , "home");
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@androidx.annotation.NonNull MenuItem item) {

        switch (item.getItemId()) {


            case R.id.menu_home:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.menu_search:
                intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
                break;

            case R.id.menu_favorite:
                intent = new Intent(HomeActivity.this , FavoriteActivity.class  );
                startActivity(intent);
                break;

            case R.id.menu_exit:
                sharedPreferences = getSharedPreferences("sharePrefMovieStreamingEntry", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putBoolean("openActivityEntry" , false);

                editor.remove("total_credit");
                editor.remove("phoneNumber");

                editor.commit();

                finish();

                break;

            case R.id.menu_credit:
                Intent intent = new Intent(HomeActivity.this , BuyAccountActivity.class);
                startActivity(intent);
                break;

            case  R.id.menu_account:
                Intent intent1 = new Intent(HomeActivity.this , AccountActivity.class);
                startActivity(intent1);
                break;

        }

        return false;
    }
}