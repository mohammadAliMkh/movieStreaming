package com.example.moviestreaming.global;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestreaming.R;
import com.example.moviestreaming.adapter.CastAdapter;
import com.example.moviestreaming.adapter.EpisodeAdapter;
import com.example.moviestreaming.adapter.HomeTopAdapter;
import com.example.moviestreaming.adapter.SeasonAdapter;
import com.example.moviestreaming.model.Cast;
import com.example.moviestreaming.model.Episode;
import com.example.moviestreaming.model.HomeMovie;
import com.example.moviestreaming.model.SeriesSeason;
import com.example.moviestreaming.view.BuyAccountActivity;
import com.example.moviestreaming.view.PlayActivity;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GlobalDetailFragment {

    RequestQueue requestQueue;

    Context context;

    KenBurnsView kenBurnsView;

    TextView descriptionTxt;

    RecyclerView recyclerView, seasonRV;

    CastAdapter adapter;


    public GlobalDetailFragment(Context context, KenBurnsView kenBurnsView , TextView description , RecyclerView castRV , RecyclerView seasonsRV){
        this.context = context;
        this.kenBurnsView = kenBurnsView;
        this.descriptionTxt = description;
        this.seasonRV = seasonsRV;
        this.recyclerView = castRV;
    }

    public GlobalDetailFragment(Context context, KenBurnsView kenBurnsView , TextView description){
        this.context = context;
        this.kenBurnsView = kenBurnsView;
        this.descriptionTxt = description;
    }

    public GlobalDetailFragment(Context context , RecyclerView recyclerView){
        this.context = context;
        this.recyclerView = recyclerView;
    }

    public void setMovieDetail(String id , ImageView play_ic , TextView download){

        requestQueue = Volley.newRequestQueue(context);

        String link = GetURLs.DetailMovieURL + "id=" + id.trim();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("movies");

                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    String id = jsonObject.getString("id");
                    String description = jsonObject.getString("description");
                    String image_link = jsonObject.getString("link_image");
                    String video_link = jsonObject.getString("link_video");

                    descriptionTxt.setText(description);
                    Picasso.get().load(image_link).into(kenBurnsView);

                    play_ic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                                Bundle bundle = new Bundle();
                                bundle.putString("video_link" , video_link);

                                Intent intent = new Intent(context , PlayActivity.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);

                        }
                    });

                    download.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Dexter.withContext(context).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    .withListener(new MultiplePermissionsListener() {
                                        @Override
                                        public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                                            if(multiplePermissionsReport.areAllPermissionsGranted()){

                                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(video_link));
                                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                                request.allowScanningByMediaScanner();
                                                request.setTitle("movie" + id.toString());
                                                request.setDestinationInExternalFilesDir(context , Environment.getExternalStorageState() + "TopMovies" , "");

                                                DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                                                manager.enqueue(request);

                                            }

                                        }

                                        @Override
                                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                                        }
                                    }).check();


                        }
                    });

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

    public void setSeriesDetail(String id){

        requestQueue = Volley.newRequestQueue(context);

        String link = GetURLs.DetailSeriesURL + "id=" + id.trim();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("series");

                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    String id = jsonObject.getString("id");
                    String description = jsonObject.getString("description");
                    String image_link = jsonObject.getString("link_image");

                    System.out.println(" gogooooooooooooooooooooooooooli");
                    descriptionTxt.setText("DESCRIPTION: " +description);
                    Picasso.get().load(image_link).into(kenBurnsView);

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

    public void setCastRV(String id){
        requestQueue = Volley.newRequestQueue(context);

        String link = GetURLs.CastsURL + "home_id=" + id.trim();

        List<Cast> casts = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("casts");
                    for (int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String image_link = jsonObject.getString("image_link");

                        Cast cast = new Cast();
                        cast.setId(id);
                        cast.setName(name);
                        cast.setImage_link(image_link);

                        casts.add(cast);
                    }

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context , RecyclerView.HORIZONTAL , false));
                    adapter = new CastAdapter(context , casts);
                    recyclerView.setAdapter(adapter);

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

    public void setSeasonRV(String id){

        requestQueue = Volley.newRequestQueue(context);
        String link = GetURLs.SeasonURL + "home_id=" + id;
        List<SeriesSeason> seriesSeasonList = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("seasons");
                    for(int i=0; i<jsonArray.length(); i ++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("id");
                        String home_id = jsonObject.getString("home_id");
                        String number = jsonObject.getString("number");
                        String episodes = jsonObject.getString("episods");
                        String img_link = jsonObject.getString("season_img_link");

                        SeriesSeason seriesSeason = new SeriesSeason();
                        seriesSeason.setId(id);
                        seriesSeason.setHome_id(home_id);
                        seriesSeason.setEpisodes(episodes);
                        seriesSeason.setNumber(number);
                        seriesSeason.setImg_link(img_link);

                        seriesSeasonList.add(seriesSeason);
                    }

                    SeasonAdapter adapter = new SeasonAdapter(context , seriesSeasonList);
                    seasonRV.setHasFixedSize(true);
                    seasonRV.setLayoutManager(new LinearLayoutManager(context , RecyclerView.HORIZONTAL , false));
                    seasonRV.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context , error.getMessage()+"" , Toast.LENGTH_SHORT);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void setEpisodeRV(String id){
        requestQueue = Volley.newRequestQueue(context);
        String link = GetURLs.EpisodeURL + "detail_id=" + id;
        List<Episode> episodeList = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("episodes");
                    for(int i=0; i<jsonArray.length(); i ++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("id");
                        String detail_id = jsonObject.getString("detail_id");
                        String episode_name = jsonObject.getString("episode_name");
                        String episode_number = jsonObject.getString("episode_number");
                        String link_img = jsonObject.getString("link_img");
                        String link_video = jsonObject.getString("link_video");

                        Episode episode = new Episode();
                        episode.setId(id);
                        episode.setDetail_id(detail_id);
                        episode.setEpisode_name(episode_name);
                        episode.setEpisode_number(episode_number);
                        episode.setLink_img(link_img);
                        episode.setLink_video(link_video);
                        System.out.println(link_img);
                        System.out.println("googooli");

                        episodeList.add(episode);
                    }

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new GridLayoutManager(context , 2));
                    EpisodeAdapter episodeAdapter = new EpisodeAdapter(context , episodeList);
                    recyclerView.setAdapter(episodeAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context , error.getMessage()+"" , Toast.LENGTH_SHORT);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void setSimilarRV(String category){
        requestQueue = Volley.newRequestQueue(context);
        String link = GetURLs.HomeMoviesURL + "category=" + category;
        List<HomeMovie> homeMovies = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("movies");

                    double rand = Math.random();
                    System.out.println(rand);
                    rand = rand * jsonArray.length();
                    int random = (int) rand;

                    if(random < 3){
                        random = random + 3;
                    }else if(random >= jsonArray.length()){
                        random = jsonArray.length() - 1 ;
                    }

                    for(int i=random-3; i<random; i ++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String genre = jsonObject.getString("genre");
                        String time = jsonObject.getString("time");
                        String rate = jsonObject.getString("rate");
                        String category = jsonObject.getString("category");
                        String publish = jsonObject.getString("publish");
                        String image_link = jsonObject.getString("image_link");
                        String director = jsonObject.getString("director");
                        String rank = jsonObject.getString("rank");

                        HomeMovie homeMovie = new HomeMovie();
                        homeMovie.setId(id);
                        homeMovie.setId(id);
                        homeMovie.setName(name);
                        homeMovie.setGenre(genre);
                        homeMovie.setTime(time);
                        homeMovie.setCategory(category);
                        homeMovie.setRate(rate);
                        homeMovie.setPublish(publish);
                        homeMovie.setImage_link(image_link);
                        homeMovie.setDirector(director);
                        homeMovie.setRank(rank);

                        homeMovies.add(homeMovie);
                    }

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context , RecyclerView.HORIZONTAL , false));
                    HomeTopAdapter homeTopAdapter = new HomeTopAdapter(context , homeMovies);
                    recyclerView.setAdapter(homeTopAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context , error.getMessage()+"" , Toast.LENGTH_SHORT);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}
