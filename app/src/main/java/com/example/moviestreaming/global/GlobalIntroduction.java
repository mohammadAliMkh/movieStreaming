package com.example.moviestreaming.global;

import android.content.Context;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestreaming.adapter.IntroAdapter;
import com.example.moviestreaming.model.Intro;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;

public class GlobalIntroduction {

    RequestQueue requestQueue;
    List<Intro> introList = new ArrayList<>();

    ViewPager viewPager;
    TabLayout tabLayout;

    IntroAdapter adapter;

    public GlobalIntroduction(ViewPager viewPager , TabLayout tabLayout){
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
    }


    public void getIntroductionList(Context context){

        requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GetURLs.IntroductionURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("introductions");

                    for(int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        Intro intro = new Intro();
                        intro.setId(jsonObject.getString("id"));
                        intro.setDescription(jsonObject.getString("description"));
                        System.out.println(jsonObject.getString("description"));
                        intro.setImage_link(jsonObject.getString("image_link"));
                        System.out.println(jsonObject.getString("image_link"));

                        introList.add(intro);
                    }

                    adapter = new IntroAdapter(context , introList);
                    viewPager.setAdapter(adapter);
                    tabLayout.setupWithViewPager(viewPager);

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
