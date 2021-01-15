package com.example.moviestreaming.global;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestreaming.adapter.CommentAdapter;
import com.example.moviestreaming.model.Comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GlobalCommentActivity {

    RecyclerView commentRV;
    Context context;
    RequestQueue requestQueue;
    List<Comment> comments;

    public GlobalCommentActivity(Context context){
        this.context = context;
    }

    public GlobalCommentActivity(Context context , RecyclerView recyclerView){

        this.commentRV = recyclerView;

        this.context = context;

    }


    public void setRecyclerViewComment(String id_movie){

        requestQueue = Volley.newRequestQueue(context);

        comments = new ArrayList<>();

        String URL = GetURLs.GetCommentsURL + "id_movie=" + id_movie;


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {


                    JSONArray array = response.getJSONArray("comments");

                    for(int i = 0; i<array.length(); i++){

                        JSONObject object = array.getJSONObject(i);

                        Comment comment = new Comment();

                        comment.setId(String.valueOf(object.getInt("id")));
                        comment.setUsername(object.getString("username"));
                        comment.setDate(object.getString("date"));
                        comment.setText(object.getString("text"));
                        comment.setValidation(String.valueOf(object.getInt("validation")));

                        comments.add(comment);

                    }

                    CommentAdapter commentAdapter = new CommentAdapter(context , comments);
                    commentRV.setAdapter(commentAdapter);
                    commentRV.setHasFixedSize(true);
                    commentRV.setLayoutManager(new LinearLayoutManager(context , RecyclerView.VERTICAL , false));



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


    public void sendComment(String comment , String id_movie , String username , String date){

        requestQueue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.POST, GetURLs.PostCommentURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Toast.makeText(context, response.trim(), Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String , String> params = new HashMap<>();

                params.put("comment" , comment);
                params.put("id_movie" , id_movie);
                params.put("username" , username);
                params.put("date" , date);

                return params;
            }
        };

        requestQueue.add(request);

    }

}
