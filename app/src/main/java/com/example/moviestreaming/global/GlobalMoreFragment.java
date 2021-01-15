package com.example.moviestreaming.global;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestreaming.adapter.FragmentAnimationAdapter;
import com.example.moviestreaming.adapter.FragmentGenreAdapter;
import com.example.moviestreaming.adapter.FragmentPopularAdapter;
import com.example.moviestreaming.adapter.FragmentSeriesAdapter;
import com.example.moviestreaming.adapter.FragmentTopAdapter;
import com.example.moviestreaming.model.HomeGenre;
import com.example.moviestreaming.model.HomeMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GlobalMoreFragment {

    Context context;
    RecyclerView moreRecyclerView;
    RequestQueue requestQueue;

    public GlobalMoreFragment(Context context , RecyclerView recyclerView){
        this.context = context;
        this.moreRecyclerView = recyclerView;
    }

    public void setGenreRecyclerView(){

        requestQueue = Volley.newRequestQueue(context);

        String link = GetURLs.HomeGenreURL;

        List<HomeGenre> homeGenres = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("genres");

                    for (int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String image = jsonObject.getString("image_link");
                        homeGenres.add(new HomeGenre(name , image));
                    }

                    FragmentGenreAdapter adapter = new FragmentGenreAdapter(context , homeGenres);
                    moreRecyclerView.setHasFixedSize(true);
                    moreRecyclerView.setLayoutManager(new GridLayoutManager(context , 2 , RecyclerView.VERTICAL , false));
                    moreRecyclerView.setAdapter(adapter);

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

    public void setTopRecyclerView(){
        requestQueue = Volley.newRequestQueue(context);

        String link = GetURLs.MoreMoviesURL + "category=top";

        List<HomeMovie> homeMovies = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("movies");

                    for (int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String time = jsonObject.getString("time");
                        String category = jsonObject.getString("category");
                        String image_link = jsonObject.getString("image_link");
                        String director = jsonObject.getString("director");
                        String publish = jsonObject.getString("publish");
                        String rate = jsonObject.getString("rate");
                        String genre = jsonObject.getString("genre");
                        String rank = jsonObject.getString("rank");


                        HomeMovie homeMovie = new HomeMovie();
                        homeMovie.setName(name);
                        homeMovie.setTime(time);
                        homeMovie.setId(id);
                        homeMovie.setRate(rate);
                        homeMovie.setCategory(category);
                        homeMovie.setGenre(genre);
                        homeMovie.setPublish(publish);
                        homeMovie.setDirector(director);
                        homeMovie.setRank(rank);
                        homeMovie.setImage_link(image_link);

                        homeMovies.add(homeMovie);
                    }

                    FragmentTopAdapter adapter = new FragmentTopAdapter(context , homeMovies);
                    moreRecyclerView.setHasFixedSize(true);
                    moreRecyclerView.setLayoutManager(new LinearLayoutManager(context , RecyclerView.VERTICAL , false));
                    moreRecyclerView.setAdapter(adapter);

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

    public void setPopularRecyclerView(){
        requestQueue = Volley.newRequestQueue(context);

        String link = GetURLs.MoreMoviesURL + "category=popular";

        List<HomeMovie> homeMovies = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("movies");

                    for (int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String time = jsonObject.getString("time");
                        String image_link = jsonObject.getString("image_link");
                        String category = jsonObject.getString("category");
                        String director = jsonObject.getString("director");
                        String publish = jsonObject.getString("publish");
                        String rate = jsonObject.getString("rate");
                        String genre = jsonObject.getString("genre");
                        String rank = jsonObject.getString("rank");


                        HomeMovie homeMovie = new HomeMovie();
                        homeMovie.setName(name);
                        homeMovie.setTime(time);
                        homeMovie.setId(id);
                        homeMovie.setRate(rate);
                        homeMovie.setGenre(genre);
                        homeMovie.setCategory(category);
                        homeMovie.setPublish(publish);
                        homeMovie.setDirector(director);
                        homeMovie.setRank(rank);
                        homeMovie.setImage_link(image_link);

                        homeMovies.add(homeMovie);
                    }

                    FragmentPopularAdapter adapter = new FragmentPopularAdapter(context , homeMovies);
                    moreRecyclerView.setHasFixedSize(true);
                    moreRecyclerView.setLayoutManager(new LinearLayoutManager(context , RecyclerView.VERTICAL , false));
                    moreRecyclerView.setAdapter(adapter);

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

    public void setSeriesRecyclerView(){
        requestQueue = Volley.newRequestQueue(context);

        String link = GetURLs.MoreMoviesURL + "category=series";

        List<HomeMovie> homeMovies = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("movies");

                    for (int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String time = jsonObject.getString("time");
                        String category = jsonObject.getString("category");
                        String image_link = jsonObject.getString("image_link");
                        String director = jsonObject.getString("director");
                        String publish = jsonObject.getString("publish");
                        String rate = jsonObject.getString("rate");
                        String genre = jsonObject.getString("genre");
                        String rank = jsonObject.getString("rank");

                        HomeMovie homeMovie = new HomeMovie();
                        homeMovie.setName(name);
                        homeMovie.setId(id);
                        homeMovie.setPublish(publish);
                        homeMovie.setGenre(genre);
                        homeMovie.setRate(rank);
                        homeMovie.setTime(time);
                        homeMovie.setCategory(category);
                        homeMovie.setDirector(director);
                        homeMovie.setRate(rate);
                        homeMovie.setImage_link(image_link);

                        homeMovies.add(homeMovie);
                    }

                    FragmentSeriesAdapter adapter = new FragmentSeriesAdapter(context , homeMovies);
                    moreRecyclerView.setHasFixedSize(true);
                    moreRecyclerView.setLayoutManager(new LinearLayoutManager(context , RecyclerView.VERTICAL , false));
                    moreRecyclerView.setAdapter(adapter);

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

    public void setAnimationRecyclerView(){
        requestQueue = Volley.newRequestQueue(context);

        String link = GetURLs.MoreMoviesURL + "category=animation";

        List<HomeMovie> homeMovies = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("movies");

                    for (int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String time = jsonObject.getString("time");
                        String category = jsonObject.getString("category");
                        String image_link = jsonObject.getString("image_link");
                        String director = jsonObject.getString("director");
                        String publish = jsonObject.getString("publish");
                        String rate = jsonObject.getString("rate");
                        String genre = jsonObject.getString("genre");
                        String rank = jsonObject.getString("rank");


                        HomeMovie homeMovie = new HomeMovie();
                        homeMovie.setName(name);
                        homeMovie.setTime(time);
                        homeMovie.setId(id);
                        homeMovie.setRate(rate);
                        homeMovie.setCategory(category);
                        homeMovie.setGenre(genre);
                        homeMovie.setPublish(publish);
                        homeMovie.setDirector(director);
                        homeMovie.setRank(rank);
                        homeMovie.setImage_link(image_link);

                        homeMovies.add(homeMovie);
                    }

                    FragmentAnimationAdapter adapter = new FragmentAnimationAdapter(context , homeMovies);
                    moreRecyclerView.setHasFixedSize(true);
                    moreRecyclerView.setLayoutManager(new LinearLayoutManager(context , RecyclerView.VERTICAL , false));
                    moreRecyclerView.setAdapter(adapter);

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
