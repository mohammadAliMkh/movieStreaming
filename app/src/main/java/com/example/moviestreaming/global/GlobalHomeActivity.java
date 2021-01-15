package com.example.moviestreaming.global;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestreaming.adapter.HomeAnimationAdapter;
import com.example.moviestreaming.adapter.HomeGenreAdapter;
import com.example.moviestreaming.adapter.HomePopularAdapter;
import com.example.moviestreaming.adapter.HomeSeriesAdapter;
import com.example.moviestreaming.adapter.HomeSliderAdapter;
import com.example.moviestreaming.adapter.HomeTopAdapter;
import com.example.moviestreaming.model.HomeGenre;
import com.example.moviestreaming.model.HomeSlider;
import com.example.moviestreaming.model.HomeMovie;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

public class GlobalHomeActivity {

    RequestQueue requestQueue;
    Context context;
    RecyclerView homeRV;

    // slider
    HomeSliderAdapter homeSliderAdapter;
    ViewPager homeSliderViewPager;
    TabLayout homeSliderTabLayout;
    List<HomeSlider> homeSliders;

    // Top Movies
    HomeTopAdapter homeTopAdapter;

    // popular movies
    HomePopularAdapter homePopularAdapter;

    // series
    HomeSeriesAdapter homeSeriesAdapter;

    // animations
    HomeAnimationAdapter homeAnimationAdapter;

    // genres
    HomeGenreAdapter homeGenreAdapter;


    public GlobalHomeActivity(Context context, List<HomeSlider> homeSliders, ViewPager viewPager, TabLayout tabLayout){
        this.context = context;
        this.homeSliderViewPager = viewPager;
        this.homeSliderTabLayout = tabLayout;
        this.homeSliders = homeSliders;
    }

    public GlobalHomeActivity(Context context , RecyclerView homeRV){
        this.context = context;
        this.homeRV = homeRV;
    }


    public void setHomeSliderViewPager(){
        requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GetURLs.HomeSliderURL,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("slider");
                    for(int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String time = jsonObject.getString("time");
                        String publish  = jsonObject.getString("publish");
                        String image_link = jsonObject.getString("image_link");

                        HomeSlider homeSlider = new HomeSlider();
                        homeSlider.setId(id);
                        homeSlider.setName(name);
                        homeSlider.setTime(time);
                        homeSlider.setPublish(publish);
                        homeSlider.setImage_link(image_link);

                        homeSliders.add(homeSlider);
                    }

                    homeSliderAdapter = new HomeSliderAdapter(context , homeSliders);
                    homeSliderViewPager.setAdapter(homeSliderAdapter);
                    homeSliderTabLayout.setupWithViewPager(homeSliderViewPager);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    public void setHomeTopRecyclerView(){

        List<HomeMovie> homeTops = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(context);

        String link = GetURLs.HomeMoviesURL + "category=top";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("movies");

                    for(int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String time = jsonObject.getString("time");
                        String image_link = jsonObject.getString("image_link");
                        String director = jsonObject.getString("director");
                        String publish = jsonObject.getString("publish");
                        String rate = jsonObject.getString("rate");
                        String category = jsonObject.getString("category");
                        String genre = jsonObject.getString("genre");

                        HomeMovie homeTopMovie = new HomeMovie();
                        homeTopMovie.setId(id);
                        homeTopMovie.setName(name);
                        homeTopMovie.setCategory(category);
                        homeTopMovie.setTime(time);
                        homeTopMovie.setImage_link(image_link);
                        homeTopMovie.setDirector(director);
                        homeTopMovie.setPublish(publish);
                        homeTopMovie.setRate(rate);
                        homeTopMovie.setGenre(genre);

                        homeTops.add(homeTopMovie);
                    }


                    homeTopAdapter = new HomeTopAdapter(context , homeTops);
                    homeRV.setAdapter(homeTopAdapter);
                    homeRV.setHasFixedSize(true);
                    homeRV.setLayoutManager(new LinearLayoutManager(context , RecyclerView.HORIZONTAL , false));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    public void setHomePopularRecyclerView(){

        List<HomeMovie> homePopulars = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(context);
        String link = GetURLs.HomeMoviesURL + "category=popular";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("movies");
                    for(int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String time = jsonObject.getString("time");
                        String image_link = jsonObject.getString("image_link");
                        String director = jsonObject.getString("director");
                        String publish = jsonObject.getString("publish");
                        String rate = jsonObject.getString("rate");
                        String category = jsonObject.getString("category");
                        String genre = jsonObject.getString("genre");

                        HomeMovie popularMovie = new HomeMovie();
                        popularMovie.setName(name.trim());
                        popularMovie.setTime(time);
                        popularMovie.setRate(rate);
                        popularMovie.setImage_link(image_link);
                        popularMovie.setId(id);
                        popularMovie.setCategory(category);
                        popularMovie.setDirector(director);
                        popularMovie.setPublish(publish);
                        popularMovie.setGenre(genre);

                        homePopulars.add(popularMovie);
                    }

                    homePopularAdapter = new HomePopularAdapter(context , homePopulars);
                    homeRV.setAdapter(homePopularAdapter);
                    homeRV.setHasFixedSize(true);
                    homeRV.setLayoutManager(new LinearLayoutManager(context , RecyclerView.HORIZONTAL , false));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void setHomeSeriesRecyclerView(){
        List<HomeMovie> homeSeries = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(context);

        String link = GetURLs.HomeMoviesURL + "category=series";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("movies");

                    for(int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String category = jsonObject.getString("category");
                        String time = jsonObject.getString("time");
                        String image_link = jsonObject.getString("image_link");
                        String director = jsonObject.getString("director");
                        String publish = jsonObject.getString("publish");
                        String rate = jsonObject.getString("rate");
                        String genre = jsonObject.getString("genre");

                        HomeMovie homeTopMovie = new HomeMovie();
                        homeTopMovie.setId(id);
                        homeTopMovie.setDirector(director);
                        homeTopMovie.setPublish(publish);
                        homeTopMovie.setRate(rate);
                        homeTopMovie.setCategory(category);
                        homeTopMovie.setGenre(genre);
                        homeTopMovie.setName(name);
                        homeTopMovie.setTime(time);
                        homeTopMovie.setImage_link(image_link);

                        homeSeries.add(homeTopMovie);
                    }


                    homeSeriesAdapter = new HomeSeriesAdapter(context , homeSeries);
                    homeRV.setAdapter(homeSeriesAdapter);
                    homeRV.setHasFixedSize(true);
                    homeRV.setLayoutManager(new LinearLayoutManager(context , RecyclerView.HORIZONTAL , false));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void setHomeAnimationRecyclerView(){
        List<HomeMovie> homeAnimations = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(context);
        String link = GetURLs.HomeMoviesURL + "category=animation";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("movies");
                    for(int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String time = jsonObject.getString("time");
                        String image_link = jsonObject.getString("image_link");
                        String director = jsonObject.getString("director");
                        String publish = jsonObject.getString("publish");
                        String category = jsonObject.getString("category");
                        String rate = jsonObject.getString("rate");
                        String genre = jsonObject.getString("genre");

                        HomeMovie animationMovie = new HomeMovie();
                        animationMovie.setName(name.trim());
                        animationMovie.setTime(time);
                        animationMovie.setRate(rate);
                        animationMovie.setId(id);
                        animationMovie.setDirector(director);
                        animationMovie.setCategory(category);
                        animationMovie.setPublish(publish);
                        animationMovie.setGenre(genre);
                        animationMovie.setImage_link(image_link);

                        homeAnimations.add(animationMovie);
                    }

                    homeAnimationAdapter = new HomeAnimationAdapter(context , homeAnimations);
                    homeRV.setAdapter(homeAnimationAdapter);
                    homeRV.setHasFixedSize(true);
                    homeRV.setLayoutManager(new LinearLayoutManager(context , RecyclerView.HORIZONTAL , false));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void setHomeGenreRecyclerView(){
        List<HomeGenre> homeGenres = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(context);
        String link = GetURLs.HomeGenreURL;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("genres");
                    for(int i=0; i<6; i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String name = jsonObject.getString("name");
                        String image_link = jsonObject.getString("image_link");

                        HomeGenre homeGenre = new HomeGenre(name , image_link);

                        homeGenres.add(homeGenre);
                    }

                    homeGenreAdapter = new HomeGenreAdapter(context , homeGenres);
                    homeRV.setAdapter(homeGenreAdapter);
                    homeRV.setHasFixedSize(true);
                    homeRV.setLayoutManager(new LinearLayoutManager(context , RecyclerView.HORIZONTAL , false));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void setListGenreRecyclerview(String genre){
        List<HomeMovie> genreList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(context);
        String link = GetURLs.GenreListURL + "genre=" + genre;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("genreList");
                    for(int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String time = jsonObject.getString("time");
                        String image_link = jsonObject.getString("image_link");
                        String director = jsonObject.getString("director");
                        String publish = jsonObject.getString("publish");
                        String category = jsonObject.getString("category");
                        String rate = jsonObject.getString("rate");
                        String genre = jsonObject.getString("genre");

                        HomeMovie genreListMovies = new HomeMovie();
                        genreListMovies.setName(name.trim());
                        genreListMovies.setTime(time);
                        genreListMovies.setRate(rate);
                        genreListMovies.setId(id);
                        genreListMovies.setDirector(director);
                        genreListMovies.setCategory(category);
                        genreListMovies.setPublish(publish);
                        genreListMovies.setGenre(genre);
                        genreListMovies.setImage_link(image_link);

                        genreList.add(genreListMovies);
                    }

                    homeTopAdapter = new HomeTopAdapter(context , genreList);
                    homeRV.setAdapter(homeTopAdapter);
                    homeRV.setHasFixedSize(true);
                    homeRV.setLayoutManager(new GridLayoutManager(context , 3));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


    public void fullSearch(String name){
        List<HomeMovie> movies = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(context);
        String link = GetURLs.FullSearchURL + "name=" + name;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("movies");
                    for(int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String time = jsonObject.getString("time");
                        String image_link = jsonObject.getString("image_link");
                        String director = jsonObject.getString("director");
                        String publish = jsonObject.getString("publish");
                        String category = jsonObject.getString("category");
                        String rate = jsonObject.getString("rate");
                        String genre = jsonObject.getString("genre");

                        HomeMovie movie = new HomeMovie();
                        movie.setName(name.trim());
                        movie.setTime(time);
                        movie.setRate(rate);
                        movie.setId(id);
                        movie.setDirector(director);
                        movie.setCategory(category);
                        movie.setPublish(publish);
                        movie.setGenre(genre);
                        movie.setImage_link(image_link);

                        movies.add(movie);
                    }

                    homeTopAdapter = new HomeTopAdapter(context , movies);
                    homeRV.setAdapter(homeTopAdapter);
                    homeRV.setHasFixedSize(true);
                    homeRV.setLayoutManager(new StaggeredGridLayoutManager(3 , StaggeredGridLayoutManager.VERTICAL));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void genreSearch(String name ,String key){

        List<HomeMovie> movies = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(context);
        String link = GetURLs.GenreSearchURL+ "genre=" + key + "&&" + "name=" + name;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("movies");
                    for(int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String time = jsonObject.getString("time");
                        String image_link = jsonObject.getString("image_link");
                        String director = jsonObject.getString("director");
                        String publish = jsonObject.getString("publish");
                        String category = jsonObject.getString("category");
                        String rate = jsonObject.getString("rate");
                        String genre = jsonObject.getString("genre");

                        HomeMovie movie = new HomeMovie();
                        movie.setName(name.trim());
                        movie.setTime(time);
                        movie.setRate(rate);
                        movie.setId(id);
                        movie.setDirector(director);
                        movie.setCategory(category);
                        movie.setPublish(publish);
                        movie.setGenre(genre);
                        movie.setImage_link(image_link);

                        movies.add(movie);
                    }

                    homeTopAdapter = new HomeTopAdapter(context , movies);
                    homeRV.setAdapter(homeTopAdapter);
                    homeRV.setHasFixedSize(true);
                    homeRV.setLayoutManager(new StaggeredGridLayoutManager(3 , StaggeredGridLayoutManager.VERTICAL));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


}
